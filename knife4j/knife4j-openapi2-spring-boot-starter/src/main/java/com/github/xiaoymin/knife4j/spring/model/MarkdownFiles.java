/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.github.xiaoymin.knife4j.spring.model;

import com.github.xiaoymin.knife4j.core.extend.OpenApiExtendMarkdownChildren;
import com.github.xiaoymin.knife4j.spring.util.MarkdownUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * @since:swagger-bootstrap-ui 1.9.3
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2019/04/17 19:54
 */
public class MarkdownFiles {

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    Logger logger= LoggerFactory.getLogger(MarkdownFiles.class);

    /***
     * markdown files dir
     */
    private String basePath;

    private List<OpenApiExtendMarkdownChildren> markdownFiles=new ArrayList<>();

    public List<OpenApiExtendMarkdownChildren> getMarkdownFiles() {
        return markdownFiles;
    }

    public void setMarkdownFiles(List<OpenApiExtendMarkdownChildren> markdownFiles) {
        this.markdownFiles = markdownFiles;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public MarkdownFiles() {
    }

    public MarkdownFiles(String basePath) {
        this.basePath = basePath;
    }

    public void init(){
        //初始化
        if (basePath!=null&&basePath!=""&&!"".equals(basePath)){
            try {
                Resource[] resources=resourceResolver.getResources(basePath);
                if (resources!=null&&resources.length>0){
                    for (Resource resource:resources){
                        OpenApiExtendMarkdownChildren markdownFile=createMarkdownFile(resource);
                        if (markdownFile!=null){
                            getMarkdownFiles().add(markdownFile);
                        }
                    }
                }
            } catch (Exception e) {
                logger.warn("(Ignores) Failed to read Markdown files,Error Message:{} ",e.getMessage());
            }
        }
    }

    private OpenApiExtendMarkdownChildren createMarkdownFile(Resource resource){
        return MarkdownUtils.resolveMarkdownResource(resource);
    }
}
