package com.demo;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * create on 2020-03-15
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        //单机模式
        config.useSingleServer().setAddress("123.56.164.168").setDatabase(0);

        //集群模式
        //config.useClusterServers().addNodeAddress()

        return (Redisson) Redisson.create(config);
    }
}
