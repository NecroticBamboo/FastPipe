package uk.co.fastpipe.Map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ScaleGestureDetector;
import uk.co.fastpipe.R;
import uk.co.fastpipe.models.MapImageLoader;


public class MapActivity extends AppCompatActivity {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private TouchImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //https://tfl.gov.uk/cdn/static/cms/images/tube-map.gif

        Bitmap bmpimg = MapImageLoader.loadMapImage(this);
        mImageView = findViewById(R.id.imageMapView);
        mImageView.setImageBitmap(bmpimg);
        mImageView.setMaxZoom(15f);
    }
}
