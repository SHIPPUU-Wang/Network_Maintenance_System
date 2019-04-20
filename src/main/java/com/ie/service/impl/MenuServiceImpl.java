package com.ie.service.impl;

import com.ie.dao.MenuMapper;
import com.ie.pojo.Menu;
import com.ie.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("menuService")
public class MenuServiceImpl implements IMenuService {

	@Resource(name = "menuDao")
	private MenuMapper menuDao;

	@Override
	public List<Menu> findAllMenu() {
		return menuDao.findAllMenu();
	}
}
