package com.kent.player;


import com.kent.player.dkplayer.DKPlayerActivity;
import com.kent.player.dkplayer.full.FullDKPlayerActivity;
import com.kent.player.mediaplayer.MediaPlayerActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by songzhukai on 2019-12-25.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_media).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerActivity.launch(MainActivity.this);
            }
        });

        findViewById(R.id.btn_dk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DKPlayerActivity.launch(MainActivity.this);
            }
        });

        findViewById(R.id.btn_dk_full).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullDKPlayerActivity.launch(MainActivity.this);
            }
        });
    }

}
