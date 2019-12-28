package com.gentle.atcrowdfunding.bean;

public class TRole {
    private Integer id;

    private String name;

    @Override
	public String toString() {
		return "TRole [id=" + id + ", name=" + name + "]";
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}