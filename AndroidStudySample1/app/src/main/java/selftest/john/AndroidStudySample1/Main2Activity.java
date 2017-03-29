package selftest.john.AndroidStudySample1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import selftest.john.myapplication.R;

public class Main2Activity extends AppCompatActivity {

    private TextView text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        text2 = (TextView) findViewById(R.id.text2);
        text2.setText(this.toString() + " id:"+ this.getTaskId());
    }

    public void button2_onClick(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
