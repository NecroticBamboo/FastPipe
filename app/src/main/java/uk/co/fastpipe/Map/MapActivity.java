package uk.co.fastpipe.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ScaleGestureDetector;
import uk.co.fastpipe.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MapActivity extends AppCompatActivity {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private TouchImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
//        https://tfl.gov.uk/cdn/static/cms/images/tube-map.gif


        String imageurl = "https://tfl.gov.uk/cdn/static/cms/images/tube-map.gif";
        InputStream in = null;

        try
        {
            Log.i("URL", imageurl);
            URL url = new URL(imageurl);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.connect();

            in = httpConn.getInputStream();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Bitmap bmpimg = BitmapFactory.decodeStream(in);
        mImageView = findViewById(R.id.imageMapView);
        mImageView.setImageBitmap(bmpimg);
        mImageView.setMaxZoom(15f);
    }

}
