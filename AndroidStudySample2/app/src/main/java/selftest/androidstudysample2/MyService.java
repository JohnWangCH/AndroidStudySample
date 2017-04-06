package selftest.androidstudysample2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    private final String TAG = "MyService";

    public Binder myBinder = new MyBinder();

    public String GetServiceInfo()
    {
        return "Some service information";
    }

    public class MyBinder extends Binder
    {
        public MyService getService()
        {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "onBind");
        return this.myBinder;
    }

    public MyService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: " + intent.getAction());

        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
