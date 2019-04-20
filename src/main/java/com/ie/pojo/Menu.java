package com.ie.pojo;

/**
 * 菜单类
 */
public class Menu {

	private double menuId;

	private String menuName;

	private String menuURL;

	private String menuCSS;

	public double getMenuId() {
		return menuId;
	}

	public void setMenuId(double menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	public String getMenuCSS() {
		return menuCSS;
	}

	public void setMenuCSS(String menuCSS) {
		this.menuCSS = menuCSS;
	}

	@Override
	public String toString() {
		return "Menu{" +
				"menuId=" + menuId +
				", menuName='" + menuName + '\'' +
				", menuURL='" + menuURL + '\'' +
				", menuCSS='" + menuCSS + '\'' +
				'}';
	}
}
