package selftest.susample;

import android.database.CrossProcessCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private String TAG = "SuSample";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String retString = "";
        try {
            //Query cpuInfo
            Log.d(TAG, "CpuInfo: " + ExecCmd.suResultExeCmd("cat /proc/cpuinfo", 6000));

        } catch (Exception e) {
        }
    }

}
