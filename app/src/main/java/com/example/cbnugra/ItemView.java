package com.example.cbnugra;

public class ItemView {
    public String date;
    public String data;

    public ItemView(){} // 기본 생성자

    public ItemView(String date, String data) {
        this.date = date;
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public String getData() {
        return data;
    }
}
