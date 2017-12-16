/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * 基线队列编号
 *
 * @author shitianshu on 2017/12/16 下午1:18.
 */
public class BaseQueueCodeUtil {

    private static final Pattern BASE_CODE_PATTERN = Pattern.compile("^[0-9]{2}[0-9A-Z]{4}[0-9]{5}WJ[0-9]*$");

    public static boolean isRight(String baseCode) {
        if (!StringUtils.hasText(baseCode)) {
            return false;
        }
        Matcher matcher = BASE_CODE_PATTERN.matcher(baseCode);
        return matcher.find();
    }

}
