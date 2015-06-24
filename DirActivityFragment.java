package com.androiddev.will.speaknotes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
//import android.app.FragmentTransaction;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.io.File;
import java.io.IOException;


/**
 * This fragment will be the start to the Speak Notes App
 *
 * When the application opens this is the first screen
 * it will contain all of the notebooks that were created by the user
 * may also have some default folders/notebooks
 *
 *
 */
public class DirActivityFragment extends Fragment {

    final String ROOTDIR_NOTEBOOKS = Environment.getExternalStorageDirectory().getAbsolutePath()
            .toString() + "/SpeakNotes_Root/";
    // ROOTDIR_NOTEBOOKS = /storage/sdcard/SpeakNotes_Root
    final String DEBUG_TAG = "DirActivityFragment.java";
    private File[] child_Dirs;
    View view;
    GridView gridview;
    OnItemSelectListener mCallback;

    public DirActivityFragment() {
    }

    public interface OnItemSelectListener {
        public void onItemSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (OnItemSelectListener) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.v(DEBUG_TAG, "ROOTDIR_NOTEBOOKS path = " + ROOTDIR_NOTEBOOKS);
        File root_Dir = new File(ROOTDIR_NOTEBOOKS);
        Log.v(DEBUG_TAG, "root_Dir.listFiles() = " + root_Dir.listFiles());

        if(root_Dir.isDirectory()){
            //This is the root directory
            //Try to list all of the child directories
            child_Dirs = root_Dir.listFiles();
            /*if(child_Dirs.length == 0){
                Log.v(DEBUG_TAG, "There were no child Dirs found.......");
            }
            This [above] is to check if the root_Dir has any child directories....may not need this....*/
            for (File f : child_Dirs){
                if(f.isDirectory()){
                    Log.v(DEBUG_TAG, "One Child Directory is: " + f.getName());
                    Log.v(DEBUG_TAG, "One Child Directory is: " + f);

                    //For each directory I need to display a button with the dir's name as label or title...
                }//end if
            }//end for
        }//end if(root_Dir.isDirectory()

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //When an item is clicked i want to perform a fragment transaction displaying
        //file activity fragment
        //final FileActivityFragment fileFrag = new FileActivityFragment();
        //final FragmentTransaction fTransaction = getActivity().getFragmentManager().beginTransaction();

        //fTransaction.replace(R.id.fragment_container, fileFrag);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(DEBUG_TAG, "position = " + position);
                Log.v(DEBUG_TAG, "id = " + id);
                Log.v(DEBUG_TAG, "position = " + child_Dirs[position].getName());
                //I can use the .getName to pass into the next fragment so it knows
                //what directory to switch into to display the correct files.
                //fTransaction.addToBackStack(null);
                //fTransaction.commit();
                //((MainActivity)getActivity()).switchFragment();
                //switchActivity();
                mCallback.onItemSelected(position);
            }
        });

    }

    /*public void switchActivity(){
        FileActivityFragment fileFrag = new FileActivityFragment();
        FragmentTransaction fTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fTransaction.replace(R.id.fragment_container, fileFrag);
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }*/

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        //Initialize the arrayAdapter
        ArrayAdapter<File> adapter = new ArrayAdapter<File>(getActivity(),
                android.R.layout.simple_list_item_1, child_Dirs);

        gridview = (GridView)view.findViewById(R.id.dir_grid_view);
        gridview.setAdapter(adapter);
        //Log.v(DEBUG_TAG, "gridview = " + gridview);

        return view;
    }


}
