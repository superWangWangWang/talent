package com.jiantai.talent.vo;

import java.util.List;

/**
 * 封装数据回显前端的对象
 */
public class ResultVo {
    private Integer code = 1;//0=正常，1=异常
    private String msg;
    private List dataList;
    private String userType;

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", dataList=" + dataList +
                ", userType='" + userType + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
