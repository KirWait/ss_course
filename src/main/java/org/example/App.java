package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;
import java.util.logging.LogManager;

@EnableFeignClients
@SpringBootApplication
@EnableAspectJAutoProxy
public class App 
{

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration();
        SpringApplication.run(App.class, args);
    }

}
