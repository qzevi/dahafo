package com.dahafo.web.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dahafo.web.dao.IUserDao;
import com.dahafo.web.entity.UserEntity;
import com.dahafo.web.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private IUserDao userDao;

	/**
	 * 新增对象
	 * 前置条件： 对象不能为null
	 * @param userEntity
	 */
	@Override
	public boolean insert(UserEntity user){
		try{
			if(user!=null){
				userDao.insert(user);
				return true;
			}
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			LOGGER.info(e.getMessage());
			return false;
		}
	}
	/**
	 * 根据ID更新对象（更新所有属性）
	 * 前置条件： 对象不能为null
	 * @param userEntity
	 */
	@Override
	public boolean update(UserEntity user){
		try{
			if(user!=null){
				userDao.update(user);
				return true;
			}
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			LOGGER.info(e.getMessage());
			return false;
		}
	}
	/**
	 * 根据ID删除对象
	 * 前置条件： 对象不能为null
	 * @param userEntity
	 */
	@Override
	public boolean delete(UserEntity user){
		try{
			if(user!=null){
				userDao.delete(user);
				return true;
			}
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			LOGGER.info(e.getMessage());
			return false;
		}
	}

	/**
	 * 根据对象查询
	 * 前置条件： 对象不能为null
	 * @param idEntity
	 */
	@Override
	public List<UserEntity> selectAll(UserEntity user){
		try{
			if(user!=null){
				List<UserEntity> users = userDao.selectAll(user);
				return users;
			}
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	

}

