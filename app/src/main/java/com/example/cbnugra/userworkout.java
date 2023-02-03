package com.example.cbnugra;

//테이블이라고 생각하고, 테이블에 들어갈 속성값을 넣기
//파이어베이스는 RDBMS와 다르기 때문에 테이블이라는 개념이 없음. 원래는 키값이라고 부름
public class userworkout {

    private String ID; // 아이디
    private String time_log; //현재시각
    private String Year; //날짜
    private String Month;
    private String Day;
    private String workoutname; //운동이름
    private String kcal; //칼로리

    public userworkout(){} // 생성자 메서드


    //getter, setter 설정
    public String getID() {
        return ID;
    }
    public void setID(String name) {
        this.ID = ID;
    }

    public String gettime_log() {
        return time_log;
    }
    public void settime_log(String name) {
        this.time_log = time_log;
    }

    public String getYear() {
        return Year;
    }
    public void setYear(String name) {
        this.Year = Year;
    }

    public String getMonth() {
        return Month;
    }
    public void setMonth(String name) {
        this.Month = Year;
    }

    public String getDay() {
        return Day;
    }
    public void setDay(String name) {
        this.Day = Day;
    }

    public String getworkoutname() {
        return workoutname;
    }
    public void setworkoutname(String name) {
        this.workoutname = workoutname;
    }

    public String getkcal() {
        return kcal;
    }
    public void setkcal(String name) {
        this.Day = kcal;
    }


    //값을 추가할때 쓰는 함수, MainActivity에서 add함수에서 사용할 것임.
    public userworkout(String ID, String time_log, String Year,String Month,String Day,String workoutname,String kcal){
        this.ID = ID;
        this.time_log=time_log;
        this.Year = Year;
        this.Month=Month;
        this.Day=Day;
        this.workoutname=workoutname;
        this.kcal=kcal;
    }
}
