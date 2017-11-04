/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.framework.system.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author shitianshu on 2017/11/3 下午11:09.
 */
public class TrackLogInterceptor extends HandlerInterceptorAdapter {

    private static final String LOG_TRACK_KEY = "LOG_TRACK_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        MDC.put(LOG_TRACK_KEY, UUID.randomUUID().toString().replaceAll("-", ""));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        MDC.remove(LOG_TRACK_KEY);
    }
}
