package com.hacker.fight.anno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhaZhaHui
 * @date：2018/9/19
 * @project project
 * @describe
 */

@Configuration
public class ApplicationContextConfig {

    @Bean("person")
    public Person getPerson(){
        return new Person();
    }
}
