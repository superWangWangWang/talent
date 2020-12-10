package com.jiantai.talent.entity;

import java.util.Date;

/**
 * 实体类 - 公告
 */
public class Notice {
    //id           int(11)
    private Integer id;
    //show         int(1)         0=显示，1=删除
    private Integer show;
    //content      varchar(256)   公告内容
    private String content;
    //create_time  timestamp      创建时间
    private Date createTime;
    //update_time  timestamp      修改时间
    private Date updateTime;
    //watched      int(11)        浏览数
    private Integer watched;

    private String title;

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", show=" + show +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", watched=" + watched +
                ", title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }
}
