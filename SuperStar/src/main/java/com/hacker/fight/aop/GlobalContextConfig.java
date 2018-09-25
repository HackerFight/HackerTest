package com.hacker.fight.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author ZhaZhaHui
 * @date：2018/9/14
 * @project project
 * @describe
 */
@Configuration
@ComponentScan(basePackages = "com.hacker.fight.aop")
//这个千万不要忘了，他就等同于以前在 配置文件中： <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
@EnableAspectJAutoProxy
public class GlobalContextConfig {

}