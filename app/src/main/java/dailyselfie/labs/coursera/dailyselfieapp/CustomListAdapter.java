package dailyselfie.labs.coursera.dailyselfieapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] names;
    private final File[] files;

    public CustomListAdapter(Activity context, int resource, String[] names, File[] files) {

        super(context,resource,names);
        this.context = context;
        this.names = names;
        this.files = files;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_daily_selfie_listing, null, true);

        TextView textView = (TextView) rowView.findViewById(R.id.textView1);
        String fileNameStr = files[position].getName();
        int startIndex = fileNameStr.indexOf("_");
        String timeStampStr = fileNameStr.substring(startIndex+1,startIndex+16);
        textView.setText(timeStampStr);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(files[position]);
        } catch(Exception e) {
        }
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        final int REQUIRED_IMAGE_SIZE = 50;
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
            fisNew = new FileInputStream(files[position]);
        } catch(Exception e) {
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fisNew,null,scaledOptions);
        imageView.setImageBitmap(bitmap);

        return rowView;
    }
}
