package com.ayogeshwaran.bakingapp.Ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ayogeshwaran.bakingapp.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

}