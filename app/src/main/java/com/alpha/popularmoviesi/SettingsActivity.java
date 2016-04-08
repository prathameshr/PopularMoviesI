package com.alpha.popularmoviesi;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        SettingsActivityFragment mFragment = new SettingsActivityFragment();
        mFragmentTransaction.replace(android.R.id.content, mFragment);
        mFragmentTransaction.commit();
    }


}
