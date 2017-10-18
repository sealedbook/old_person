/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.framework.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shitianshu on 2017/10/18 下午11:41.
 */
@Controller
public class SystemController {

    @RequestMapping("/ruok")
    @ResponseBody
    public String isAlive() {
        return "OK";
    }

}
