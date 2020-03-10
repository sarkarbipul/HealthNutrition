package com.logicaltriangle.hnn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    TextView title;
    ImageView bottom_path, tree1, tree2, round_img;
    ImageView ic1, ic2, ic3, ic4, ic5, ic6, ic7, ic8, ic9;
    ImageView ic10, ic11, ic12, ic13, ic14, ic15, ic16, ic17, ic18;
    Animation slide_up, jump_down, scale_up, fade_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //fade_in animation
        title = findViewById(R.id.title);
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        title.startAnimation(fade_in);

        //slide_up animation
        /*bottom_path = findViewById(R.id.bottom_path);
        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        bottom_path.startAnimation(slide_up);*/

        //jump_down animation
        ic1 = findViewById(R.id.ic1);
        ic2 = findViewById(R.id.ic2);
        ic3 = findViewById(R.id.ic3);
        ic4 = findViewById(R.id.ic4);
        ic5 = findViewById(R.id.ic5);
        ic6 = findViewById(R.id.ic6);
        ic7 = findViewById(R.id.ic7);
        ic8 = findViewById(R.id.ic8);
        ic9 = findViewById(R.id.ic9);
        ic10 = findViewById(R.id.ic10);
        ic11 = findViewById(R.id.ic11);
        ic12 = findViewById(R.id.ic12);
        ic13 = findViewById(R.id.ic13);
        ic14 = findViewById(R.id.ic14);
        ic15 = findViewById(R.id.ic15);
        ic16 = findViewById(R.id.ic16);
        ic17 = findViewById(R.id.ic17);
        ic18 = findViewById(R.id.ic18);
        tree1 = findViewById(R.id.tree1);
        tree2 = findViewById(R.id.tree2);

        jump_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.jump_down);

        ic1.startAnimation(jump_down);
        ic2.startAnimation(jump_down);
        ic3.startAnimation(jump_down);
        ic4.startAnimation(jump_down);
        ic5.startAnimation(jump_down);
        ic6.startAnimation(jump_down);
        ic7.startAnimation(jump_down);
        ic8.startAnimation(jump_down);
        ic9.startAnimation(jump_down);
        ic10.startAnimation(jump_down);
        ic11.startAnimation(jump_down);
        ic12.startAnimation(jump_down);
        ic13.startAnimation(jump_down);
        ic14.startAnimation(jump_down);
        ic15.startAnimation(jump_down);
        ic16.startAnimation(jump_down);
        ic17.startAnimation(jump_down);
        ic18.startAnimation(jump_down);
        tree1.startAnimation(jump_down);
        tree2.startAnimation(jump_down);

        //scale_up animation
        round_img = findViewById(R.id.round_img);
        scale_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up);
        round_img.startAnimation(scale_up);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}
