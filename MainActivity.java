package com.androiddev.will.speaknotes;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity implements DirActivityFragment.OnItemSelectListener {

    //final String ROOTDIR_NOTEBOOKS = Environment.getExternalStorageDirectory().getAbsolutePath();
    // ROOTDIR_NOTEBOOKS = /storage/sdcard
    //Moved this to DirActivityFragment.java because I could not access it there from here.
    final String DEBUG_TAG = "MainActivity.java; Speak Notes: ";

    @SuppressLint("LongLogTag")//So it doesnt complain about long log tags....
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DirActivityFragment dirFragment = new DirActivityFragment();

        //This [below] loads the fragment container [activity_main.xml] at runtime so i
        //can switch it between the dir and file activity fragments
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, dirFragment).commit();
    }

    //Called when a gridView item is clicked
    @SuppressLint("LongLogTag")
    public void onItemSelected(int position){
        Log.v(DEBUG_TAG, "onItemSelected(); position = " + position);
        //I have the position that was clicked from the DirActivityFragment gridview
        //I need the name of the directory...or item....clicked by user.
        FileActivityFragment fileFrag = new FileActivityFragment();
        FragmentTransaction transAction = getSupportFragmentManager().beginTransaction();
        transAction.replace(R.id.fragment_container, fileFrag);
        transAction.addToBackStack(null);
        transAction.commit();

    }

    /*public void switchFragment(){
        //return "You Got It!";
        //FileActivityFragment fileFrag = new FileActivityFragment();
        //getSupportFragmentManager().beginTransaction()
                //.replace(R.id.fragment_container, new FileActivityFragment(), "").commit();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
