package uk.co.fastpipe.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

public class MapImageLoader {

    private static String imageurl = "https://tfl.gov.uk/cdn/static/cms/images/tube-map.gif";
    private static String fileName = "tube-map.gif";

    public static Bitmap loadMapImage(Context context) {
        InputStream in = null;
        try {
            if (mapIsCached(context)) {
                in = loadMapFromCache(context);
            }
        }
        catch (IOException ex){
            //ignore
            ex.printStackTrace();
        }

        if (in==null){
            in = loadMapFromWeb(imageurl);
            try {
                cacheMap(context, in);
                in = loadMapFromCache(context);
            }
            catch (IOException ex){
                //ignore
                ex.printStackTrace();
            }
        }


        return BitmapFactory.decodeStream(in);
    }


    private static boolean mapIsCached(Context context) {
        File f = new File(getPathName(context));
        return f.exists();
    }

    private static String getPathName(Context context) {
        return context.getCacheDir() + "/" + fileName;
    }

    private static InputStream loadMapFromCache(Context context) throws IOException{
        File f = new File(getPathName(context));
        InputStream res = new FileInputStream(f);
        return res;
    }

    private static void cacheMap(Context context,InputStream in) throws IOException {
        Path path = Paths.get(getPathName(context));
        Files.copy(in, path);
    }

    private static InputStream loadMapFromWeb(String imageurl) {
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
