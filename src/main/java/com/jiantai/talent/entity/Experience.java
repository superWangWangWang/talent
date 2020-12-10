package com.jiantai.talent.entity;

public class Experience {
    //idint(11) NOT NULL
    private Integer id;
    //namevarchar(32) NULL工作年限
    private String name;

    @Override
    public String toString() {
        return "experience{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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
        this.name = name;
    }
}
