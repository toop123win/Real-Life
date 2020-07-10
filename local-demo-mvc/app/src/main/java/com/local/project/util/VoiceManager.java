package com.local.project.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;


/**
 * 声音播放工具
 */
public class VoiceManager {
    private static volatile VoiceManager voiceManager;
    public final int MSG_TIME_INTERVAL = 100;
    // 多媒体例如声音的状态
    public final int MEDIA_STATE_UNDEFINE = 200;
    public final int MEDIA_STATE_RECORD_STOP = 210;
    public final int MEDIA_STATE_RECORD_DOING = 220;
    public final int MEDIA_STATE_RECORD_PAUSE = 230;
    public final int MEDIA_STATE_PLAY_STOP = 310;
    public final int MEDIA_STATE_PLAY_DOING = 320;
    private Context mContext = null;
    private SeekBar mSBPlayProgress;
    private int mSavedState, mDeviceState = MEDIA_STATE_UNDEFINE;
    private MediaRecorder mMediaRecorder = null;
    private MediaPlayer mMediaPlayer = null;
    private String mRecTimePrev;
    private long mRecTimeSum = 0;

    private int playFileRaw = 0;

    /**
     * 播放监听
     */
    private VoicePlayCallBack voicePlayCallBack;

    private VoiceManager(Context context) {
        this.mContext = context;
    }

    /**
     * 获取单例
     *
     * @param context
     * @return
     */
    public static VoiceManager getInstance(Context context) {
        if (voiceManager == null) {
            synchronized (VoiceManager.class) {
                if (voiceManager == null) {
                    voiceManager = new VoiceManager(context);
                }
            }
        }
        return voiceManager;
    }

