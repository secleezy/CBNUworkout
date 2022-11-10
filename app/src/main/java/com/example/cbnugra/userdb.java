package com.example.cbnugra;

//테이블이라고 생각하고, 테이블에 들어갈 속성값을 넣기
//파이어베이스는 RDBMS와 다르기 때문에 테이블이라는 개념이 없음. 원래는 키값이라고 부름
public class userdb {

    String ID; // 아이디
    String PW; // 패스워드
    String UserName; // 이름
    String Sex; // 성별
    String Birthday; //생년월일
    String Phonenum; // 전화번호
    Boolean trainer; // 회원유형, true가 트레이너

    public userdb(){} // 생성자 메서드


    //getter, setter 설정
    public String getID() {
        return ID;
    }
    public void setID(String name) {
        this.ID = ID;
    }

    public String getPW() {
        return PW;
    }
    public void setPW(String name) {
        this.PW = PW;
    }

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getSex() {
        return Sex;
    }
    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getBirthday() {
        return Birthday;
    }
    public void setBirthday(String name) {
        this.Birthday = Birthday;
    }

    public String getPhonenum() {
        return Phonenum;
    }
    public void setPhonenum(String name) {
        this.Phonenum = Phonenum;
    }

    public Boolean gettrainer() {
        return trainer;
    }
    public void settrainer(Boolean name) {
        this.trainer = trainer;
    }

    //값을 추가할때 쓰는 함수, MainActivity에서 add함수에서 사용할 것임.
    public userdb(String ID, String PW, String UserName, String Sex, String Birthday, String Phonenum, Boolean trainer){
        this.ID = ID;
        this.PW = PW;
        this.UserName = UserName;
        this.Sex = Sex;
        this.Birthday=Birthday;
        this.Phonenum=Phonenum;
        this.trainer=trainer;
    }
}
