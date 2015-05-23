package dailyselfie.labs.coursera.dailyselfieapp;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;

public class DailySelfiePreview extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_daily_selfie_preview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle data = getArguments();
        //Toast.makeText(getActivity(),"Bundle :::: "+data.getString("FILE_PATH"),Toast.LENGTH_LONG).show();

        ImageView imageView = (ImageView) getActivity().findViewById(R.id.previewImage);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(data.getString("FILE_PATH"));
        } catch(Exception e) {
            //
        }

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        final int REQUIRED_IMAGE_SIZE = 350;
        BitmapFactory.decodeStream(fis,null,bitmapOptions);
        int width_temp = bitmapOptions.outWidth, height_temp = bitmapOptions.outHeight;
        int scale = 1;
        while(true) {
            if((width_temp/2)<REQUIRED_IMAGE_SIZE || (height_temp/2)<REQUIRED_IMAGE_SIZE) {
                break;
            }
            width_temp/=2;
            height_temp/=2;
            scale++;
        }

        BitmapFactory.Options scaledOptions = new BitmapFactory.Options();
        scaledOptions.inSampleSize = scale;

        FileInputStream fisNew = null;
        try {
            fisNew = new FileInputStream(data.getString("FILE_PATH"));
        } catch(Exception e) {
            //
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fisNew,null,scaledOptions);
        imageView.setImageBitmap(bitmap);
    }
}
