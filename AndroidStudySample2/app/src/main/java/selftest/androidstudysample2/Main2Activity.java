package selftest.androidstudysample2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private MyService myService = null;
    private String TAG = "Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = new Intent(this, MyService.class);
        //i.setAction("startService");
        //startService(i);
        bindService(i, connection, BIND_AUTO_CREATE);

    }

    public void btnAct1_onClick(View v)
    {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Toast.makeText(Main2Activity.this, "Service bind", Toast.LENGTH_SHORT).show();
            myService = ((MyService.MyBinder)iBinder).getService();
            Log.d(TAG, myService.GetServiceInfo());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //Toast.makeText(Main2Activity.this, "Service unbind", Toast.LENGTH_SHORT).show();
            myService = null;
        }
    };
}
