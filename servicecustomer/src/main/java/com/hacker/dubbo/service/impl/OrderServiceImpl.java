package com.hacker.dubbo.service.impl;

import com.hacker.dubbo.service.bean.UserAddress;
import com.hacker.dubbo.service.interfaces.OrderService;
import com.hacker.dubbo.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hacker
 * @date：2018/9/22
 * @project project
 * @describe
 *     1.将服务提供者注册到服务中心
 *         1）导入dubbo的依赖
 *         2）注册中心使用的zookeeper，引入操作zookeeper的客户端
 *     2.让服务消费者去注册中心订阅服务提供者的服务地址
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    UserService userService;

    public List<UserAddress> initOrder(String userId) {
        return userService.getUserAddressList(userId);
    }
}
