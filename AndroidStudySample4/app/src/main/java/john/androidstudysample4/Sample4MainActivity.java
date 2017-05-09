package john.androidstudysample4;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Sample4MainActivity extends AppCompatActivity {

    private static final String AUTHORITY = "android.study.sample.sample4";
    private static final Uri PERSON_ALL_URI = Uri.parse("content://" + AUTHORITY + "/persons");
    private ContentResolver resolver;
    private TextView txtInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample4_main);
        resolver = getContentResolver();
        txtInfo = (TextView) findViewById(R.id.txtViewInfo);
    }

    public void btnInit_onClick(View v)
    {
        resolver.delete(PERSON_ALL_URI, null, null);
        ContentValues c = new ContentValues();
        c.put("name", "John");
        c.put("Info", "Cool man");
        c.put("age", "32");
        resolver.insert(PERSON_ALL_URI, c);
    }
    public void btnInsert_onClick(View v)
    {
        ContentValues c = new ContentValues();
        c.put("name", "Johnson");
        c.put("Info", "another man");
        c.put("age", "34");
        resolver.insert(PERSON_ALL_URI, c);
    }
    public void btnDelete_onClick(View v)
    {
        resolver.delete(PERSON_ALL_URI, "name = ?", new String[]{"Johnson"});
    }
    public void btnQuery_onClick(View v)
    {
        String result = "";
        Cursor c = resolver.query(PERSON_ALL_URI, null, null, null, null);
        while (c.moveToNext())
        {
            result += "name : " + c.getString(c.getColumnIndex("name"))+ "\n";
            result += "info : " + c.getString(c.getColumnIndex("info"))+ "\n";
            result += "age : " + c.getString(c.getColumnIndex("age"))+ "\n";
            result += "\n\n";
        }
        txtInfo.setText(result);
    }
    public void btnUpdate_onClick(View v)
    {
        ContentValues c = new ContentValues();
        c.put("age", "18");
        resolver.update(PERSON_ALL_URI, c, "name = ?", new String[]{"John"});
    }

}
