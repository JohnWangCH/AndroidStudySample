package john.startupmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            String app = sp.getString("program", "");
            if(!app.equals("")) {
                Intent i = context.getPackageManager().getLaunchIntentForPackage(app);
                context.startActivity(i);
            }
        }
    }
}
