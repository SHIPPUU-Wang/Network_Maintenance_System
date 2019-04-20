package com.ie.controller;

import com.ie.pojo.Menu;
import com.ie.service.impl.MenuServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 */
@RestController
@CrossOrigin
public class MenuController {

	@Resource(name = "menuService")
	private MenuServiceImpl menuService;

	@RequestMapping("/menu")
	public Map<String, Object> menu() {

		// 通过数据库所查询的原始数据menus
		List<Menu> menus = menuService.findAllMenu();

		// findMap 最终返回的Map
		Map<String, Object> findMap = new HashMap<>();
		// findMap中唯一的元素：findList
		List<Object> findList = new ArrayList<>();
		// 遍历menus集合时，i 用于分组
		int i = 1;
		// 向每个findList的元素 存入 Map集合 element，element分为title、other
		Map<String, Object> element = new HashMap<>();
		// 将list存入 element的other元素中
		List<Menu> list = new ArrayList<>();
		for (Menu m : menus) {

			if (i == m.getMenuId()) {
				element.put("title", m);
			} else if (i < m.getMenuId() & i + 1 > m.getMenuId()) {
				list.add(m);
			} else {
				// 将本组list存入 当前element的other元素中
				element.put("other", list);
				// 将本组得到的element Map集合存入findList中
				findList.add(element);
				// 上一组数据处理完成，对下组数据进行环境初始化
				element = new HashMap<>();
				list = new ArrayList<>();
				// 当前所遍历的Menu的menuId为整数，存入element的title元素中
				element.put("title", m);
				i++;
			}
		}

		// 将最后一个的list放入Map集合element中的other元素
		element.put("other", list);
		// 将最后一个element放入findList中
		findList.add(element);

		// 遍历findList
		/*for (List ll: findList ) {
			System.out.println(ll);
		}*/

		// 将findList放入findMap中
		findMap.put("menu", findList);
		return findMap;
	}
}

