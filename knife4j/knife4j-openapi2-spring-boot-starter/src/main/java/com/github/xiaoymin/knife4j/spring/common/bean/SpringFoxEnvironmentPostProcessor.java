/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: https://www.xiaominfo.com.
 * Developer Web Site: https://doc.xiaominfo.com.
 */

package com.github.xiaoymin.knife4j.spring.common.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultPropertiesPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @since:knife4j 4.0.0
 * @auth <a href="xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2022/8/11 21:52
 */
public class SpringFoxEnvironmentPostProcessor implements EnvironmentPostProcessor {

    /**
     * 配置名称key值
     */
    final static String SPRING_MVC_MATCHING_STRATEGY="spring.mvc.pathmatch.matching-strategy";

    /**
     * 当前策略枚举类全路径
     */
    final static String MATCHING_CLASS_NAME="org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.MatchingStrategy";
    Logger logger= LoggerFactory.getLogger(SpringFoxEnvironmentPostProcessor.class);

    /**
     * 处理springfox2.x版本兼容高版本Spring Boot出现空指针的异常的情况，为开发者自动配置matchingStrategy策略(如果开发者没要求的情况下)
     * springfox2 默认使用{@link org.springframework.util.AntPathMatcher}
     * @param environment Spring Boot环境上下文环境对象
     * @param application Spring Boot主程序Application
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        logger.debug("springfox-post-processor..");
        //首先，判断当前Spring Boot版本
        //高版本才处理,自2.4.0开始提供MatchingStrategy
        //判断当前类是否存在
        if (ClassUtils.isPresent(MATCHING_CLASS_NAME,ClassUtils.getDefaultClassLoader())){
            logger.debug("Spring Boot Version Getter than 2.4.0,handle MatchingStrategy");
            String matchingStrategy=environment.getProperty(SPRING_MVC_MATCHING_STRATEGY);
            if (matchingStrategy==null||"".equals(matchingStrategy)){
                //当前对象为空，给定默认值ant_path_matcher
                Map<String,Object> sources=new HashMap<>();
                //springfox使用的策略是AntPathMatcher
                sources.put(SPRING_MVC_MATCHING_STRATEGY,"ant_path_matcher");
                DefaultPropertiesPropertySource defaultPropertiesPropertySource=new DefaultPropertiesPropertySource(sources);
                //更新，添加一个默认值
                environment.getPropertySources().addLast(defaultPropertiesPropertySource);
            }
        }
    }
}
