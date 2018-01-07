package app.kimyeonjung.trendreader.core.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import app.kimyeonjung.trendreader.R;

public class FCMRegister {
    public FCMRegister() {
    }

    public void refreshSubscribe(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        HashMap<String, Boolean> channelData = new HashMap<>();
        channelData.put(context.getString(R.string.fcm_quickly_Channel), pref.getBoolean(context.getString(R.string.pref_fcm_quickly), true));
        channelData.put(context.getString(R.string.fcm_morning_Channel), pref.getBoolean(context.getString(R.string.pref_fcm_daily_morning), false));
        channelData.put(context.getString(R.string.fcm_evening_Channel), pref.getBoolean(context.getString(R.string.pref_fcm_daily_evening), false));

        for (String channel : channelData.keySet()) {
            if (channelData.get(channel)) {
                FirebaseMessaging.getInstance().subscribeToTopic(channel);
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(channel);
            }
        }
    }
}
