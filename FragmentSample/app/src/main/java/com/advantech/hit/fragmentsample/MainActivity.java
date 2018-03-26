package com.advantech.hit.fragmentsample;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    FragmentOne f1 = new FragmentOne();
    FragmentTwo f2 = new FragmentTwo();
    FragmentManager fragmentManager = getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add fragment 1 and 2
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainner, f1);
        fragmentTransaction.add(R.id.fragmentContainner, f2);
        fragmentTransaction.commit();
    }
    public void btnStartFrag1_onClick(View v)
    {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(f1);
        fragmentTransaction.hide(f2);
        fragmentTransaction.commit();

    }

    public void btnStartFrag2_onClick(View v)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(f2);
        fragmentTransaction.hide(f1);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this,"Been touched",Toast.LENGTH_SHORT).show();
    }
}
