package com.hacker.dubbo.service.interfaces;

import com.hacker.dubbo.service.bean.UserAddress;

import java.util.List;

/**
 * 用户服务
 * @author Hacker
 */
public interface UserService {
	
	/**
	 * 按照用户id返回所有的收货地址
	 * @param userId
	 */
	public List<UserAddress> getUserAddressList(String userId);

}
