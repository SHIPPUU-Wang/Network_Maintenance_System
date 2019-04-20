package com.ie.dao;

import com.ie.pojo.Menu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Menu Mapper接口
 */

@Repository("menuDao")
public interface MenuMapper {


	/*@Results({
			@Result(property = "menuId", column = "menu_id"),
			@Result(property = "menuName", column = "menu_name"),
			@Result(property = "menuURL", column = "menu_url")
			@Result(property = "menuCSS", column = "menu_css")
	})*/
	@Select("SELECT * FROM menu")
	List<Menu> findAllMenu();
}
