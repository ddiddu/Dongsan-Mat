package com.example.mc_week1_final;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class LoadingActivity extends Activity {

    ProgressBar progressBar;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loadscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar=findViewById(R.id.progress_bar);
        textView=findViewById(R.id.text_view);
        imageView=findViewById(R.id.image_view);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressAnimation();
        startLoading();
    }

    private void startLoading() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },8000);
    }

    private void progressAnimation() {
        ProgressBarAnimation anim=new ProgressBarAnimation(this, progressBar,textView,0f,100f);
        anim.setDuration(8000);
        progressBar.setAnimation(anim);
    }
}
