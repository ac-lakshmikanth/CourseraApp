package dailyselfie.labs.coursera.dailyselfieapp;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
//import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DailySelfieMain extends ActionBarActivity implements DailySelfieListing.SelectionListener {

    private DailySelfieListing mDailySelfieListing;
    private DailySelfiePreview mDailySelfiePreview;
    private Intent cameraIntent;
    private File[] fileNames = null;
    private FragmentManager fm = null;
    static final int TAKE_PICTURE_INTENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_selfie_main);

        fm = getFragmentManager();

        File cacheDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //Toast.makeText(getApplicationContext(), "cacheDir ::: "+cacheDir, Toast.LENGTH_LONG).show();
        if(cacheDir != null) {
            fileNames = cacheDir.listFiles();
            //Toast.makeText(getApplicationContext(), "fileNames.length :::: "+fileNames.length, Toast.LENGTH_LONG).show();
            if(fileNames.length > 0) {
                this.loadSelfiePics();
            }
        }

        setAlarm();
    }

    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(DailySelfieMain.this, DailySelfieAlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000, pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(2*60*1000),(2*60*1000),pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily_selfie_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.take_picture) {
            this.initiateCameraAction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initiateCameraAction() {

//        try {
//            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//            //cameraManager.getCameraCharacteristics();
//            Toast.makeText(getApplicationContext(), "camera:::"+cameraManager.getCameraIdList(), Toast.LENGTH_LONG).show();
//        } catch(Exception e) {
//            //
//        }

        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(cameraIntent.resolveActivity(getPackageManager())!=null){

            File cacheFile = null, cacheDir = null;

            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_kkmmss").format(new Date());
                String fileName = "SELFIE_"+timeStamp;

                cacheFile = File.createTempFile(fileName, ".jpg",getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                Uri imgUri = Uri.fromFile(cacheFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            } catch (Exception  io) {
                Toast.makeText(getApplicationContext(), "Error in initiateCameraAction", Toast.LENGTH_LONG).show();
            }
            startActivityForResult(cameraIntent, TAKE_PICTURE_INTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_LONG).show();

        if(requestCode == TAKE_PICTURE_INTENT && resultCode == RESULT_OK) {
            //Toast.makeText(getApplicationContext(), "loadSelfiePics", Toast.LENGTH_LONG).show();
            this.loadSelfiePics();
        }
    }

    public void onItemSelected(int position) {
        //Toast.makeText(getApplicationContext(), "position :::: "+fileNames[position], Toast.LENGTH_LONG).show();

        File cacheDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        fileNames = cacheDir.listFiles();
        this.loadSelfiePreview(fileNames[position]);
    }

    private void loadSelfiePics() {

        mDailySelfieListing = new DailySelfieListing();
        //FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, mDailySelfieListing);
        ft.commit();
    }

    private void loadSelfiePreview(File fileUrl) {

        mDailySelfiePreview = new DailySelfiePreview();
        //FragmentManager fm = getFragmentManager();
        Bundle args = new Bundle();
        args.putString("FILE_PATH",fileUrl.getAbsolutePath());
        mDailySelfiePreview.setArguments(args);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, mDailySelfiePreview);
        ft.addToBackStack("BACK_STACK");
        ft.commit();

        // execute transaction now
        fm.executePendingTransactions();
    }
}
