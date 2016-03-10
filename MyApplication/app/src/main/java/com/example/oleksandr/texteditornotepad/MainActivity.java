package com.example.oleksandr.texteditornotepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends Activity {
    private EditText mEditTextMainField;
    private String mFileName = null;
    private String mPath = Environment.getExternalStorageDirectory().toString() + "/files/";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextMainField = (EditText) findViewById(R.id.editText);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        float fSize = Float.parseFloat(sharedPreferences.getString(getString(R.string.size), "20"));
        mEditTextMainField.setTextSize(fSize);

        String regular = sharedPreferences.getString(getString(R.string.preference_style), "");
        int iTypeface = Typeface.NORMAL;
        if (regular.contains("Bold"))
            iTypeface += Typeface.BOLD;

        if (regular.contains("Italics"))
            iTypeface += Typeface.ITALIC;

        mEditTextMainField.setTypeface(null, iTypeface);

        if (sharedPreferences.getBoolean(getString(R.string.preference_color_red), false)) {
            mEditTextMainField.setTextColor(Color.RED);
        }

        if (sharedPreferences.getBoolean(getString(R.string.preference_color_white), false)) {
            mEditTextMainField.setTextColor(Color.WHITE);
        }

        if (sharedPreferences.getBoolean(getString(R.string.preference_color_black), false)) {
            mEditTextMainField.setTextColor(Color.BLACK);
        }

        if (sharedPreferences.getBoolean(getString(R.string.preference_color_green), false)) {
            mEditTextMainField.setTextColor(Color.GREEN);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionClear:
                mEditTextMainField.setText("");
                Toast.makeText(getApplicationContext(), "Empty!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionSettings:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.actionOpen:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("File name");
                builder.setMessage("Enter file name for opening");
                final EditText editText = new EditText(this);
                builder.setView(editText);
                builder.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEditTextMainField.setText("");
                        String value = editText.getText().toString();
                        mFileName = value;
                        File file = new File(mPath + mFileName);
                        if (file.exists() && file.isFile()) {
                            mEditTextMainField.setText(openFile(mFileName));
                        } else {
                            Toast.makeText(getApplicationContext(), "File not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
                break;
            case R.id.actionSave:
                AlertDialog.Builder builder1= new AlertDialog.Builder(this);
                builder1.setTitle("File name");
                builder1.setMessage("Enter file name for saving");
                final EditText editText1 = new EditText(this);
                builder1.setView(editText1);
                builder1.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value1 = editText1.getText().toString();
                        mFileName = value1;
                        saveFile(mFileName, mEditTextMainField.getText().toString());
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG);
                    }
                });
                builder1.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Cancled", Toast.LENGTH_LONG);
                    }
                });
                builder1.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFile(String mFileName, String body){
        try{
            File root = new File(this.mPath);
            if (!root.exists()){
                root.mkdirs();
            }
            File file = new File(root, mFileName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(body);
            fileWriter.flush();
            fileWriter.close();
            Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT);
        }catch (Exception e){

        }
    }

    private String openFile(String mFileName) {
        StringBuilder text = new StringBuilder();
        try {
            File file = new File(this.mPath, mFileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                text.append(line + "\n");
            }

        } catch (Exception e){

        }
        return text.toString();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.oleksandr.texteditornotepad/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.oleksandr.texteditornotepad/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
