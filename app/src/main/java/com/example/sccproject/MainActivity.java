package com.example.sccproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button infoBtn, easyBtn, diffBtn;
    private int NUM = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoBtn = (Button)findViewById(R.id.info);
        easyBtn = (Button)findViewById(R.id.easy);
        diffBtn = (Button)findViewById(R.id.difficulty);

        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Game.class);
                MainActivity.this.startActivity(intent);
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Info.class);
                MainActivity.this.startActivity(intent);
            }
        });
        diffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameDiff.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }



}
