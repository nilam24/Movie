package in.cdac.moviestageone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"EmptyMethod", "ConstantConditions", "deprecation"})
public class MovieDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MoviePojo>> {

    private TextView emptyViewText;
    private static final String TAG = MovieDetailActivity.class.getName();
    private List<MoviePojo> moviePojoList;
    private MoviePojo pojo;
    private DetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list);

        emptyViewText = findViewById(R.id.emptyview);
        ListView listview = findViewById(R.id.listview1);
        listview.setEmptyView(emptyViewText);

        Bundle bundle = getIntent().getExtras();

        Log.e(TAG, "--------" + bundle);

        if (bundle != null) {

            pojo = new MoviePojo();
            pojo = bundle.getParcelable("pojo");

            moviePojoList = new ArrayList<>();
            moviePojoList.add(pojo);

            long id_movie = 0;
            adapter = new DetailAdapter(this, moviePojoList, id_movie);
            listview.setAdapter(adapter);
            Log.e(TAG, "id==" + id_movie);

        } else {

            emptyViewText.setText(R.string.textView);
            Log.e(TAG, "bundle" + bundle);
        }

        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if ((networkInfo != null) && (!networkInfo.isConnected())) {
                int MOVIE_LOADER_ID = 0;
                getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);

            }
        } catch (NullPointerException e) {

            Log.e(TAG, "" + e.getMessage());

        }

    }

    @NonNull
    @Override
    public Loader<List<MoviePojo>> onCreateLoader(int id, Bundle args) {

        return new MovieLoader(MovieDetailActivity.this, moviePojoList);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<MoviePojo>> loader, List<MoviePojo> data) {

        moviePojoList = data;

        if (data != null) {
            adapter.addAll(data);

        } else {
            emptyViewText.setText(R.string.textView);

        }

        adapter.clear();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<MoviePojo>> loader) {

        adapter.clear();

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

        outState.putParcelable("pojo", pojo);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
