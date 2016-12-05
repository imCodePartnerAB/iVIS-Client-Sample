package com.imcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class IvisClientSample extends SpringBootServletInitializer {

    private static Class<IvisClientSample> application = IvisClientSample.class;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(application);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(application, args);
    }

}