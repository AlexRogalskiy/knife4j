/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */
package com.github.xiaoymin.knife4j.aggre.core;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.aggre.cloud.CloudRoute;
import com.github.xiaoymin.knife4j.aggre.core.pojo.BasicAuth;
import com.github.xiaoymin.knife4j.aggre.core.pojo.CommonAuthRoute;
import com.github.xiaoymin.knife4j.aggre.core.pojo.SwaggerRoute;

import java.util.List;
import java.util.Optional;

/***
 *
 * @since:knife4j-aggregation-spring-boot-starter 2.0.8
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2020/10/29 20:09
 */
public interface RouteRepository {
    /**
     * start心跳监听程序
     */
    default void start(){}

    /**
     * stop心跳监听乘车
     */
    default void close(){}

    /**
     * 校验请求Header是否正确
     * @param header 请求头
     * @return 是否校验成功
     */
    boolean checkRoute(String header);

    /**
     * 根据请求header获取
     * @param header 请求头
     * @return 服务Route
     */
    SwaggerRoute getRoute(String header);

    /**
     * 获取所有
     * @return 返回所有Routes服务
     */
    List<SwaggerRoute> getRoutes();

    /**
     * 根据Header请求头获取Basic基础信息
     * @param header 请求头
     * @return Basic基础信息
     */
    default BasicAuth getAuth(String header){return null;}

    /**
     * 获取route中配置的Basic信息
     * @param header 请求头
     * @param commonAuthRoutes routes集合
     * @return Basic基础信息
     */
    default BasicAuth getAuthByRoute(String header, List<? extends CommonAuthRoute> commonAuthRoutes){
        BasicAuth basicAuth=null;
        if (CollectionUtil.isNotEmpty(commonAuthRoutes)){
            //判断route中是否设置了basic，如果route中存在，则以route中为准
            Optional<? extends CommonAuthRoute> cloudRouteOptional=commonAuthRoutes.stream().filter(cloudRoute -> StrUtil.equalsIgnoreCase(cloudRoute.pkId(),header)).findFirst();
            if (cloudRouteOptional.isPresent()){
                CommonAuthRoute route=cloudRouteOptional.get();
                if (route.getRouteAuth()!=null&&route.getRouteAuth().isEnable()){
                    basicAuth=route.getRouteAuth();
                }
            }
        }
        return basicAuth;
    }
}
