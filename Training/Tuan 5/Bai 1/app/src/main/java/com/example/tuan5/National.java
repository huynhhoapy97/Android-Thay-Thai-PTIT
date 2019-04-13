package com.example.tuan5;

public class National {
    public String Ten;
    public String Population;
    public Integer Hinh;

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getPopulation() {
        return Population;
    }

    public void setPopulation(String population) {
        Population = population;
    }

    public Integer getHinh() {
        return Hinh;
    }

    public void setHinh(Integer hinh) {
        Hinh = hinh;
    }

    public National(String ten, String population, Integer hinh) {
        Ten = ten;
        Population = population;
        Hinh = hinh;
    }
}

