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
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/**
 * Created by songzhukai on 2020-01-06.
 */
public class AdVideoView extends FrameLayout {

    private final String TAG = AdVideoView.class.getSimpleName();

    private VideoView mVideoView;
    private AdVideoController mController;
    private AdProvider mAdProvider;

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
                Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.anim_reduce);
                Animation anim = genAnimation();

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
        String url = "data/local/tmp/le.mp4";
        Log.d("FullDKPlayerActivity", "url=" + url);
        mVideoView.setUrl(url);
        mVideoView.start();

    }

    private AnimationSet genAnimation() {
        AnimationSet animSet = new AnimationSet(false);
        int videoViewWidth =  mVideoView.getWidth();
        int videoViewHeight =  mVideoView.getHeight();
        float height = 300f;
        float width = 400;
        float widthRate = width/videoViewWidth;
        float heightRate = height/videoViewHeight;

        //对目的位置座标偏移距离
        float offsetX = -300f;
        float offsetY = -150f;

        Log.d(TAG, "oriWidth="+videoViewWidth+", targetWidth="+ width+", rate="+widthRate);
        Log.d(TAG, "oriHeight="+videoViewHeight+", targetHeight="+ height+", rate="+heightRate);

        /**
         * ScaleAnimation构造
         * @param fromX X方向开始时的宽度，1f表示控件原有大小
         * @param toX X方向结束时的宽度，
         * @param fromY Y方向上开的宽度，
         * @param toY Y方向结束的宽度
         * @param pivotX 缩放的轴心X的位置，取值类型是float，单位是px像素，比如：X方向控件中心位置是 mVideoView.getWidth() / 2f
         * @param pivotY 缩放的轴心Y的位置，取值类型是float，单位是px像素，比如：X方向控件中心位置是 mVideoView.getHeight() / 2f
         **/
        ScaleAnimation scaleAnim = new ScaleAnimation(1f, widthRate, 1f, widthRate, videoViewWidth / 2f + offsetX, videoViewHeight / 2f + offsetY);
        scaleAnim.setDuration(3000);

        animSet.addAnimation(scaleAnim);

        return animSet;
    }


    public VideoView getVideoView() {
        return mVideoView;
    }

    public void setAdProvider(AdProvider adProvider) {
        mAdProvider = adProvider;
    }


}
