package in.cdac.moviestageone;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell1 on 20/06/2018.
 */

@SuppressWarnings({"ALL", "UnusedAssignment", "RedundantCast"})
public class Activity2 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MoviePojo>> {

    private GridView gridView;
    private TextView empty;
    private final StringBuilder query1 = new StringBuilder();
    private String req1;
    private MoviePojo pojo1;
    private MovieAdapter movieAdapter1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_layout);

        empty=(TextView)findViewById(R.id.textView);
        gridView = (GridView) findViewById(R.id.gridview2);
        gridView.setEmptyView(empty);
        movieAdapter1 = new MovieAdapter(this, new ArrayList<MoviePojo>());
        gridView.setAdapter(movieAdapter1);
        movieAdapter1.notifyDataSetChanged();

        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if ((info != null) && (info.isConnected())) {

                int MOVIE_LOADER_ID = 1;
                getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);

            }

        } catch (Exception e) {
            e.getMessage();

        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pojo1= (MoviePojo) gridView.getItemAtPosition(position);
                id=parent.getSelectedItemId();
                parent.getChildAt(position);
                int i=parent.getSelectedItemPosition();
                if(pojo1!=null)
                {
                    id=pojo1.getId();
                    String poster=pojo1.getPoster_path();
                    Intent intent=new Intent(Activity2.this,MovieDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("pojo",pojo1);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });

    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {

        String qr1 = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
        query1.append(qr1);
        String API_KEY = BuildConfig.MOVIE_API_KEY;
        query1.append(API_KEY);
        req1 = query1.toString();

        return new MovieLoader(getApplicationContext(), req1);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<MoviePojo>> loader, List<MoviePojo> data) {

        gridView.getFirstVisiblePosition();
        if (data == null) {
            empty.setText(R.string.empty_textView);
        }
        movieAdapter1.clear();
        if((data!=null)&&(!data.isEmpty())) {
            movieAdapter1.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

        movieAdapter1.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(Activity2.this);
        menuInflater.inflate(R.menu.setting_pop, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.popular) {
            Intent in = new Intent(Activity2.this, MainActivity.class);
            startActivity(in);

        }
        Toast.makeText(Activity2.this, "sort by popular", Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pojo1=new MoviePojo();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("req1",req1);
        outState.putParcelable("pojo",pojo1);
    }
    @Override
    public Object onRetainCustomNonConfigurationInstance() {

        pojo1=new MoviePojo();
        return  pojo1;
    }

}
