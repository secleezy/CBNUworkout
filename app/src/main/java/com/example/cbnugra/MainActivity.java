    package com.example.cbnugra;

    import androidx.appcompat.app.AppCompatActivity;

    import android.app.ActivityOptions;
    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Handler;
    import android.util.Pair;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import com.bumptech.glide.Glide;


    public class MainActivity extends AppCompatActivity {

    Button btnStart;
    ImageView ivTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btn_Start);
        ivTitle = (ImageView) findViewById(R.id.iv_title);

        Glide.with(this).load(R.raw.loading).into(ivTitle); //로딩 이미지


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,join.class);
                //건너갈 액티비티를 정해둔 인텐트 객체 선언

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(ivTitle,"imgTransition");
                //액티비티에서 움직일 뷰와 트랜지션이름을 Pair배열에 담아둔다.

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                //액티비티 옵션을 적용하기 위해 ActivityOptions객체를 만들고 트랜지션 에니메이션에 대한 설정을 넣는다

                startActivity(intent,options.toBundle());
            }
        }, 2000);


    }
}