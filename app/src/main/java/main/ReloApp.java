package main;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by HuyTran on 3/21/17.
 */

public class ReloApp extends Application {

    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }


    /**
     * Tracking this Action by some params input
     * @param categoryId
     * @param actionId
     * @param labelId
     * @param valueId
     */
     void trackingWithAnalyticGoogleServices(String categoryId, String actionId, String labelId, long valueId){
        getDefaultTracker();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(categoryId)
                .setAction(actionId)
                .setLabel(labelId)
                .setValue(valueId)
                .build());

    }


    /**
     * Tracking this Action by screen
     * @param nameScreen
     */
    private void trackingAnalyticByScreen(String nameScreen){
        getDefaultTracker();
        mTracker.setScreenName(nameScreen);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void trackingAnalytics(Boolean isScreenOnly, String screenName, String category, String action, String label, long value) {
        if (isScreenOnly) {
            trackingAnalyticByScreen(screenName);
        } else {
            trackingWithAnalyticGoogleServices(category, action, label, value);
        }
    }

    //Screen Only
    public void trackingAnalytics(String screenName) {
        trackingAnalytics(true, screenName, "", "", "", 0);
    }

    //Event
    public void trackingAnalytics(String category, String action, String label, long value) {
        trackingAnalytics(false, "", category, action, label, value);
    }

}
