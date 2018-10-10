package com.hacker.dubbo.service.interfaces;

import com.hacker.dubbo.service.bean.UserAddress;

import java.util.List;

/**
 * 订单服务
 * @author Hacker
 */
public interface OrderService {
	
	/**
	 * 初始化订单
	 * @param userId
	 */
	public List<UserAddress> initOrder(String userId);

}
