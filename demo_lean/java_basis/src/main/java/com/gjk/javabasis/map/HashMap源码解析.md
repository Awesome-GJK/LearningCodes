# HashMap中的位运算

## hash(key)

> 获取key的hash值，计算公式如下：

```java
(h = key.hashCode()) ^ (h >>> 16); 
```

> 示例1：
>
> key为org/apache/xmlbeans/impl/config
>
> * key.hashCode() = -1845479676 转二进制
>   		0110 1101 1111 1111 1100 1000 1111 1100
>   1001 0010 0000 0000 0011 0111 0000 0011
>   1001 0010 0000 0000 0011 0111 0000 0100
> * h>>>16 
>       0000 0000 0000 0000 1001 0010 0000 0000
> * ^异或操作（相同为0，不同为1） 
>       1001 0010 0000 0000 1010 0101 0000 0100
>   2449515780

>示例2：
>
>key为org/apache
>
>* key.hashCode() = 537747161 转二进制
>  0010 0000 0000 1101 0101 1110 1101 1001
>* h>>>16
>  0000 0000 0000 0000 0010 0000 0000 1101
>* ^异或操作（相同为0，不同为1） 
>  0010 0000 0000 1101 0111 1110 1101 0100
>  537755348

## tab[i = (n - 1) & hash]

> 对key的hash取模，此处使用的逻辑运算，而不是使用普通计算，二进制的逻辑运算，底层运算，效率肯定会高很多。n为hashMap的size()，默认为16。取模的运算公式如下：

```java
(n-1) & hash
```

> 示例1：继续使用上面的示例1，进行计算，&(都为1，则值为1，否则为0)
>
> n-1= 15
>             0000 0000 0000 0000 0000 0000 0000 1111
>       &   1001 0010 0000 0000 1010 0101 0000 0100———————————————————————————————————————————————
>             0000 0000 0000 0000 0000 0000 0000 0100
>             = 4

> 示例2：继续使用上面的示例2，进行计算，&(都为1，则值为1，否则为0)
>
> n-1= 15
>             0000 0000 0000 0000 0000 0000 0000 1111
>       &   0010 0000 0000 1101 0111 1110 1101 0100———————————————————————————————————————————————
>             0000 0000 0000 0000 0000 0000 0000 0100
>             = 4

# 插入方法--put()

> put方法源码如下：

```java
    
    public V put(K key, V value) {
        //hash(key)参考上面位运算
        return putVal(hash(key), key, value, false, true);
    }



    static final int hash(Object key) {
        int h;
        //key的hash值 和 h转二进制的高16位 进行异或运算
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }



    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        
         //如果桶的数量为0或者桶为null,则调用resize()初始化容量
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        
        //(n - 1) & hash, 计算将数据存放在哪个桶
        //如果桶中没有数据，则新建节点吗，将数据放入桶中
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            //下面是桶中存在数据的处理方式
            Node<K,V> e; K k;
            //如果桶中第一个元素的key与待插入元素的key相同，保存到e中用于后续修改value值
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            //如果第一个元素是树节点，则调用树节点的putTreeVal插入元素
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                //遍历这个桶对应的链表，bigCount用于记录链表长度
                for (int binCount = 0; ; ++binCount) {
                    //如果找到链表尾部仍然没有找到，则往链表尾部插入新节点
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        //此时binCount长度为7，插入新节点后，还没有加1，可以理解成链表长度大于等于8
                        //当链表长度大于等于8的时候，判断是否需要将链表树化
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    //如果待插入的数据在链表中可以找到，则退出循环，此时用e记录节点，后续修改value值
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //如果找到对应的key，替换旧值为新值
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        //修改次数加1，用于快速失败机制
        ++modCount;
        //hashMap中的键值对数量加1，并判断是否大于扩容阈值
        if (++size > threshold)
            //当大于扩容阈值时，进行扩容
            resize();
        //没有任何处理
        afterNodeInsertion(evict);
        return null;
    }
```

> HashMap中插入元素步骤总结如下：
>
> * （1）计算key的hash值；
> * （2）如果数组数量为0，则初始化数组；
> * （3）如果key所在的桶没有元素，则直接插入；
> * （4）如果key所在的桶中的第一个元素的key与待插入的key相同，说明找到了元素，转后续流程（9）处理；
> * （5）如果第一个元素是树节点，则调用树节点的putTreeVal()寻找元素或插入树节点；
> * （6）如果不是以上三种情况，则遍历桶对应的链表查找key是否存在于链表中；
> * （7）如果找到了对应key的元素，则转后续流程（9）处理；
> * （8）如果没找到对应key的元素，则在链表最后插入一个新节点并判断是否需要树化；
> * （9）如果找到了对应key的元素，则判断是否需要替换旧值，并直接返回旧值；
> * （10）如果插入了元素，则数量加1并判断是否需要扩容；

## TreeNode.putTreeVal

> 
>
> TreeNode.putTreeVal向树中插入元素的方法步骤如下：
>
> * （1）寻找根节点；
> * （2）从根节点开始查找；
> * （3）比较hash值及key值，如果都相同，直接返回，在putVal()方法中决定是否要替换value值；
> * （4）根据hash值及key值确定在树的左子树还是右子树查找，找到了直接返回；
> * （5）如果最后没有找到则在树的相应位置插入元素，并做平衡；

