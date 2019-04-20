package com.ie.service;

import com.ie.pojo.Menu;

import java.util.List;

/**
 * Menu 业务层接口
 */
public interface IMenuService {

	List<Menu> findAllMenu();
}
