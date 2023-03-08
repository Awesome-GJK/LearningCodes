# 插入方法--put()

> put方法源码如下：

```java
    public V put(K key, V value) {
        return putVal(key, value, false);
    }
    
    
     final V putVal(K key, V value, boolean onlyIfAbsent) {
         // key和value都不能为null,否则报错
        if (key == null || value == null) throw new NullPointerException();
         //计算key的hash
        int hash = spread(key.hashCode());
         //记录链表中元素个数
        int binCount = 0;
         //这是一个死循环，
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f; int n, i, fh;
            //如果数组是空或者数组长度为0，则初始化数组
            if (tab == null || (n = tab.length) == 0)
                tab = initTable();
            //如果插入桶中没有元素，则直接插入
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                if (casTabAt(tab, i, null,
                             new Node<K,V>(hash, key, value, null)))
                    //此处会通过CAS进行插入，如果插入成功，break跳出循环；
                    //如果插入失败，已经有其他元素了，则再次进入循环重新插入
                    break;                   // no lock when adding to empty bin
            }
            //如果要插入的元素所在的桶的第一个元素的hash是MOVED，则当前线程帮忙一起迁移元素
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);
            else {
                V oldVal = null;
                //如果桶不为空且不在迁移数据，则锁住这个桶
                synchronized (f) {
                    //再次检查桶中第一个元素是否有变化。防止获取锁之前被其他线程修改
                    //如果有变化进入，重新进入循环
                    if (tabAt(tab, i) == f) {
                        //如果第一个元素的hash大于0，代表当前不在迁移数据，且数据时链表存储的，不是使用的树
                        if (fh >= 0) {
                            binCount = 1;
                            //遍历链表
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;
                                //如果存在key相同，则替换旧值，跳出循环
                                if (e.hash == hash &&
                                    ((ek = e.key) == key ||
                                     (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                Node<K,V> pred = e;
                                //如果没有找到key相同的，则新建节点存到链表尾部
                                if ((e = e.next) == null) {
                                    pred.next = new Node<K,V>(hash, key,
                                                              value, null);
                                    break;
                                }
                            }
                        }
                        //如果桶中第一个元素是树，则通过putTreeVal向树中插入值
                        else if (f instanceof TreeBin) {
                            Node<K,V> p;
                            binCount = 2;
                            //如果成功插入树，则putTreeVal返回null.
                            //否则在树中查找节点，替换旧值
                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                           value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                    }
                }
                //如果链表长度大于等于8，则将链表树化
                if (binCount != 0) {
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    //如果存在旧值，返回旧值
                    if (oldVal != null)
                        return oldVal;
                    //跳出最外层for循环
                    break;
                }
            }
        }
         //成功插入，链表计数+1，扩容也在这个方法里
        addCount(1L, binCount);
        return null;
    }
```

> ConcurrentHashMap主要通过CAS + synchronized + volatile实现线程安全的。put方法主要步骤如下：
>
> * （1）如果桶数组未初始化，则初始化；
> * （2）如果待插入的元素所在的桶为空，则尝试CAS把此元素直接插入到桶的第一个位置；
> * （3）如果正在扩容，则当前线程一起加入到扩容的过程中；
> * （4）如果待插入的元素所在的桶不为空且不在迁移元素，则锁住这个桶（分段锁）；
> * （5）如果当前桶中元素以链表方式存储，则在链表中寻找该元素或者插入元素；
> * （6）如果当前桶中元素以红黑树方式存储，则在红黑树中寻找该元素或者插入元素；
> * （7）如果元素存在，则返回旧值；
> * （8）如果元素不存在，整个Map的元素个数加1，并检查是否需要扩容；

## 初始化数组--initTable()

> initTable方法源码如下：

```java
    private final Node<K,V>[] initTable() {
        Node<K,V>[] tab; int sc;
        while ((tab = table) == null || tab.length == 0) {
            //sizeCtl 小于0 ，说明有其他线程正在执行初始化或者扩容，那么当前线程让出cpu，进入就绪状态
            if ((sc = sizeCtl) < 0)
                Thread.yield(); // lost initialization race; just spin
            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
                //进行CAS操作，将sizeCtl值更新为-1，如果成功当前现场进入初始化
                //如果失败，说明其他线程先一步进行初始化，则进入下一次循环。
                //下一次进来的时候还没有初始化完毕，则进入上面的if中让出CPU使用权
               //下一次进来的时候已经初始化完毕，length!=0，所以直接退出循环
                try {
                    if ((tab = table) == null || tab.length == 0) {
                        //如果sc为0，则使用默认容量16
                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                        //新建数组，并给table赋值
                        @SuppressWarnings("unchecked")
                        Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                        table = tab = nt;
                        //n-(n>>>2) = n - n/4 = 0.75n
                        //这里是计算扩容阈值
                        sc = n - (n >>> 2);
                    }
                } finally {
                    //将扩容阈值设置到sizeCtl，不使用也没有threshold负载因子和loadFactor扩容阈值两个属性
                    sizeCtl = sc;
                }
                break;
            }
        }
        return tab;
    }
```

