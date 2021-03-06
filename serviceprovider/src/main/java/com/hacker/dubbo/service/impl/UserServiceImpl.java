package com.hacker.dubbo.service.impl;

import com.hacker.dubbo.service.bean.UserAddress;
import com.hacker.dubbo.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Hacker
 * @date：2018/9/21
 * @project project
 * @describe
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    /**
     * @param userId
     * @return
     */
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress userAddress = new UserAddress(1, "浙江省杭州市西湖区", userId, "Hacker", "18368116334", "1");
        UserAddress userAddress2 = new UserAddress(2, "浙江省杭州市滨江区", userId, "渣渣辉", "18368116334", "0");
        return Arrays.asList(userAddress, userAddress2);
    }
}
