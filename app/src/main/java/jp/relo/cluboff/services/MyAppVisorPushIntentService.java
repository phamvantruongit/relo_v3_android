package jp.relo.cluboff.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gcm.GCMBaseIntentService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import framework.phvtUtils.AppLog;
import framework.phvtUtils.StringUtil;
import jp.relo.cluboff.R;
import jp.relo.cluboff.database.MyDatabaseHelper;
import jp.relo.cluboff.model.HistoryPushDTO;
import jp.relo.cluboff.model.MessageEvent;
import jp.relo.cluboff.ui.activity.PushvisorHandlerActivity;
import jp.relo.cluboff.util.LoginSharedPreference;
import jp.relo.cluboff.util.Utils;

/**
 * Created by tonkhanh on 7/14/17.
 */

public class MyAppVisorPushIntentService extends GCMBaseIntentService {
    MyDatabaseHelper myDatabaseHelper;
    private Bundle bundle=null;
    public static final int NOTIFICATION_ID = 1;
    public MyAppVisorPushIntentService() {
    }
    @Override
    protected void onMessage(Context context, Intent intent) {
        myDatabaseHelper = new MyDatabaseHelper(getBaseContext());
        bundle = intent.getExtras();
        if(bundle!=null){
                String message = bundle.getString("message");
                String title = bundle.getString("title");
                String url = bundle.getString("u");
                String pushIDStr= bundle.getString("c");

                //Debug Params message
                String xString = bundle.getString("x");
                String yString = bundle.getString("y");
                String zString = bundle.getString("z");
                String wString = bundle.getString("w");
                long timtString = bundle.getLong("google.sent_time");
                HistoryPushDTO dataPush = new HistoryPushDTO();
                dataPush.setTitlePush(title);
                dataPush.setTimeHis(timtString);
                dataPush.setContentHis(message);
                dataPush.setxHis(xString);
                dataPush.setyHis(yString);
                dataPush.setzHis(zString);
                dataPush.setwHis(wString);
                dataPush.setUrlHis(wString);
                myDatabaseHelper.savePush(dataPush,!StringUtil.isEmpty(pushIDStr));
                EventBus.getDefault().post(new MessageEvent(MyAppVisorPushIntentService.class.getSimpleName()));
                if(isBackgroundRunning()){
                    AppLog.log("In isBackgroundRunning");
                    if(StringUtil.isEmpty(url)){
                        int pushID = Utils.convertInt(pushIDStr);
                        cancelNotification(context,pushID);
                    }
                }else{
                    AppLog.log("Out isBackgroundRunning");
                    //sendNotification(getBaseContext(),bundle);
                }
        }

    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    @Override
    protected void onError(Context context, String s) {

    }

    @Override
    protected void onRegistered(Context context, String s) {

    }

    @Override
    protected void onUnregistered(Context context, String s) {

    }
    public boolean isBackgroundRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> services = activityManager.getRunningAppProcesses();
        boolean isActivityFound = false;

        if (services.get(0).processName
                .equalsIgnoreCase(getPackageName())) {
            isActivityFound = true;
        }
        return isActivityFound;
    }
    public void sendNotification(Context ctx, Bundle mBundle) {
        String message = mBundle.getString("message");
        String title = mBundle.getString("title");
        Intent intent = new Intent(ctx, PushvisorHandlerActivity.class);
        intent.putExtras(mBundle);
        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(ctx);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, b.build());
    }
}