> initTable方法步骤如下：
>
> * （1）使用CAS锁控制只有一个线程初始化桶数组；
> * （2）sizeCtl在初始化后存储的是扩容门槛；
> * （3）扩容门槛写死的是桶数组大小的0.75倍，桶数组大小即map的容量，也就是最多存储多少个元素。

## 判断是否需要扩容--addCount()

> addCount方法源码解析:

```java
    private final void addCount(long x, int check) {
        CounterCell[] as; long b, s;
        //compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)
        //先通过CAS对baseCount进行+1，如果失败了，说明有其他线程在修改baseCount
        if ((as = counterCells) != null ||
            !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
            CounterCell a; long v; int m;
            boolean uncontended = true;
            //如果CAS修改baseCount失败了，则通过CAS修改各个桶的CounterCell
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[ThreadLocalRandom.getProbe() & m]) == null ||
                !(uncontended =
                  U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
                //如果CAS修改CounterCell也失败了，则对counterCell进行扩容，，来减少多个线程hash到同一个桶的概率
                fullAddCount(x, uncontended);
                return;
            }
            if (check <= 1)
                return;
            //计算元素个数，即baseCount+各个桶的CounterCell
            s = sumCount();
        }
        if (check >= 0) {
            Node<K,V>[] tab, nt; int n, sc;
            //当元素个数达到扩容阈值时，进行扩容
            while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
                   (n = tab.length) < MAXIMUM_CAPACITY) {
                int rs = resizeStamp(n);
                //sc小于 0 说明当前正在扩容
                if (sc < 0) {
                    if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                        sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                        transferIndex <= 0)
                        //扩容执行完成，跳出循环
                        break;
                    //扩容未完成，当前线程加入迁移元素中，并把扩容线程数+1
                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                        transfer(tab, nt);
                }
                else if (U.compareAndSwapInt(this, SIZECTL, sc,
                                             (rs << RESIZE_STAMP_SHIFT) + 2))
                    transfer(tab, null);
                s = sumCount();
            }
        }
    }
```

>addCount判断是否需要扩容方法步骤如下：
>
>* （1）元素个数的存储方式类似于LongAdder类，存储在不同的段上，减少不同线程同时更新size时的冲突；
>* （2）计算元素个数时把这些段的值及baseCount相加算出总的元素个数；
>* （3）正常情况下sizeCtl存储着扩容门槛，扩容门槛为容量的0.75倍；、
>* （4）扩容时sizeCtl高位存储扩容邮戳(resizeStamp)，低位存储扩容线程数加1（1+nThreads）；
>* （5）其它线程添加元素后如果发现存在扩容，也会加入的扩容行列中来；

## 迁移元素方法--transfer()

> transfer方法步骤：
>
> * （1）新桶数组大小是旧桶数组的两倍；
> * （2）迁移元素先从靠后的桶开始；
> * （3）迁移完成的桶在里面放置一ForwardingNode类型的元素，标记该桶迁移完成；
> * （4）迁移时根据hash&n是否等于0把桶中元素分化成两个链表或树；
> * （5）低位链表（树）存储在原来的位置；
>
> （6）高们链表（树）存储在原来的位置加n的位置；
>
> （7）迁移元素时会锁住当前桶，也是分段锁的思想；

# 删除方法--remove()

> remove方法源码解析：

