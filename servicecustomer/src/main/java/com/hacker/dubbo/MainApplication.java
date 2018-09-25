package com.hacker.dubbo;

import com.hacker.dubbo.service.bean.UserAddress;
import com.hacker.dubbo.service.interfaces.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author Hacker
 * @date：2018/9/22
 * @project project
 * @describe
 */
public class MainApplication {

    public static void main(String[] args) throws  Exception{
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-customer.xml");

        OrderService orderService = ctx.getBean(OrderService.class);
        List<UserAddress> userAddresses = orderService.initOrder("10001");
        System.out.println(userAddresses);

        /**
         * 他是阻塞用的
         */
        System.in.read();
    }
}
