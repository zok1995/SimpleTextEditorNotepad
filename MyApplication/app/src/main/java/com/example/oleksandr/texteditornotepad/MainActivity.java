package com.example.oleksandr.texteditornotepad;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText mEditTextMainField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextMainField = (EditText) findViewById(R.id.editText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        float fSize = Float.parseFloat(sharedPreferences.getString("Size", "20"));
        mEditTextMainField.setTextSize(fSize);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionClear:
                mEditTextMainField.setText("");
                Toast.makeText(getApplicationContext(),"Empty!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionSettings:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
