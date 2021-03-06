package com.dahafo.web.entity;


import com.dahafo.core.IEntity;
import com.dahafo.core.util.IDGenerator;

public class UserEntity implements IEntity{


	private static final long serialVersionUID = -6238139886349796492L;

	/**
	 * 主键
	 **/
	private Long id;

	/**
	 * 名称
	 **/
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId() {
		setId(IDGenerator.getPKId());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

