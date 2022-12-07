package com.example.cbnugra;

//테이블이라고 생각하고, 테이블에 들어갈 속성값을 넣기
//파이어베이스는 RDBMS와 다르기 때문에 테이블이라는 개념이 없음. 원래는 키값이라고 부름
public class userworkout {

    private String ID; // 아이디
    private String year;
    private String month;
    private String day;
    private String workoutname;
    private String kcal;

    public userworkout(){} // 생성자 메서드


    //getter, setter 설정
    public String getID() {
        return ID;
    }
    public void setID(String name) {
        this.ID = ID;
    }


    //값을 추가할때 쓰는 함수, MainActivity에서 add함수에서 사용할 것임.
    public userworkout(String ID){
        this.ID = ID;

    }
}
