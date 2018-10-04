package in.cdac.moviestageone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by Dell1 on 11/05/2018.
 */

@SuppressWarnings("ALL")
class MovieAdapter extends ArrayAdapter<MoviePojo> {

    private List<MoviePojo> list;
    private URL url = null;
    private MoviePojo pojo;
    MoviePojo p;
    long id;
    String title;
    String release_dat;
    double votAvg;
    String Plot_synopsis;
    double popular;

    public MovieAdapter(Context context) {
        super(context, 0, 0);
    }

    public MovieAdapter(Context context, List<MoviePojo> list) {
        super(context, 0, list);

        this.list = list;

    }

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<MoviePojo> list) {

        super(context, resource, list);
        this.list = list;

    }

    @Override
    public int getCount() {

        return super.getCount();

    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getPosition(@Nullable MoviePojo item) {

        return super.getPosition(item);

    }

    @Nullable
    @Override
    public MoviePojo getItem(int position) {

        return super.getItem(position);

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View itemview = convertView;
        final Context context = parent.getContext();
        String baseUrl = "http://image.tmdb.org/t/p/";
        String width = "w185";

        if (itemview == null) {
            itemview = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
        }

        final ImageView img;
        img = (ImageView) itemview.findViewById(R.id.imageView);

        pojo = new MoviePojo();

        pojo = getItem(position);

        String path = pojo.getPoster_path();
        popular = pojo.getPopularity();
        votAvg = pojo.getVote_average();


        try {
            url = new URL(baseUrl + width + path);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Picasso.get().load(url.toString()).resize(135, 120).into(img);

        return itemview;

    }

}
