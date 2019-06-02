package uk.co.fastpipe.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ScaleGestureDetector;
import uk.co.fastpipe.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MapActivity extends AppCompatActivity {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private TouchImageView mImageView;

    private String imageurl = "https://tfl.gov.uk/cdn/static/cms/images/tube-map.gif";
    private String fileName = "tube-map.gif";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //https://tfl.gov.uk/cdn/static/cms/images/tube-map.gif

        InputStream in = null;
        try {
            if (mapIsCached(this)) {
                in = loadMapFromCache(this);
            }
        }
        catch (IOException ex){
            //ignore
            ex.printStackTrace();
        }

        if (in==null){
            in = loadMapFromWeb(imageurl);
            try {
                cacheMap(this, in);
                in = loadMapFromCache(this);
            }
            catch (IOException ex){
                //ignore
                ex.printStackTrace();
            }
        }


        Bitmap bmpimg = BitmapFactory.decodeStream(in);
        mImageView = findViewById(R.id.imageMapView);
        mImageView.setImageBitmap(bmpimg);
        mImageView.setMaxZoom(15f);
    }



    private boolean mapIsCached(Context context) {
        File f = new File(getPathName(context));
        return f.exists();
    }

    private String getPathName(Context context) {
        return context.getCacheDir() + "/" + fileName;
    }

    private InputStream loadMapFromCache(Context context) throws IOException{
        File f = new File(getPathName(context));
        InputStream res = new FileInputStream(f);
        return res;
    }

    private void cacheMap(Context context,InputStream in) throws IOException {
        Path path = Paths.get(getPathName(context));
        Files.copy(in, path);
    }

    private InputStream loadMapFromWeb(String imageurl) {
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
        return in;
    }


}