```java
    public V remove(Object key) {
        return replaceNode(key, null, null);
    }


    final V replaceNode(Object key, V value, Object cv) {
        int hash = spread(key.hashCode());
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f; int n, i, fh;
            if (tab == null || (n = tab.length) == 0 ||
                (f = tabAt(tab, i = (n - 1) & hash)) == null)
                break;
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);
            else {
                V oldVal = null;
                boolean validated = false;
                synchronized (f) {
                    if (tabAt(tab, i) == f) {
                        if (fh >= 0) {
                            validated = true;
                            for (Node<K,V> e = f, pred = null;;) {
                                K ek;
                                if (e.hash == hash &&
                                    ((ek = e.key) == key ||
                                     (ek != null && key.equals(ek)))) {
                                    V ev = e.val;
                                    if (cv == null || cv == ev ||
                                        (ev != null && cv.equals(ev))) {
                                        oldVal = ev;
                                        if (value != null)
                                            e.val = value;
                                        else if (pred != null)
                                            pred.next = e.next;
                                        else
                                            setTabAt(tab, i, e.next);
                                    }
                                    break;
                                }
                                pred = e;
                                if ((e = e.next) == null)
                                    break;
                            }
                        }
                        else if (f instanceof TreeBin) {
                            validated = true;
                            TreeBin<K,V> t = (TreeBin<K,V>)f;
                            TreeNode<K,V> r, p;
                            if ((r = t.root) != null &&
                                (p = r.findTreeNode(hash, key, null)) != null) {
                                V pv = p.val;
                                if (cv == null || cv == pv ||
                                    (pv != null && cv.equals(pv))) {
                                    oldVal = pv;
                                    if (value != null)
                                        p.val = value;
                                    else if (t.removeTreeNode(p))
                                        setTabAt(tab, i, untreeify(t.first));
                                }
                            }
                        }
                    }
                }
                if (validated) {
                    if (oldVal != null) {
                        if (value == null)
                            addCount(-1L, -1);
                        return oldVal;
                    }
                    break;
                }
            }
        }
        return null;
    }
```

>remove删除方法步骤如下：
>
>* （1）计算hash；
>* （2）如果所在的桶不存在，表示没有找到目标元素，返回；
>* （3）如果正在扩容，则协助扩容完成后再进行删除操作；
>* （4）如果是以链表形式存储的，则遍历整个链表查找元素，找到之后再删除；
>* （5）如果是以树形式存储的，则遍历树查找元素，找到之后再删除；
>* （6）如果是以树形式存储的，删除元素之后树较小，则退化成链表；
>* （7）如果确实删除了元素，则整个map元素个数减1，并返回旧值；
>* （8）如果没有删除元素，则返回null；

# 获取方法--get()

> get方法源码解析：

```java
    public V get(Object key) {
        Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
        int h = spread(key.hashCode());
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (e = tabAt(tab, (n - 1) & h)) != null) {
            if ((eh = e.hash) == h) {
                if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                    return e.val;
            }
            else if (eh < 0)
                return (p = e.find(h, key)) != null ? p.val : null;
            while ((e = e.next) != null) {
                if (e.hash == h &&
                    ((ek = e.key) == key || (ek != null && key.equals(ek))))
                    return e.val;
            }
        }
        return null;
    }
```

> get方法执行步骤如下：
>
> * （1）hash到元素所在的桶；
> * （2）如果桶中第一个元素就是该找的元素，直接返回；
> * （3）如果是树或者正在迁移元素，则调用各自Node子类的find()方法寻找元素；
> * （4）如果是链表，遍历整个链表寻找元素；
> * （5）获取元素没有加锁；

# 获取元素个数--size()

> size方法源码解析：

```java
    public int size() {
        long n = sumCount();
        return ((n < 0L) ? 0 :
                (n > (long)Integer.MAX_VALUE) ? Integer.MAX_VALUE :
                (int)n);
    }
    

        final long sumCount() {
        CounterCell[] as = counterCells; CounterCell a;
        long sum = baseCount;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    sum += a.value;
            }
        }
        return sum;
    }

```

> size方法执行步骤如下：
>
> * （1）元素的个数依据不同的线程存在在不同的段里；（见addCounter()分析）
> * （2）计算CounterCell所有段及baseCount的数量之和；
> * （3）获取元素个数没有加锁；

# 总结

（1）ConcurrentHashMap是HashMap的线程安全版本；

（2）ConcurrentHashMap采用（数组 + 链表 + 红黑树）的结构存储元素；

（3）ConcurrentHashMap相比于同样线程安全的HashTable，效率要高很多；

（4）ConcurrentHashMap采用的锁有 synchronized，CAS，自旋锁，分段锁，volatile等；

（5）ConcurrentHashMap中没有threshold和loadFactor这两个字段，而是采用sizeCtl来控制；

（6）sizeCtl = -1，表示正在进行初始化；

（7）sizeCtl = 0，默认值，表示后续在真正初始化的时候使用默认容量；

（8）sizeCtl > 0，在初始化之前存储的是传入的容量，在初始化或扩容后存储的是下一次的扩容门槛；

