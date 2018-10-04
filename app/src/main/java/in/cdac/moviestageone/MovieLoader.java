package in.cdac.moviestageone;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell1 on 18/05/2018.
 */

@SuppressWarnings("ALL")
class MovieLoader extends AsyncTaskLoader<List<MoviePojo>> {


    private final Context context;
    private String resQery;
    private List<MoviePojo> list;

    public MovieLoader(Context context, List<MoviePojo> list) {
        super(context);
        this.context = context;
        this.list = list;

    }

    public MovieLoader(Context context,  String resquery) {
        super(context);
        this.context = context;
        this.resQery = resquery;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<MoviePojo> loadInBackground() {

        List<MoviePojo> movielist = new ArrayList<>();
        if (resQery == null) {
            return null;
        }

             return QueryUtils.fetchMoviedetails(resQery);

    }

}
