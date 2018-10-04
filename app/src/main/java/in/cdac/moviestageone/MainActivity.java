package in.cdac.moviestageone;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MoviePojo>> {

    private static final String TAG = MainActivity.class.getName();
    private final int Movie_Loader_ID = 1;
    private TextView emptyview;
    private GridView movieThumb_grid;
    ListView listView11;
    private MovieAdapter movieAdapter;
    private MoviePojo pojo;
    private final String qr = "http://api.themoviedb.org/3/movie/popular?api_key=";
    private final StringBuilder query = new StringBuilder();
    private final String API_KEY = BuildConfig.MOVIE_API_KEY;
    private String req;
    private String req1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyview = (TextView) findViewById(R.id.textView1);
        movieThumb_grid = (GridView) findViewById(R.id.gridview1);

        pojo = new MoviePojo();

        movieAdapter = new MovieAdapter(this, new ArrayList<MoviePojo>());
        movieThumb_grid.setAdapter(movieAdapter);
        movieAdapter.clear();

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

            if (info != null && info.isConnected()) {

                getSupportLoaderManager().initLoader(Movie_Loader_ID, null, this);


            } else {

                emptyview.setText(R.string.empty_textView);
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "catched" + e.getMessage());
        }

        movieThumb_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pojo = (MoviePojo) movieThumb_grid.getItemAtPosition(position);
                id = parent.getSelectedItemId();
                parent.getChildAt(position);
                int i = parent.getSelectedItemPosition();
                if (pojo != null) {

                    id = pojo.getId();
                    String path = pojo.getPoster_path();

                    Intent in = new Intent(MainActivity.this, MovieDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("pojo", pojo);
                    in.putExtras(bundle);
                    startActivity(in);

                }

            }
        });

    }

    @Override
    public Loader<List<MoviePojo>> onCreateLoader(int id, Bundle args) {

        query.append(qr);
        query.append(API_KEY);
        req = query.toString();

        return new MovieLoader(this, req);

    }

    @Override
    public void onLoadFinished(Loader<List<MoviePojo>> loader, List<MoviePojo> data) {

        movieThumb_grid.getFirstVisiblePosition();

        if (data == null) {
            emptyview.setText(R.string.textView);
        }
        movieAdapter.clear();

        if ((data != null) && (!data.isEmpty())) {

            movieAdapter.addAll(data);

        }

    }

    @Override
    public void onLoaderReset(Loader<List<MoviePojo>> loader) {

        movieAdapter.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.high_rated) {

            Intent in = new Intent(MainActivity.this, Activity2.class);
            startActivity(in);
        }
        Toast.makeText(MainActivity.this, "sort by highest rate", Toast.LENGTH_LONG).show();

        return true;

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        pojo = new MoviePojo();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("req", req);
        outState.putParcelable("pojo", pojo);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {

        pojo = new MoviePojo();
        if (pojo != null) {
            return pojo;
        }
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

