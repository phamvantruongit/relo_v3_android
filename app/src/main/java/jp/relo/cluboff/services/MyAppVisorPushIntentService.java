package jp.relo.cluboff.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
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
                if(!"1".equals(url)){
                    HistoryPushDTO dataPush = new HistoryPushDTO();
                    dataPush.setTitlePush(title);
                    dataPush.setTimeHis(timtString);
                    dataPush.setContentHis(message);
                    dataPush.setxHis(xString);
                    dataPush.setyHis(yString);
                    dataPush.setzHis(zString);
                    dataPush.setwHis(wString);
                    myDatabaseHelper.savePush(dataPush);
                    //EventBus.getDefault().post(new MessageEvent(MyAppVisorPushIntentService.class.getSimpleName()));
                    if(isBackgroundRunning(getBaseContext())){
                        AppLog.log("In isBackgroundRunning");
                        if(StringUtil.isEmpty(url)){
                            int pushID = Utils.convertInt(pushIDStr);
                            //cancelNotification(context,pushID);
                        }
                    }else{
                        AppLog.log("NOT In isBackgroundRunning");
                    }

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
    public static boolean isForeground(Context context) {

        // Get the Activity Manager
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        // Get a list of running tasks, we are only interested in the last one,
        // the top most so we give a 1 as parameter so we only get the topmost.
        List<ActivityManager.RunningAppProcessInfo> task = manager.getRunningAppProcesses();

        // Get the info we need for comparison.
        ComponentName componentInfo = task.get(0).importanceReasonComponent;

        // Check if it matches our package name.
        if(context.getPackageName().equals(componentInfo.getPackageName()))
            return true;

        // If not then our app is not on the foreground.
        return false;
    }

    public static boolean isBackgroundRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        return true;
                    }
                }
            }
        }


        return false;
    }
}
