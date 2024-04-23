package com.example.healthtrack.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;

import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.healthtrack.R;
import com.example.healthtrack.Utils.CommonUtils;
import com.example.healthtrack.Utils.DataLocalManager;
import com.example.healthtrack.Views.Activity.MainHomeActivity;


/**
 * Created by FRAMGIA\vu.tuan.anh on 21/08/2017.
 */
public class StepService extends Service implements SensorEventListener {
    private static final String TAG = "TAG: " + StepService.class.getSimpleName();

    private UpdateUiCallBack mCallback;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private BroadcastReceiver mBroadcastReceiver;
    private StepBinder mStepBinder = new StepBinder();
    private SensorManager mSensorManager;
    private int mCurrentStep;
    private int mNotifyIdStep = 100;
    private int mHasStepCount = 0;
    private int mPreviousStepCount = 0;
    private boolean mHasRecord = false;

    @Override
    public void onCreate() {
        super.onCreate();
        initNotification();
        initTodayData();
        initBroadcastReceiver();
        new Thread(new Runnable() {
            public void run() {
                startStepDetector();
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStepBinder;
    }

    public void registerCallback(UpdateUiCallBack paramICallback) {
        mCallback = paramICallback;
    }

    public void resetStepCount() {
        resetStepSensor();
        saveData(); // Lưu dữ liệu mới (số bước chân đã đặt lại)
        updateNotification(); // Cập nhật thông báo với số bước chân mới
    }


    private void resetStepSensor() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
            mSensorManager = null;
        }
        mHasRecord = false;
        mHasStepCount = 0;
        mPreviousStepCount = 0;
        mCurrentStep = 0;
        initTodayData(); // Cập nhật lại dữ liệu ngày hiện tại
        startStepDetector(); // Khởi động lại lắng nghe cảm biến
    }


    private void startStepDetector() {
        if (mSensorManager != null) {
            mSensorManager = null;
        }
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        addCountStepListener();
    }

    private void addCountStepListener() {
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        Sensor detectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            mSensorManager
                    .registerListener(StepService.this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Log.v(TAG, "Count sensor not available!");
        }
    }


    public int getStepCount() {
        return mCurrentStep;
    }


    public PendingIntent getDefaultIntent(int flags) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            return PendingIntent.getActivity(this, 1, new Intent(), PendingIntent.FLAG_MUTABLE);
        } else {
            return PendingIntent.getActivity(this, 1, new Intent(), flags);
        }

    }

    private void initTodayData() {
        mCurrentStep = CommonUtils.getStepNumber();
        updateNotification();
    }

    private void initBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SHUTDOWN);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case Intent.ACTION_SCREEN_ON:
                        Log.i(TAG, "screen_on");
                        break;
                    case Intent.ACTION_SCREEN_OFF:
                        Log.i(TAG, "screen_off");
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        Log.i(TAG, "screen unlock");
                        break;
                    case Intent.ACTION_CLOSE_SYSTEM_DIALOGS:
                        Log.i(TAG, "receive ACTION_CLOSE_SYSTEM_DIALOGS");
                        saveData();
                        break;
                    case Intent.ACTION_SHUTDOWN:
                        Log.i(TAG, "receive ACTION_SHUTDOWN");
                        saveData();
                        break;
                    case Intent.ACTION_DATE_CHANGED:
                        Log.i(TAG, "receive ACTION_DATE_CHANGED");
                        saveData();
                        break;
                    case Intent.ACTION_TIME_CHANGED:
                        Log.i(TAG, "receive ACTION_TIME_CHANGED");
                        saveData();
                        break;
                    case Intent.ACTION_TIME_TICK:
                        Log.i(TAG, "receive ACTION_TIME_TICK");
                        saveData();
                        break;
                }
            }
        };
        registerReceiver(mBroadcastReceiver, filter);
    }

    private void initNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chanel = new NotificationChannel(
                    "My Channel Id",
                    "My Foreground Service",
                    NotificationManager.IMPORTANCE_LOW);
            chanel.setLightColor(Color.BLUE);
            chanel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);

            mBuilder = new NotificationCompat.Builder(this, "My Channel Id");
            mBuilder.setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText("The number of steps today: " + mCurrentStep + " step")
                    .setContentIntent(getDefaultIntent(Notification.FLAG_ONGOING_EVENT))
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(false)
                    .setChannelId("My Channel Id")
                    .setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher);
            Notification notification = mBuilder.build();

            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(chanel);
            startForeground(mNotifyIdStep, notification);
        }
    }

    private void updateNotification() {
        Intent hangIntent = new Intent(this, MainHomeActivity.class);
        PendingIntent hangPendingIntent =
                PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification notification =
                mBuilder.setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText("The number of steps today: " + mCurrentStep + " step")
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(hangPendingIntent)
                        .build();
        mNotificationManager.notify(mNotifyIdStep, notification);
        if (mCallback != null) {
            mCallback.updateUi(mCurrentStep);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int tempStep = (int) sensorEvent.values[0];
        Log.i("STEP", "step " + tempStep);
        if (!mHasRecord) {
            mHasRecord = true;
            mHasStepCount = tempStep;
        } else {
            int thisStepCount = tempStep - mHasStepCount;
            int thisStep = thisStepCount - mPreviousStepCount;
            mCurrentStep += thisStep;
            mPreviousStepCount = thisStepCount;
        }
        updateNotification();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void saveData() {
        DataLocalManager.getInstance().setWalkingStep(CommonUtils.STEP_NUMBER_KEY, mCurrentStep);
    }


    public class StepBinder extends Binder {
        public StepService getService() {
            return StepService.this;
        }
    }
}

