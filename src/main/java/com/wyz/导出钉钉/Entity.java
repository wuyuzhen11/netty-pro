package com.wyz.导出钉钉;

import java.util.Date;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-12 17:02
 **/

public class Entity {
    private String date;

    private String startDate;

    private String endDate;

    private String type;

    private double jiange;

    private String userName;

    private String part;

    private Date start;

    private Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public double getJiange() {
        return jiange;
    }

    public void setJiange(double jiange) {
        this.jiange = jiange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
