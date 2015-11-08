package tw.tii.hackjunction.passengerapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ggm on 11/8/15.
 */
public class Utils {

    public static byte[] urlToBytes(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getGEOUrl(String address) {
        try {
            address = URLEncoder.encode(address, "utf-8");
            return "https://maps.googleapis.com/maps/api/geocode/json?address=" + address;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getAddressFromJSON(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            String formattedAddress = object.getJSONArray("results")
                    .getJSONObject(0)
                    .getString("formatted_address");
            return formattedAddress;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
