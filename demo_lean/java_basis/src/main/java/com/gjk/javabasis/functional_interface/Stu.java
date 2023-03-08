package com.gjk.javabasis.functional_interface;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stu
 *
 * @author: gaojiankang
 * @date: 2022/9/9 13:46
 * @description:
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stu {

    private String id;
    private String name;
    private String age;
    private String parent;
}
