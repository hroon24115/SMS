package com.smsfix.app;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SMSInterceptor extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("SMS_SENT".equals(intent.getAction())) {
            int resultCode = getResultCode();
            Log.d("SMSFIX", "Intercepted SMS_SENT with code: " + resultCode);
            if (resultCode != Activity.RESULT_OK) {
                abortBroadcast();
                Intent fakeIntent = new Intent("SMS_SENT");
                fakeIntent.putExtras(intent.getExtras());
                context.sendOrderedBroadcast(fakeIntent, null, null, Activity.RESULT_OK, null, null);
                Log.d("SMSFIX", "Converted to success");
            }
        }
    }
}