## treeifyBin()方法

> treeifyBin方法，判断链表是否需要树化。只有桶数量大于等于64，且链表长度大于等于8时，才会进行扩容。如果只是链表长度大于等于8，而桶数量还是小于64时，可以进行扩容而不是树化，扩容时链表会拆分成两个链表达到减少链表元素的作用。
>
> 真正的树化方法为TreeNode.treeify()方法，方法步骤如下：
>
> * （1）从链表的第一个元素开始遍历；
> * （2）将第一个元素作为根节点；
> * （3）其它元素依次插入到红黑树中，再做平衡；
> * （4）将根节点移到链表第一元素的位置（因为平衡的时候根节点会改变）；

# 扩容方法--resize()

> resize方法源码如下：

```java
    final Node<K,V>[] resize() {
        //旧数组
        Node<K,V>[] oldTab = table;
        //旧容量
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        //旧扩容阈值
        int oldThr = threshold;
        //定义新容量、新扩容阈值
        int newCap, newThr = 0;
        
        if (oldCap > 0) {
            //如果旧容量已经达到了最大容量，则不再进行扩容
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            //如果旧容量扩大两倍后小于最大容量，并且就容量大于初始容量16，则将容量扩大两倍，扩容阈值也扩大两倍
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        //使用非默认构造方法创建Map,第一次插入元素会走到这里
        //如果旧容量为0且旧扩容阈值大于0，则将就扩容阈值赋值给新容量
        else if (oldThr > 0) 
            newCap = oldThr;
        else {               
            //调用默认构造方法创建的map，第一次插入元素会走到这里
            //如果旧容量和旧阈值都等于0，说明还没有进行初始化，则设置默认容量，设置默认扩容阈值
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        // 如果新扩容门槛为0，则计算为容量*装载因子，但不能超过最大容量
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        //设置扩容阈值
        threshold = newThr;
        //新建扩容后的数组，并赋值给桶
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        
        if (oldTab != null) {
            //遍历旧数组 
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                //旧数组中第一个元素不为空，则赋值给e
                if ((e = oldTab[j]) != null) {
                    //在旧数组中清空这个元素，便于GC
                    oldTab[j] = null;
                    //如果链表中只有一个元素，则计算这个元素在新桶中的位置，并搬移到新桶中
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        // 如果第一个元素是树节点，则把这颗树打散成两颗树插入到新桶中去
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        //如果这个链表不止一个元素，并且不是树，则将链表拆分成两个链表插入到新桶中
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            //比如，假如原来容量为4，3、7、11、15四个元素都在三号桶中
                            //现在扩容到8，则3和11还是在三号桶，7和15要搬移到7号桶中
                            //也就是将一个链表拆分成两个链表
                            next = e.next;
                            // (e.hash & oldCap) == 0的元素放在低位链表中
                            // 比如，3 & 4 == 0
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                // (e.hash & oldCap) != 0的元素放在高位链表中
                                // 比如，7 & 4 != 0
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        
                        //将低位链表插入新桶中，并且位置和旧桶一样
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        //将高位链表插入新桶中，位置为 旧容量+旧位置 对应的桶
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

```

> HashMap中扩容步骤总结如下：
>
> * （1）如果使用是默认构造方法，则第一次插入元素时初始化为默认值，容量为16，扩容门槛为12；
> * （2）如果使用的是非默认构造方法，则第一次插入元素时初始化容量等于扩容门槛，扩容门槛在构造方法里等于传入容量向上最近的2的n次方；
> * （3）如果旧容量大于0，则新容量等于旧容量的2倍，但不超过最大容量2的30次方，新扩容门槛为旧扩容门槛的2倍；
> * （4）创建一个新容量的桶；
> * （5）搬移元素，原链表分化成两个链表，低位链表存储在原来桶的位置，高位链表搬移到原来桶的位置加旧容量的位置；

# 取值方法--get()

> get方法源码如下：

```java
    public V get(Object key) {
        Node<K,V> e;
        //hash(key)，和插入数据一样就key的hash
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }


    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        //如果桶的数量大于0，桶不为空，且key所在桶的第一个元素不为空
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
            //检查第一个元素是否为要查找的元素，如果是直接返回
            if (first.hash == hash && 
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            
            if ((e = first.next) != null) {
                //如果第一个元素是树节点，则按树的方式查找
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                
                //否则遍历整个链表查找元素
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

```

> HashMap中取值步骤总结如下：
>
> * （1）计算key的hash值；
> * （2）找到key所在的桶及其第一个元素；
> * （3）如果第一个元素的key等于待查找的key，直接返回；
> * （4）如果第一个元素是树节点就按树的方式来查找，否则按链表方式查找；

# 总结

> * （1）HashMap是一种散列表，采用（数组 + 链表 + 红黑树）的存储结构；
> * （2）HashMap的默认初始容量为16（1<<4），默认装载因子为0.75f，容量总是2的n次方；
> * （3）HashMap扩容时每次容量变为原来的两倍；
> * （4）当桶的数量小于64时不会进行树化，只会扩容；
> * （5）当桶的数量大于64且单个桶中元素的数量大于8时，进行树化；
> * （6）当单个桶中元素数量小于6时，进行反树化；
> * （7）HashMap是非线程安全的容器；
> * （8）HashMap查找添加元素的时间复杂度都为O(1)；