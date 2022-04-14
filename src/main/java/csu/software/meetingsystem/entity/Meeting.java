package csu.software.meetingsystem.entity;

import java.sql.Date;

public class Meeting {
    private int id;
    private Date date;
    private int week;
    private int weekday;
    private int roomId;
    private String eight;
    private String ten;
    private String twelve;
    private String fourteen;
    private String sixteen;
    private String eighteen;
    private String twenty;
    private String twentyTwo;

    public Meeting() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getEight() {
        return eight;
    }

    public void setEight(String eight) {
        this.eight = eight;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTwelve() {
        return twelve;
    }

    public void setTwelve(String twelve) {
        this.twelve = twelve;
    }

    public String getFourteen() {
        return fourteen;
    }

    public void setFourteen(String fourteen) {
        this.fourteen = fourteen;
    }

    public String getSixteen() {
        return sixteen;
    }

    public void setSixteen(String sixteen) {
        this.sixteen = sixteen;
    }

    public String getEighteen() {
        return eighteen;
    }

    public void setEighteen(String eighteen) {
        this.eighteen = eighteen;
    }

    public String getTwenty() {
        return twenty;
    }

    public void setTwenty(String twenty) {
        this.twenty = twenty;
    }

    public String getTwentyTwo() {
        return twentyTwo;
    }

    public void setTwentyTwo(String twentyTwo) {
        this.twentyTwo = twentyTwo;
    }
}