（9）sizeCtl = (resizeStamp << 16) + (1 + nThreads)，表示正在进行扩容，高位存储扩容邮戳，低位存储扩容线程数加1；

（10）更新操作时如果正在进行扩容，当前线程协助扩容；

（11）更新操作会采用synchronized锁住当前桶的第一个元素，这是分段锁的思想；

（12）整个扩容过程都是通过CAS控制sizeCtl这个字段来进行的，这很关键；

（13）迁移完元素的桶会放置一个ForwardingNode节点，以标识该桶迁移完毕；

（14）元素个数的存储也是采用的分段思想，类似于LongAdder的实现；

（15）元素个数的更新会把不同的线程hash到不同的段上，减少资源争用；

（16）元素个数的更新如果还是出现多个线程同时更新一个段，则会扩容段（CounterCell）；

（17）获取元素个数是把所有的段（包括baseCount和CounterCell）相加起来得到的；

（18）查询操作是不会加锁的，所以ConcurrentHashMap不是强一致性的；

（19）ConcurrentHashMap中不能存储key或value为null的元素；

# 彩蛋——值得学习的技术

ConcurrentHashMap中有哪些值得学习的技术呢？

我认为有以下几点：

（1）CAS + 自旋，乐观锁的思想，减少线程上下文切换的时间；

（2）分段锁的思想，减少同一把锁争用带来的低效问题；

（3）CounterCell，分段存储元素个数，减少多线程同时更新一个字段带来的低效；

（4）@sun.misc.Contended（CounterCell上的注解），避免伪共享；（p.s.伪共享我们后面也会讲的^^）

（5）多线程协同进行扩容；

（6）你又学到了哪些呢？

# 彩蛋——不能解决的问题

ConcurrentHashMap不能解决什么问题呢？

请看下面的例子：

```java
private static final Map<Integer, Integer> map = new ConcurrentHashMap<>();

public void unsafeUpdate(Integer key, Integer value) {
    Integer oldValue = map.get(key);
    if (oldValue == null) {
        map.put(key, value);
    }
}
```

这里如果有多个线程同时调用unsafeUpdate()这个方法，ConcurrentHashMap还能保证线程安全吗？

答案是不能。因为get()之后if之前可能有其它线程已经put()了这个元素，这时候再put()就把那个线程put()的元素覆盖了。

那怎么修改呢？

答案也很简单，使用putIfAbsent()方法，它会保证元素不存在时才插入元素，如下：

```java
public void safeUpdate(Integer key, Integer value) {
    map.putIfAbsent(key, value);
}
```

那么，如果上面oldValue不是跟null比较，而是跟一个特定的值比如1进行比较怎么办？也就是下面这样：

```java
public void unsafeUpdate(Integer key, Integer value) {
    Integer oldValue = map.get(key);
    if (oldValue == 1) {
        map.put(key, value);
    }
}
```

这样的话就没办法使用putIfAbsent()方法了。

其实，ConcurrentHashMap还提供了另一个方法叫replace(K key, V oldValue, V newValue)可以解决这个问题。

replace(K key, V oldValue, V newValue)这个方法可不能乱用，如果传入的newValue是null，则会删除元素。

```java
public void safeUpdate(Integer key, Integer value) {
    map.replace(key, 1, value);
}
```

那么，如果if之后不是简单的put()操作，而是还有其它业务操作，之后才是put()，比如下面这样，这该怎么办呢？

```java
public void unsafeUpdate(Integer key, Integer value) {
    Integer oldValue = map.get(key);
    if (oldValue == 1) {
        System.out.println(System.currentTimeMillis());
        /**
         * 其它业务操作
         */
        System.out.println(System.currentTimeMillis());
      
        map.put(key, value);
    }
}
```

这时候就没办法使用ConcurrentHashMap提供的方法了，只能业务自己来保证线程安全了，比如下面这样：

```java
public void safeUpdate(Integer key, Integer value) {
    synchronized (map) {
        Integer oldValue = map.get(key);
        if (oldValue == null) {
            System.out.println(System.currentTimeMillis());
            /**
             * 其它业务操作
             */
            System.out.println(System.currentTimeMillis());

            map.put(key, value);
        }
    }
}
```

这样虽然不太友好，但是最起码能保证业务逻辑是正确的。

当然，这里使用ConcurrentHashMap的意义也就不大了，可以换成普通的HashMap了。

上面只是举一个简单的例子，我们不能听说ConcurrentHashMap是线程安全的，就认为它无论什么情况下都是线程安全的，还是那句话尽信书不如无书。