package com.lele.fivelinkball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private FiveLinkBallView fiveLinkBallView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fiveLinkBallView = (FiveLinkBallView)findViewById(R.id.fiveLinkBallView);
        fiveLinkBallView.setLevel(getIntent().getIntExtra("level",1));
    }
}