    /**
     * 播放器结束监听
     */
    private MediaPlayer.OnCompletionListener mPlayCompetedListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mDeviceState = MEDIA_STATE_PLAY_STOP;
            mHandler.removeMessages(MSG_TIME_INTERVAL);
            mMediaPlayer.stop();
            mMediaPlayer.release();
            if (mSBPlayProgress != null) {
                mSBPlayProgress.setProgress(0);
            }
            if (voicePlayCallBack != null) {
                voicePlayCallBack.playFinish();
            }
        }
    };
    /**
     * 播放
     */
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            VoiceTimeUtils ts;
            int current;
            try {
                switch (msg.what) {
                    //录音
                    case MSG_TIME_INTERVAL:
                        if (mDeviceState == MEDIA_STATE_RECORD_DOING) {
                            ts = VoiceTimeUtils.timeSpanToNow(mRecTimePrev);
                            mRecTimeSum += ts.mDiffSecond;
                            mRecTimePrev = VoiceTimeUtils.getTimeStrFromMillis(ts.mNowTime);
                            ts = VoiceTimeUtils.timeSpanSecond(mRecTimeSum);

                            mHandler.sendEmptyMessageDelayed(MSG_TIME_INTERVAL, 1000);
                        }
                        //播放
                        else if (mDeviceState == MEDIA_STATE_PLAY_DOING) {
                            current = mMediaPlayer.getCurrentPosition();
                            if (mSBPlayProgress != null) {
                                mSBPlayProgress.setProgress(current);
                            }
                            ts = VoiceTimeUtils.timeSpanSecond(current / 1000);
                            //回调播放进度
                            if (voicePlayCallBack != null) {
                                voicePlayCallBack.playDoing(current / 1000, String.format("%02d:%02d:%02d",
                                        ts.mSpanHour, ts.mSpanMinute, ts.mSpanSecond));
                            }
                            mHandler.sendEmptyMessageDelayed(MSG_TIME_INTERVAL, 1000);
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
            }
        }
    };



    /**
     * 播放SeekBar监听
     *
     * @param seekBar
     */
    public void setSeekBarListener(SeekBar seekBar) {
        mSBPlayProgress = seekBar;
        if (mSBPlayProgress != null) {
            mSBPlayProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mHandler.removeMessages(MSG_TIME_INTERVAL);
                    mSavedState = mDeviceState;
                    if (mSavedState == MEDIA_STATE_PLAY_DOING) {
                        pauseMedia(mMediaPlayer);
                    }
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mHandler.removeMessages(MSG_TIME_INTERVAL);
                    VoiceTimeUtils ts = VoiceTimeUtils.timeSpanSecond(progress / 1000);
                    //播放进度
                    if (voicePlayCallBack != null) {
                        voicePlayCallBack.playDoing(progress / 1000, String.format("%02d:%02d:%02d",
                                ts.mSpanHour, ts.mSpanMinute, ts.mSpanSecond));
                    }
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    seektoMedia(mMediaPlayer, mSBPlayProgress.getProgress());

                    if (mSavedState == MEDIA_STATE_PLAY_DOING) {
                        playMedia(mMediaPlayer);
                        mHandler.sendEmptyMessage(MSG_TIME_INTERVAL);
                    }
                }
            });
        }
    }


    /**
     * 开始播放（外部调）
     *
     * @param rewFile 音频存放文件夹
     */
    public void startPlay(int rewFile) {
        playFileRaw = rewFile;
        startPlay(true);
    }

    /**
     * 开始播放（内部调）
     *
     * @param init
     */
    private void startPlay(boolean init) {
        try {
            mMediaRecorder = null;
            stopMedia(mMediaPlayer, true);
            mMediaPlayer = null;
            mMediaPlayer = MediaPlayer.create(mContext, playFileRaw);
            mMediaPlayer.setOnCompletionListener(mPlayCompetedListener);
            mMediaPlayer.start();
        } catch (Exception e) {
            Log.e("播放出错了", e.getMessage());
        }
    }


    /**
     * 停止播放
     */
    public void stopPlay() {
        mHandler.removeMessages(MSG_TIME_INTERVAL);
        mDeviceState = MEDIA_STATE_PLAY_STOP;
        stopMedia(mMediaPlayer, true);
        mMediaPlayer = null;
    }

    /**
     * 是否在播放中
     *
     * @return
     */
    public boolean isPlaying() {
        return mDeviceState == MEDIA_STATE_PLAY_DOING;
    }

    /*********************************播放操作end***************************/




    /**
     * 播放录音开始
     *
     * @param mp
     * @return
     */
    private boolean playMedia(MediaPlayer mp) {
        boolean result = false;
        try {
            if (mp != null) {
                mp.start();
                result = true;
            }
        } catch (Exception e) {
        }

        return result;
    }

    /**
     * 拖动播放进度条
     *
     * @param mp
     * @param pos
     * @return
     */
    private boolean seektoMedia(MediaPlayer mp, int pos) {
        boolean result = false;
        try {
            if (mp != null && pos >= 0) {
                mp.seekTo(pos);
                result = true;
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 停止播放
     *
     * @param mp
     * @param release
     * @return
     */
    private boolean stopMedia(MediaPlayer mp, boolean release) {
        boolean result = false;
        try {
            if (mp != null) {
                mp.stop();

                if (release) {
                    mp.release();


                }
                result = true;
            }
        } catch (Exception e) {
        }

        return result;
    }

    /**
     * 暂停播放
     *
     * @param mp
     * @return
     */
    private boolean pauseMedia(MediaPlayer mp) {
        boolean result = false;

        try {
            if (mp != null) {
                mp.pause();
                result = true;
            }
        } catch (Exception e) {
        }

        return result;
    }


    /**
     * 播放录音回调监听
     */
    public interface VoicePlayCallBack {

        /**
         * 音频长度
         * 指定的某个时间段，以秒为单位
         */
        void voiceTotalLength(long time, String strTime);

        /**
         * 播放中
         * 指定的某个时间段，以秒为单位
         */
        void playDoing(long time, String strTime);

        //播放暂停
        void playPause();

        //播放开始
        void playStart();

        //播放结束
        void playFinish();
    }

}
