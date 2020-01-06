package com.example.mc_week1_final;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        finish();
    }

    private void progressAnimation() {
        ProgressBarAnimation anim=new ProgressBarAnimation(this, progressBar,textView,0f,100f);
        anim.setDuration(8000);
        progressBar.setAnimation(anim);
    }
}
