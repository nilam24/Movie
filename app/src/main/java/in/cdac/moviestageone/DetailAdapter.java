package in.cdac.moviestageone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Dell1 on 01/06/2018.
 */

@SuppressWarnings("ALL")
class DetailAdapter extends ArrayAdapter<MoviePojo> {

    private List<MoviePojo> list;
    private long id;

    public DetailAdapter(Context context) {
        super(context, 0, 0);

    }

    public DetailAdapter(Context context, List<MoviePojo> list, long id) {
        super(context, 0, list);

        this.list = list;
        this.id = id;

    }

    public DetailAdapter(@NonNull Context context, int resource, @NonNull List<MoviePojo> list) {
        super(context, 0, list);

        this.list = list;

    }

    @Nullable
    @Override
    public MoviePojo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable MoviePojo item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View itemview = convertView;
        Context context = parent.getContext();
        if (itemview == null) {
            itemview = LayoutInflater.from(context).inflate(R.layout.activity_movie_detail, parent, false);
        }

        TextView titleText, release_datText, votAvgText, plot_synopsisText;
        ImageView img2;

        titleText = (TextView) itemview.findViewById(R.id.textView1);
        release_datText = (TextView) itemview.findViewById(R.id.textView2);
        votAvgText = (TextView) itemview.findViewById(R.id.textView3);
        plot_synopsisText = (TextView) itemview.findViewById(R.id.textView4);
        img2 = (ImageView) itemview.findViewById(R.id.imageView2);

        MoviePojo pojoItem = new MoviePojo();
        pojoItem = getItem(position);
        long id = pojoItem.getId();

        String title = pojoItem.getTitle();
        titleText.setText(title);

        String dat = pojoItem.getRelease_date();
        release_datText.setText(dat);

        double popu = pojoItem.getPopularity();
        String pop = String.valueOf(popu);
        double votAvg = pojoItem.getVote_average();
        String vot = String.valueOf(votAvg);
        votAvgText.setText(vot);

        String overview = pojoItem.getOverview();
        plot_synopsisText.setText(overview);

        String imglink = pojoItem.getPoster_path();

        String baseUrl = "http://image.tmdb.org/t/p/";
        String width = "w185";

        URL url = null;
        try {
            url = new URL(baseUrl + width + imglink);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Picasso.get().load(url.toString()).into(img2);

        return itemview;
    }
}
