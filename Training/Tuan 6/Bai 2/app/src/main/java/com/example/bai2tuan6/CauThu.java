package com.example.bai2tuan6;

public class CauThu {
    public int hinh;
    public String tencauthu;
    public String giacauthu;

    public CauThu(int hinh, String tencauthu, String giacauthu, String teamcauthu) {
        this.hinh = hinh;
        this.tencauthu = tencauthu;
        this.giacauthu = giacauthu;
        this.teamcauthu = teamcauthu;
    }
    public CauThu(){

    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTencauthu() {
        return tencauthu;
    }

    public void setTencauthu(String tencauthu) {
        this.tencauthu = tencauthu;
    }

    public String getGiacauthu() {
        return giacauthu;
    }

    public void setGiacauthu(String giacauthu) {
        this.giacauthu = giacauthu;
    }

    public String getTeamcauthu() {
        return teamcauthu;
    }

    public void setTeamcauthu(String teamcauthu) {
        this.teamcauthu = teamcauthu;
    }

    public String teamcauthu;

}
