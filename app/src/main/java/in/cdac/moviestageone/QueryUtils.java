package in.cdac.moviestageone;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dell1 on 11/05/2018.
 */

@SuppressWarnings("ALL")
public class QueryUtils {


    private static final String TAG = QueryUtils.class.getName();

    static HttpURLConnection httpURLConnection = null;
    static InputStream inputStream = null;
    String query = "";
    Context context;
    static List<MoviePojo> list;

    public QueryUtils() {

    }

    public static List<MoviePojo> fetchMoviedetails(String reqUrl) {

        URL url = createUrl(reqUrl);
        String res = makeHTTPConnection(url);
        List<MoviePojo> list = JsonFormat(res);

        return list;

    }

    private static URL createUrl(String queryRequest) {

        URL url = null;
        try {
            url = new URL(queryRequest);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "" + url);

        return url;

    }

    private static String makeHTTPConnection(URL url) {

        int READ_TIME_OUT=1500;
        int CONNECT_TIME_OUT=1200;

        String res = "";

        if (url == null) {
            return res;
        }

        try {

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                res = readInputStream(inputStream);

                Log.e(TAG, " " + res);
            } else {
                Log.e(TAG, "not ok  ");

            }

        }
        catch (SocketException s)
        {
            s.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();

        }

        return res;

    }

    private static String readInputStream(InputStream stream) {

        StringBuilder stringBuilder = new StringBuilder();
        Log.e(TAG, "inputstream==" + stream);
        try {

            if (stream != null) {
                stream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                if (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
            }

        } catch (SocketTimeoutException ex) {
            ex.printStackTrace();

        }
        catch (IOException io)
        {
            io.printStackTrace();
        }

        Log.e(TAG, stringBuilder.toString());

        return stringBuilder.toString();

    }

    private static List<MoviePojo> JsonFormat(String json) {

        String poster_path = null;
        long id;

        List<MoviePojo> pojoList = new ArrayList<>();

        if (json == null) {
            return null;
        }

        try {
            JSONObject rootObj = new JSONObject(json);
            JSONArray resArray = rootObj.getJSONArray("results");
            int i = 0;
            for (i = 0; i < resArray.length(); i++) {

                JSONObject resObj = resArray.getJSONObject(i);
                poster_path = resObj.optString("poster_path");
                id = resObj.optLong("id");

                String title = resObj.optString("title");
                double voteAvg = resObj.optDouble("vote_average");
                String overview = resObj.optString("overview");
                String release_date = resObj.optString("release_date");

                double popularity = resObj.optDouble("popularity");

                MoviePojo pojo = new MoviePojo(id, poster_path, title, release_date, voteAvg, overview, popularity);
                pojoList.add(pojo);

            }

        } catch (Exception e) {

            Log.e(TAG, e.getMessage());

        }

        Log.e(TAG, "list value*************" + pojoList);

        return pojoList;

    }

}
