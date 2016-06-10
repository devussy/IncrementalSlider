package com.incrementalslider.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.incrementalslider.R;

public class MainActivity extends AppCompatActivity {

    private IncrementalSlider mIncrementalSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIncrementalSlider = (IncrementalSlider) findViewById(R.id.main_incremental_slider);

        mIncrementalSlider.setLabelTextSize(50f);
    }
}
