package com.gjk.javabasis.juc.completableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * CompletableFutureFunction
 *
 * @author: gaojiankang
 * @date: 2022/12/26 9:38
 * @description:
 */
@Slf4j
public class CompletableFutureFunction {




    public final ExecutorService executor = new ThreadPoolExecutor(
            2,
            4,
            10L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            new ThreadFactoryBuilder().setNameFormat("Completable-Future-Function-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    public void testRunAfterEither() {
        System.out.println("开始***");

        System.out.println("小明和小黄分别在家做饭，小红等着吃饭！！！");
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.runAsync() --- 小明正在做饭");
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " --- CompletableFuture.runAsync() --- 小明饭做好了");
            return "小明做的饭好了";
        }, executor).runAfterEitherAsync(CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.runAsync() --- 小黄正在做饭");
            try {
                TimeUnit.SECONDS.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " --- CompletableFuture.runAsync() --- 小黄饭做好了");
            return "小黄做的饭好了";
        }, executor), () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.applyToEitherAsync() --- 小红吃饭了");
        }, executor);

        completableFuture.join();
        System.out.println("结束***");
    }

    public void testAcceptEither() {
        System.out.println("开始***");
        System.out.println("张三等电梯上楼");
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.supplyAsync() --- 1号电梯下行中");
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1号电梯";
        }, executor).acceptEitherAsync(CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.supplyAsync() --- 2号电梯下行中");
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "2号电梯";
        }, executor), result -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.applyToEitherAsync() --- " + result + "到达");
            System.out.println(name + " --- CompletableFuture.applyToEitherAsync() --- 张三进入" + result);
        }, executor);

        completableFuture.join();
        System.out.println("结束***");
    }

    public void testApplyToEither() {
        System.out.println("开始***");
        System.out.println("张三等电梯上楼");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.supplyAsync() --- 1号电梯下行中");
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1号电梯";
        }, executor).applyToEitherAsync(CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.supplyAsync() --- 2号电梯下行中");
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "2号电梯";
        }, executor), result -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.applyToEitherAsync() --- " + result + "到达");
            return "张三上了" + result;
        }, executor);

        System.out.println(completableFuture.join());
        System.out.println("结束***");
    }

    public void testRunAfterBoth(){
        System.out.println("开始***");

        CompletableFuture<String> pot = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- CompletableFuture.supplyAsync() --- 洗锅");
            return "锅";
        }, executor);

        CompletableFuture<String> meter = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- CompletableFuture.runAsync() --- 洗米");
            return "米";
        },executor);

        CompletableFuture<Void> completableFuture1 = meter.runAfterBoth(pot, () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.runAfterBoth --- 无参数，无返回值");
        });

        CompletableFuture<Void> completableFuture2 = meter.runAfterBothAsync(pot, () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.runAfterBothAsync --- 无参数，无返回值");
        });

        CompletableFuture<Void> completableFuture3 = meter.runAfterBothAsync(pot, () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.runAfterBothAsync + executor --- 无参数，无返回值");
        }, executor);
        CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3).join();
        System.out.println("结束***");
    }

    public void testThenAcceptBoth(){
        System.out.println("开始***");

        CompletableFuture<String> pot = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- CompletableFuture.supplyAsync() --- 洗锅");
            return "锅";
        }, executor);

        CompletableFuture<String> meter = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- CompletableFuture.runAsync() --- 洗米");
            return "米";
        },executor);

        CompletableFuture<Void> rice1 = meter.thenAcceptBoth(pot, (v1, v2) -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- pot,Meter --- 获取'" + v2 + "'和'" + v1 + "'后开始煮饭");
        });

        CompletableFuture<Void> rice2 = meter.thenAcceptBothAsync(pot, (v1, v2) -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- pot,Meter --- 获取'" + v2 + "'和'" + v1 + "'后开始煮饭");
        });

        CompletableFuture<Void> rice3 = meter.thenAcceptBothAsync(pot, (v1, v2) -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" executor --- pot,Meter --- 获取'" + v2 + "'和'" + v1 + "'后开始煮饭");
        },executor);
        CompletableFuture.allOf(rice1, rice2, rice3).join();
        System.out.println("结束***");
    }

    public void testThenCombine(){
        System.out.println("开始***");

        CompletableFuture<String> pot = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- CompletableFuture.supplyAsync() --- 洗锅");
            return "锅";
        }, executor);

        CompletableFuture<String> meter = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- CompletableFuture.runAsync() --- 洗米");
            return "米";
        },executor);

        CompletableFuture<String> rice1 = meter.thenCombine(pot, (v1, v2) -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- pot,Meter --- 获取'" + v2 + "'和'" + v1 + "'后开始煮饭");
            return "白米饭 + thenCombine";
        });

        CompletableFuture<String> rice2 = meter.thenCombineAsync(pot, (v1, v2) -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- pot,Meter --- 获取'" + v2 + "'和'" + v1 + "'后开始煮饭");
            return "白米饭 + thenCombineAsync";
        });

        CompletableFuture<String> rice3 = meter.thenCombineAsync(pot, (v1, v2) -> {
            String name = Thread.currentThread().getName();
            System.out.println(name +" --- pot,Meter --- 获取'" + v2 + "'和'" + v1 + "'后开始煮饭");
            return "白米饭 + thenCombineAsync + executor";
        },executor);
        List<String> collect = Stream.of(rice1, rice2, rice3).map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println("result: " + JSON.toJSONString(collect));
        System.out.println("结束***");
    }

    public void testThenApply() {
        System.out.println("main --- 开始***");

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.supplyAsync --- Hello World");
            return "Hello World";
        }).thenApplyAsync(s -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.thenApplyAsync ---" + s + "gjk");
            return s + "gjk";
        }, executor).thenApply(r -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.thenApply ---" + r);
            return r;
        });
        System.out.println("main --- completableFuture.join前***");
        System.out.println(completableFuture.join());
        System.out.println("main --- 结束***");
    }

    public void testThenAccept() {
        System.out.println("main --- 开始***");

        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.supplyAsync --- Hello World");

            return "Hello World";
        }, executor).thenAcceptAsync(s -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.thenAcceptAsync --- Hello World" + s);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenAccept(s ->{
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.thenAccept --- Hello World");
        });

        System.out.println("main --- completableFuture.join前***");
        completableFuture.join();
        System.out.println("main --- 结束***");
    }

    public void testThenRun() {
        System.out.println("main --- 开始***");

        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.supplyAsync --- Hello World");
            return "Hello World";
        }, executor).thenRunAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.thenRunAsync --- Hello World");
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRun(() ->{
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.thenRun --- Hello World");
        });

        System.out.println("main --- completableFuture.join前***");
        completableFuture.join();
        System.out.println("main --- 结束***");
    }

    public void testThenCompose() {
        System.out.println("main --- 开始***");

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.supplyAsync --- Hello World");
            return "Hello World";
        }, executor).thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " --- CompletableFuture.thenComposeAsync ---" + s + " gjk");
            return s + " gjk";
        })).thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            String s1 = s.toUpperCase();
            System.out.println(name + " --- CompletableFuture.thenCompose ---" + s1);
            return s1;
        }));

        System.out.println("main --- completableFuture.join前***");
        System.out.println(completableFuture.join());
        System.out.println("main --- 结束***");
    }


    public void testExceptionally(){
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 7 / 0)
                .thenApply(r -> r * 10)
                //异常被exceptionally捕获，并且输出 0 ，等同于try-catch
                .exceptionally(e -> 0);
        System.out.println(completableFuture.join());
    }

    public void testWhenComplete(){
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 7 / 1)
                        .whenComplete((x,y) -> {
                            System.out.println(x);
                            if(y != null){
                                System.out.println("error:" + y.getMessage() );
                            }
                        });

        System.out.println(completableFuture.join());
    }

    public void testHandle(){
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 7 / 0)
                .handle((x,y) -> {
                    if(y != null){
                        System.out.println("error:" + y.getMessage() );
                        return 0;
                    }
                    return x;
                });

        System.out.println(completableFuture.join());
    }

    public static void main(String[] args) {
        CompletableFutureFunction function = new CompletableFutureFunction();
        function.testHandle();
    }

}
