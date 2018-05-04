/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.ops.health.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.framework.util.SystemConfigUtil;

/**
 * 问卷
 *
 * @author shitianshu on 2017/11/20 下午11:25.
 */
@Controller
public class QAController {

    private static final Logger LOG = LoggerFactory.getLogger(QAController.class);

    private static final String HOST = SystemConfigUtil.fetchConfig().getProperty("qa.login.host");


    @RequestMapping(value = "/qa")
    public void qa(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        HttpClient httpClient = new HttpClient();
        String requestURL = SystemConfigUtil.fetchConfig().getProperty("qa.login.path");
        PostMethod postMethod = new PostMethod(requestURL);

        postMethod.addParameter("authMethod", "Authdb");
        postMethod.addParameter("user", SystemConfigUtil.fetchConfig().getProperty("qa.login.username"));
        postMethod.addParameter("password", SystemConfigUtil.fetchConfig().getProperty("qa.login.password"));
        postMethod.addParameter("action", "login");
        postMethod.addParameter("login_submit", "login");
        postMethod.addParameter("loginlang", "default");

        try {
            int httpCode = httpClient.executeMethod(postMethod);
            Header setCookieHeader = postMethod.getResponseHeader("Set-Cookie");
            HeaderElement[] headerElements = setCookieHeader.getElements();
            for (HeaderElement headerElement : headerElements) {
                try {
                    createCookie(headerElement.getName(), headerElement.getValue(), response);
                } catch(Throwable e) {
                    continue;
                }
            }
            if ("wjck".equals(action)) {
                String redirectUrl = SystemConfigUtil.fetchConfig().getProperty("qa.url.view");
                response.sendRedirect(redirectUrl);
                return;
            }
            if ("wjdc".equals(action)) {
                String redirectUrl = SystemConfigUtil.fetchConfig().getProperty("qa.url.export");
                response.sendRedirect(redirectUrl);
                return;
            }

        } catch (IOException e) {
            LOG.error("set cookeie error.", e);
        }


    }

    private void createCookie(String cookieName, final String cookieValue, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setDomain(HOST);
        cookie.setMaxAge(-1);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
