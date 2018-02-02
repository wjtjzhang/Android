package com.lele.fivelinkball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View imageView1;
    private View imageView2;
    private View imageView3;
    private View imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int level =1;
        intent.putExtra("name", "诸葛亮");

        switch (v.getId()) {
            case R.id.imageView1:
                level = 1;
            case R.id.imageView2:
                level = 2;
            case R.id.imageView3:
                level = 3;
            case R.id.imageView4:
                level = 4;
            default:
                break;
        }
        intent.putExtra("level", level);
        intent.setClass(MainActivity.this, GameActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
