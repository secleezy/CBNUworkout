package com.example.cbnugra;

//테이블이라고 생각하고, 테이블에 들어갈 속성값을 넣기
//파이어베이스는 RDBMS와 다르기 때문에 테이블이라는 개념이 없음. 원래는 키값이라고 부름

public class changepw{
    private String PW;
    public changepw(){}

    public String getPWchange() {
        return PW;
    }
    public void setPWchange(String name) {
        this.PW = PW;
    }

    public changepw(String PWchange)
    {
        this.PW = PW;
    }
}
