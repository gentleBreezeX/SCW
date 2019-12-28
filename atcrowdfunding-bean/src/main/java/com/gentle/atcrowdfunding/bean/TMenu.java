package com.gentle.atcrowdfunding.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TMenu implements Serializable{
    private Integer id;

    private Integer pid;

    private String name;

    private String icon;

    private String url;
    
    //给父类菜单添加子菜单
    private List<TMenu> childrenMenus = new ArrayList<TMenu>();

    @Override
	public String toString() {
		return "TMenu [id=" + id + ", pid=" + pid + ", name=" + name + ", icon=" + icon + ", url=" + url
				+ ", childrenMenus=" + childrenMenus + "]";
	}

	public List<TMenu> getChildrenMenus() {
		return childrenMenus;
	}

	public void setChildrenMenus(List<TMenu> childrenMenus) {
		this.childrenMenus = childrenMenus;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}