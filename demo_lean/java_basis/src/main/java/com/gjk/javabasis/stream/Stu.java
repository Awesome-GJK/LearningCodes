package com.gjk.javabasis.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname Stu
 * @Description
 * @Date 2024/2/18 16:07
 * @Created by gaojiankang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stu {

    String className;
    Double ywCore;
    Double sxCore;
}
