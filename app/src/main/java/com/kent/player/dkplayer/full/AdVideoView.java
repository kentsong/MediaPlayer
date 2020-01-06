package com.kent.player.dkplayer.full;

import com.dueeeke.videocontroller.AdVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.kent.player.R;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

/**
 * Created by songzhukai on 2020-01-06.
 */
public class AdVideoView extends FrameLayout {

    private VideoView mVideoView;
    private AdVideoController mController;

    public AdVideoView(@NonNull Context context) {
        this(context, null);
    }

    public AdVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setBackgroundColor(Color.TRANSPARENT);
        mVideoView = new VideoView(getContext());
        mVideoView.setLayoutParams(new LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        addView(mVideoView);

        AdVideoController controller = new AdVideoController(getContext());
        controller.setCountDown(5);
        controller.setListener(new AdVideoController.AdVideoViewListener() {
            @Override
            public void onFinish() {
                mVideoView.pause();
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_reduce);

                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mVideoView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mVideoView.startAnimation(anim);
            }
        });


        mVideoView.setVideoController(controller);

//        String url = Environment.getExternalStorageDirectory().getPath() + "/ad/le.mp4";
        String url = "dev/le.mp4";
        Log.d("FullDKPlayerActivity", "url=" + url);
        mVideoView.setUrl(url);
        mVideoView.start();

    }

    public VideoView getVideoView() {
        return mVideoView;
    }
}
