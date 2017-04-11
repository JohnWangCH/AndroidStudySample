package john.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import john.aidlservice.*;

public class MainActivity extends AppCompatActivity {

    private String TAG = "AIDLClient";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnConnect_onClick(View v)
    {
        Intent i = new Intent("android.intent.action.AIDLService");
        bindService(i, connections, BIND_AUTO_CREATE);
    }

    public void btnDisconnect_onClick(View v)
    {
        if(connections!=null)
            unbindService(connections);
    }

    public void btnGreet_onClick(View v)
    {
        if(aidlInterface != null)
        {
            try {
                Toast.makeText(this, aidlInterface.Greating("John"), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Log.d(TAG, e.toString());
            }
        }
        else
        {
            Log.d(TAG, "aidl is null");
        }
    }

    public void btnCompliemt_onClick(View v)
    {
        if(aidlInterface != null)
        {
            try {
                Toast.makeText(this, aidlInterface.Complimenting(new Person("May", 0)), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Log.d(TAG, e.toString());
            }
        }
        else
        {
            Log.d(TAG, "aidl is null");
        }
    }
    private IMyAidlInterface aidlInterface;
    ServiceConnection connections = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            aidlInterface =IMyAidlInterface.Stub.asInterface(iBinder);
            Log.d(TAG,"bind");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            aidlInterface = null;
            Log.d(TAG,"unbind");
        }
    };
}
