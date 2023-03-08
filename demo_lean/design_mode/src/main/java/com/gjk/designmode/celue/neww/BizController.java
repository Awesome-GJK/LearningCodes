package com.gjk.designmode.celue.neww;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

/**
 * BizController
 *
 * @author: GJK
 * @date: 2022/3/29 15:12
 * @description:
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BizController {

    private final BizService bizService;

    @PostMapping("/v1/biz/testMuti")
    public String test1(String order, Integer level) {
        return bizService.getCheckResultComX(order, level);
    }
}
