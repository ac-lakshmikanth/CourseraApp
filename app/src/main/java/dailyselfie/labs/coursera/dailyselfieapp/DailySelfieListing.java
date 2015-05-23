package dailyselfie.labs.coursera.dailyselfieapp;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class DailySelfieListing extends ListFragment {


    private static final String TAG = "Lab-DailySelfieListing";
    private static File[] fileNames;
    private static String[] names;

    public interface SelectionListener {
        public void onItemSelected(int position);
    }

    private SelectionListener mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //String[] names   = {"value11","value23","value33","value44","value55","value66","value77","value88","value99"};
        //setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.activity_daily_selfie_listing, names));

        File cacheDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //Toast.makeText(getActivity(), "getActivity :::: "+getActivity(), Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), "cacheDir :::: "+cacheDir, Toast.LENGTH_LONG).show();

        if(cacheDir!=null) {
            fileNames = cacheDir.listFiles();
            //Toast.makeText(getActivity(), "fileNames.length :::: "+fileNames.length, Toast.LENGTH_LONG).show();
            if(fileNames.length > 0) {
                names = new String[fileNames.length];
                for (int i = 0; i < fileNames.length; i++) {
                    names[i] = fileNames[i].getName();
                }
                setListAdapter(new CustomListAdapter(getActivity(), R.layout.activity_daily_selfie_listing, names, fileNames));
            }
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {

            mCallback = (SelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SelectionListener");
        }
    }

// Note: ListFragments come with a default onCreateView() method.
// For other Fragments you'll normally implement this method.
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        //return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.activity_daily_selfie_listing, container, false);
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "Entered onActivityCreated()");

    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {

        // Notify the hosting Activity that a selection has been made.

        mCallback.onItemSelected(position);

    }

}

