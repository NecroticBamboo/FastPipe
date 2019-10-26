package uk.co.fastpipe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import uk.co.fastpipe.Map.TouchImageView;
import uk.co.fastpipe.graph.Graph;
import uk.co.fastpipe.graph.Node;
import uk.co.fastpipe.models.MapImageLoader;
import uk.co.fastpipe.models.StationMask;
import uk.co.fastpipe.models.TubeGraph;
import uk.co.fastpipe.models.TubeReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteMap extends AppCompatActivity {

    private TouchImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        Bitmap masterImage = MapImageLoader.loadMapImage(this);
        Bitmap shinyImage=makeShiny(masterImage,128);
        mImageView = findViewById(R.id.imageMapView);


        try {
            StationMask[] stationsMaskArray = readStationMask(getResources().openRawResource(R.raw.station_masks));
            HashMap<String,StationMask> stationMaskHashMap = new HashMap<>();
            for(StationMask m:stationsMaskArray){
                stationMaskHashMap.put(prepareStationName(m.getStations().replaceAll("\\.png","")),m);
            }
            Intent intent = getIntent();

            String routeStr = intent.getStringExtra(FastPipeActivity.ROUTE_STRING);
            String[] ids;

// Must call equals because Java compares strings by reference
            if (routeStr.equals("")) {
                ids = new String[0];

            } else {
                // split route str by ','
                ids = routeStr.split(",");
            }

            // load tube graph
            TubeGraph tube = TubeReader.load(this);
            Graph g = tube.generateGraph();

            final ArrayList<Node> list = new ArrayList<>();

            //final LinearLayout listView2 = findViewById(R.id.linearLayout);

            // find stations by their IDs
            for (int i = 0; i < ids.length; i++) {
                // convert each item to number (ID)
                int id = Integer.parseInt(ids[i]);

                // take station names and add them to the list
                Node n = g.getNodeById(id);
                list.add(n);
            }

            Bitmap mask = makeMask(masterImage, stationMaskHashMap, list);
            mImageView.setImageBitmap(combine(masterImage,shinyImage,mask));
            mImageView.setMaxZoom(15f);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap makeMask(Bitmap masterImage, HashMap<String, StationMask> stationMaskHashMap, ArrayList<Node> list) {
        Bitmap bmp;
        Resources res = this.getResources();
        int w = masterImage.getWidth(), h = masterImage.getHeight();
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,w,h,paint);

        for(int i=0;i<list.size()-2;i++){
            String st1=prepareStationName(list.get(i).getName());
            String st2=prepareStationName(list.get(i+1).getName());
            String name = st1 + "_" + st2;
            int id = res.getIdentifier( name, "raw", this.getPackageName() );
            if(id==0){
                name = st2 + "_" + st1;
                id=res.getIdentifier( name, "raw", this.getPackageName() );
            }
            if(id==0) {
                continue;
            }
            Bitmap mask = makeTransparent(BitmapFactory.decodeResource(getResources(),id), Color.WHITE);
            StationMask sm=stationMaskHashMap.get(name);

            canvas.drawBitmap(mask,(float) sm.getY(),(float) sm.getX(),new Paint());
        }
        return bmp;
    }

    public static Bitmap makeTransparent(Bitmap bit, int transparentColor) {
        int width =  bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int [] allpixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());
        //myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        for(int i =0; i<myBitmap.getHeight()*myBitmap.getWidth();i++){
                allpixels[i] = (allpixels[i] & 0xFF) == 0 ? allpixels[i] | 0xFF000000 : Color.TRANSPARENT;
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }

    public static Bitmap makeShiny(Bitmap bit,int brightness) {
        Bitmap image=bit.copy(Bitmap.Config.ARGB_8888,true);
        int width =  bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int [] allpixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        for(int i =0; i<myBitmap.getHeight()*myBitmap.getWidth();i++){
            int color = allpixels[i];

            int r = (color >> 16) & 0xFF;
            r = r + brightness;
            if (r > 255)
                r = 255;

            int g = (color >> 8) & 0xFF;
            g = g + brightness;
            if (g > 255)
                g = 255;

            int b = color &0xFF;
            b = b + brightness;
            if (b > 255)
                b = 255;

            allpixels[i] = Color.argb(255, r,g,b);
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }

    public static Bitmap combine(Bitmap background,Bitmap overlay,Bitmap mask) {
        Bitmap myBitmap=background.copy(Bitmap.Config.ARGB_8888,true);

        int [] resultPixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
        myBitmap.getPixels(resultPixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());

        int [] overlayPixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
        overlay.getPixels(overlayPixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());

        int [] maskPixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
        mask.getPixels(maskPixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());

        for(int i =0; i<myBitmap.getHeight()*myBitmap.getWidth();i++){
            if((maskPixels[i] & 0XFF) ==0){
                resultPixels[i]=overlayPixels[i];
                resultPixels[i]=0xFF555555;
            }
        }

        myBitmap.setPixels(resultPixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }

    private static String prepareStationName(String s){
        return s.toLowerCase().replaceAll(" ","");
    }
    private static StationMask[] readStationMask(InputStream data) throws IOException {
        StationMask[] stationsMaskArray;
        try (Reader stationsReader = new InputStreamReader(data)) {
            CsvToBean<StationMask> csvToBean = new CsvToBeanBuilder(stationsReader)
                    .withType(StationMask.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<StationMask> stationsMaskList = csvToBean.parse();

            stationsMaskArray = stationsMaskList.toArray(new StationMask[stationsMaskList.size()]);
        }
        return stationsMaskArray;
    }

}
