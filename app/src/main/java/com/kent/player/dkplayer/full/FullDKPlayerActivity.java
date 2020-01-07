package com.kent.player.dkplayer.full;

import com.dueeeke.videocontroller.AdVideoController;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.kent.player.R;
import com.kent.player.dkplayer.DKPlayerActivity;
import com.kent.player.dkplayer.DebugActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by songzhukai on 2019-12-30.
 */
public class FullDKPlayerActivity extends DebugActivity {

    private static final String VOD_Url = "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_full_dkplayer;
    }

    @Override
    protected void initView() {
        super.initView();

        AdVideoView adVideoView = findViewById(R.id.player);
        mVideoView = adVideoView.getVideoView();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, FullDKPlayerActivity.class);
        activity.startActivity(intent);
    }
}
