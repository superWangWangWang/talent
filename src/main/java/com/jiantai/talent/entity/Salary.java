package com.jiantai.talent.entity;

public class Salary {
    //idint(11) NOT NULL
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "Salary{" +
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
