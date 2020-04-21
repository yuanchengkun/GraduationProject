package com.edu.cuit.competition_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class CompetitionManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompetitionManagementSystemApplication.class, args);
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(30));
        //该方法已降级
        //factory.setMaxRequestSize("30MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(120));
        return factory.createMultipartConfig();
    }

}
