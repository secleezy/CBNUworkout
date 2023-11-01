package com.example.cbnugra;

import java.time.LocalDate;

//클릭 인터페이스 선언, 구현은 mainhome_calendar.java에서
public interface OnItemListener {
    void onItemClick(LocalDate dayText);
}
