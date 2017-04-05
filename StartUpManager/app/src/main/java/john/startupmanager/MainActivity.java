package john.startupmanager;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner appSpin;
    private SharedPreferences sharePreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appSpin = (Spinner) findViewById(R.id.spinApps);
        //appSpin.setAdapter(
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,
                programList());
        sharePreference = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String savedProgram = sharePreference.getString("program", "");
        appSpin.setAdapter(adapter);
        appSpin.setSelection(programList().indexOf(savedProgram));
        appSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, " onItemSeleted", Toast.LENGTH_LONG).show();
                sharePreference.edit().putString("program", adapterView.getSelectedItem().toString()).apply();
                sharePreference.edit().commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "call onNothingSeleted", Toast.LENGTH_LONG).show();
            }
        });
    }


    private ArrayList<String> programList()
    {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add("");
        for (ApplicationInfo appInfo: getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA))
        {
            if(getPackageManager().getLaunchIntentForPackage(appInfo.packageName)!= null)
                ret.add(appInfo.packageName);
        }
        return ret;
    }
}
