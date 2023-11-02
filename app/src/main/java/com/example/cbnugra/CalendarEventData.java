package com.example.cbnugra;

public class CalendarEventData {
    public String date;
    public String data;

    public CalendarEventData(){} // 기본 생성자

    public CalendarEventData(String date, String data) {
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
