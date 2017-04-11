package john.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
    private String TAG = "AIDLService";
    public MyService() {
    }
    IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String Greating(String who) throws RemoteException {
            return "Hello, " + who;
        }

        @Override
        public String Complimenting(Person who) throws RemoteException {
            return "Hello, " + who.getName() + ", you looks " + (who.getSex()==0?"beautiful":"handsome");
        }
    };

    @Override
    public void onCreate() {
        Log.d(TAG, "call onCreate");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "call onBind");
        return stub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "call onUnbind");
        return super.onUnbind(intent);
    }
}
