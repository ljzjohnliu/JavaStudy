package com.study.android.videoview;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.utils.SystemUIUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class TestSurfaceViewActivity extends BaseSimpleActivity {
    private static final String TAG = "TestSurfaceView";

    @BindView(R.id.animation_view)
    AnimationSurfaceView animationView;
    @BindView(R.id.standard_surface)
    SurfaceView standardSurface;
    @BindView(R.id.standard_texture)
    TextureView standardTexture;
    @BindView(R.id.standard_videoview)
    VideoView standardVideoView;

    private MediaPlayer surfaceMediaPlayer;
    private MediaPlayer textureMediaPlayer;

    @OnClick({R.id.surface_pic, R.id.surface_video, R.id.texture_video, R.id.video_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.surface_pic:
                animationView.setVisibility(View.VISIBLE);
                standardSurface.setVisibility(View.GONE);
                standardTexture.setVisibility(View.GONE);
                standardVideoView.setVisibility(View.GONE);
                break;
            case R.id.surface_video:
                animationView.setVisibility(View.GONE);
                standardSurface.setVisibility(View.VISIBLE);
                standardTexture.setVisibility(View.GONE);
                standardVideoView.setVisibility(View.GONE);
                break;
            case R.id.texture_video:
                standardSurface.setVisibility(View.GONE);
                animationView.setVisibility(View.GONE);
                standardTexture.setVisibility(View.VISIBLE);
                standardVideoView.setVisibility(View.GONE);
                break;
            case R.id.video_view:
                standardSurface.setVisibility(View.GONE);
                animationView.setVisibility(View.GONE);
                standardTexture.setVisibility(View.GONE);
                standardVideoView.setVisibility(View.VISIBLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                standardVideoView.start();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        SystemUIUtils.setupTranslucentSystemBar(this);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: thread id = " + Thread.currentThread().getId());

        standardSurface.getHolder().addCallback(surfaceCallback);

        standardTexture.setSurfaceTextureListener(textureListener);

        //????????????????????????VideoView????????????????????????
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //getWindow() .addFlags (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        standardVideoView.setVideoURI(Uri.parse("android.resource://com.study.android/" + R.raw.test_bg1));
        standardVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "onPrepared: ---------????????????????????? ???????????????????????????---------");
            }
        });

        standardVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion: ------------??????????????????----------");
            }
        });

        standardVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "onCompletion: ------------??????????????????----------");
                return false;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "------onNewIntent: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "------onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "------onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "------onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "------onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "------onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "------onDestroy: ");
    }

    /*------------------------------------SurfaceView ?????????????????? ??????-----------------------*/
    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        // SurfaceHolder????????????????????????
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "SurfaceHolder ?????????");
            // ??????SurfaceHolder???????????????????????????????????????????????????
            if (surfaceMediaPlayer != null && surfaceMediaPlayer.isPlaying()) {
                surfaceMediaPlayer.stop();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i(TAG, "SurfaceHolder ?????????");
            playSurfaceMediaPlayer();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.i(TAG, "SurfaceHolder ???????????????");
        }
    };

    protected void playSurfaceMediaPlayer() {
        // ????????????????????????
//        String path = et_path.getText().toString().trim();
//        File file = new File(path);
//        if (!file.exists()) {
//            Toast.makeText(this, "????????????????????????", 0).show();
//            return;
//        }
//        mediaPlayer.setDataSource(file.getAbsolutePath());

        try {
            surfaceMediaPlayer = new MediaPlayer();
            surfaceMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // ????????????????????????
            try {
                AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.test_bg1);
                surfaceMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                        file.getLength());
                file.close();
            } catch (IOException e) {
                Log.d(TAG, "play: e = " + e);
                e.printStackTrace();
            }

            // ?????????????????????SurfaceHolder
            surfaceMediaPlayer.setDisplay(standardSurface.getHolder());
            Log.i(TAG, "????????????");
            surfaceMediaPlayer.prepareAsync();
            surfaceMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(TAG, "????????????");
                    surfaceMediaPlayer.setLooping(true);
                    surfaceMediaPlayer.start();
                }
            });
            surfaceMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // ????????????????????????
                    Log.d(TAG, "onCompletion: ");
                }
            });

            surfaceMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onError: what = " + what + ", extra = " + extra);
                    // ????????????????????????
                    playSurfaceMediaPlayer();
                    return false;
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "play: ------ e = " + e);
            e.printStackTrace();
        }
    }

    protected void stopSurfaceMediaPlayer() {
        if (surfaceMediaPlayer != null) {
            surfaceMediaPlayer.stop();
        }
    }
    /*------------------------------------SurfaceView ?????????????????? ??????-----------------------*/

    /*------------------------------------TextureView ?????????????????? ??????-----------------------*/
    private Surface textureSurface;
    private TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
            textureSurface = new Surface(surface);
            playTextureMediaPlayer();    //????????????
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
            textureSurface = null;
            if (textureMediaPlayer != null) {
                textureMediaPlayer.stop();
                textureMediaPlayer.release();
            }
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

        }
    };

    protected void playTextureMediaPlayer() {

        try {
            textureMediaPlayer = new MediaPlayer();
            textureMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // ????????????????????????
            try {
                AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.test_bg2);
                textureMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                        file.getLength());
                file.close();
            } catch (IOException e) {
                Log.d(TAG, "play: e = " + e);
                e.printStackTrace();
            }

            // ?????????????????????SurfaceHolder
            textureMediaPlayer.setSurface(textureSurface);
            Log.i(TAG, "????????????");
            textureMediaPlayer.prepareAsync();
            textureMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(TAG, "????????????");
                    textureMediaPlayer.setLooping(true);
                    textureMediaPlayer.start();
                }
            });
            textureMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // ????????????????????????
                    Log.d(TAG, "onCompletion: ");
                }
            });

            textureMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.d(TAG, "onError: what = " + what + ", extra = " + extra);
                    // ????????????????????????
                    playTextureMediaPlayer();
                    return false;
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "play: ------ e = " + e);
            e.printStackTrace();
        }
    }

    /*------------------------------------TextureView ?????????????????? ??????-----------------------*/
}