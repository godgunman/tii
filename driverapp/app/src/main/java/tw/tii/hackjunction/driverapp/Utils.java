package tw.tii.hackjunction.driverapp;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by ggm on 11/7/15.
 */
public class Utils {

    public static void initParseData() {

        String[] names = {"David", "Joseph", "Michael", "Moshe", "Daniel",
                "Benjamin", "James", "Jacob", "Jack", "Alexander"};
        double[][] points = {{60.186362, 24.969325},
                {60.184325, 24.923605}, {60.171955, 24.938309}, {60.163226, 24.947923},
                {60.148675, 24.922370}, {60.162314, 24.892223}, {60.189569, 24.885190},
                {60.206215, 24.858339}, {60.205490, 24.929457}, {60.244677, 24.946367}};

        for (int i = 0; i < names.length; i++) {
            ParseObject object = new ParseObject("Request");
            object.put("location", new ParseGeoPoint(points[i][0], points[i][1]));
            object.put("username", names[i]);
            object.saveInBackground();
        }
    }
}
