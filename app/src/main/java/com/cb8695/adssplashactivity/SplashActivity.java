package com.cb8695.adssplashactivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.cb8695.adssplashactivity.widget.DonutProgress;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

public class SplashActivity extends Activity {
    private ImageView bgIv;
    private ImageView adIv;
    private DonutProgress skipRpb;
    private static final String picURL = "http://img2.3lian.com/2014/f6/173/d/51.jpg";
    private static final Uri picURI = Uri.parse(picURL);
    private static Handler mHandler = new Handler();
    private static final int MSG_PROGRESS_UPDATE = 0x110;
    // 广告页总时长
    private static final int ADVERTISE_TIME = 2000;
    // Progress更新时长
    private static final int PROGRESS_UPDATE_TIME = 40;

    class MyHandler extends Handler {
        WeakReference<Activity> weakReference;

        public MyHandler(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = weakReference.get();
            if (activity != null) {
                skipRpb.setProgress(skipRpb.getProgress() + 100 / (ADVERTISE_TIME / PROGRESS_UPDATE_TIME));
                this.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, PROGRESS_UPDATE_TIME);
                if (skipRpb.getProgress() == 100) {
                    this.removeMessages(MSG_PROGRESS_UPDATE);
                    startMainActivity();
                }
            }
        }
    }


    Runnable showAdPic = new Runnable() {
        public void run() {
            Picasso.with(SplashActivity.this).load(picURI).into(adIv, new Callback() {
                MyHandler pHandler = new MyHandler(SplashActivity.this);
                @Override
                public void onSuccess() {
                    bgIv.setVisibility(View.GONE);
                    skipRpb.setVisibility(View.VISIBLE);
                    pHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
                    skipRpb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startMainActivity();
                        }
                    });
                }
                @Override
                public void onError() {

                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bgIv = (ImageView) findViewById(R.id.bg_iv);
        adIv = (ImageView) findViewById(R.id.ad_iv);
        skipRpb = (DonutProgress) findViewById(R.id.skip_rpb);
        mHandler.postDelayed(showAdPic, ADVERTISE_TIME);
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }
}
