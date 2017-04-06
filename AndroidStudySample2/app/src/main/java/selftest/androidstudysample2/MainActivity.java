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
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private MyService myService;
    private ToggleButton startSeviceBtn, bindServiceBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startSeviceBtn = (ToggleButton) findViewById(R.id.startSeviceBtn);
        bindServiceBtn = (ToggleButton) findViewById(R.id.bindServiceBtn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myService != null) {
            String info = myService.GetServiceInfo();
            Log.d(TAG, info);
        }
    }

    public void bindServiceBtn_onClick(View view)
    {

        if(bindServiceBtn.isChecked())
        {
            Intent i = new Intent(this, MyService.class);
            bindService(i, connection, BIND_AUTO_CREATE);
        }
        else
        {
            unbindService(connection);
        }
    }

    public void startServiceBtn_onClick(View view)
    {
        Intent i = new Intent(this, MyService.class);
        if(startSeviceBtn.isChecked())
        {
            i.setAction("startService");
            startService(i);
        }
        else
        {
            stopService(i);
        }



    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Toast.makeText(MainActivity.this, "Service bind", Toast.LENGTH_SHORT).show();
            myService = ((MyService.MyBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(MainActivity.this, "Service unbind", Toast.LENGTH_SHORT).show();
            myService = null;
        }
    };


    public void btnAct2_onClick(View v)
    {
        Intent i = new Intent(this, Main2Activity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

}
