package com.gjk.javabasis.juc.completableFuture;

import lombok.Builder;
import lombok.Data;

/**
 * Fruit
 *
 * @author: gaojiankang
 * @date: 2023/2/25 15:07
 * @description:
 */
@Data
@Builder
public class Fruit {

    private Integer appleCount;

    private Integer orangeCount;

    private Integer bananaCount;

}
