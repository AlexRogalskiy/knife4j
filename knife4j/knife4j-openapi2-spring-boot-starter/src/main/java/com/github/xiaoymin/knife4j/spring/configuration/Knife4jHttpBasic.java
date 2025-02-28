/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://doc.xiaominfo.com.
 */

package com.github.xiaoymin.knife4j.spring.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

/***
 * HTTP basic properties
 * @since:knife4j 1.9.6
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2019/08/27 15:40
 */
@ConfigurationProperties(prefix = "knife4j.basic")
public class Knife4jHttpBasic {

    /**
     * Whether to enable HTTP basic authentication, the default is false
     */
    private boolean enable=false;

    /**
     * HTTP basic username
     */
    private String username;

    /**
     * HTTP basic password
     */
    private String password;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
