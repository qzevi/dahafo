package com.dahafo.web.service;

import java.util.List;
import com.dahafo.web.entity.UserEntity;

public interface IUserService {

	/**
	 * 新增对象
	 * 前置条件： 对象不能为null
	 * @param user
	 */
	public boolean insert(UserEntity userEntity);
	

	/**
	 * 根据ID更新对象（更新所有属性）

	 * 前置条件： 对象不能为null
	 * @param user
	 */
	public boolean update(UserEntity userEntity);
	

	/**
	 * 根据ID删除对象
	 * 前置条件： 对象不能为null
	 * @param user
	 */
	public boolean delete(UserEntity userEntity);
	

	/**
	 * 根据ID查询对象
	 * 前置条件： 对象不能为null
	 * @param id
	 */
	public List<UserEntity> selectAll(UserEntity userEntity);
	
}

