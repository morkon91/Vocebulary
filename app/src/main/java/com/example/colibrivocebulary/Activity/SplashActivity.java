package com.example.colibrivocebulary.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.colibrivocebulary.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView splash_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_imageView = findViewById(R.id.splash_imageView);

        getWindow().setBackgroundDrawableResource(R.color.colorPrimaryDark);

        splash_imageView.setVisibility(View.VISIBLE);


        YoYo.with(Techniques.Pulse).duration(100).repeat(2).pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, WordsActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                startActivity(new Intent(SplashActivity.this, WordsActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        }).playOn(splash_imageView);
    }
}
