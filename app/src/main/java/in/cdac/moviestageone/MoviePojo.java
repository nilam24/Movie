package in.cdac.moviestageone;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Comparator;

/**
 * Created by Dell1 on 18/05/2018.
 */

@SuppressWarnings("ALL")
public class MoviePojo implements Parcelable {

    private static String TAG = MoviePojo.class.getName();
    private long id;
    private String poster_path;
    private String title;
    private String release_date;
    private double vote_average;
    private String overview;
    private double popularity;

    public MoviePojo() {

    }

    public MoviePojo(long id, String poster_path, String title, String release_date, double vote_average, String overview) {
        this.id = id;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    public MoviePojo(long id, String poster_path, String title, String release_date, double vote_average, String overview, double popularity) {
        this.id = id;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
        this.popularity = popularity;
    }

    protected MoviePojo(Parcel in) {
        id = in.readLong();
        poster_path = in.readString();
        title = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        popularity = in.readDouble();

    }

    public static final Creator<MoviePojo> CREATOR = new Creator<MoviePojo>() {
        @Override
        public MoviePojo createFromParcel(Parcel in) {
            return new MoviePojo(in);
        }

        @Override
        public MoviePojo[] newArray(int size) {
            return new MoviePojo[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }


    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeDouble(vote_average);
        dest.writeString(overview);
        dest.writeDouble(popularity);
    }

    public static final Comparator<MoviePojo> popular_compare = new Comparator<MoviePojo>() {
        @Override
        public int compare(MoviePojo o1, MoviePojo o2) {
            double pop1 = o1.getPopularity();
            double pop2 = o2.getPopularity();

            return (int) (pop2 - pop1);
        }

    };

    public static final Comparator<MoviePojo> rate_compare = new Comparator<MoviePojo>() {
        @Override
        public int compare(MoviePojo p1, MoviePojo p2) {

            double rate1 = p1.getVote_average();
            double rate2 = p2.getVote_average();

            return (int) (rate2-rate1);
        }
    };
    @Override
    public String toString() {

        return poster_path + "   " + release_date + "  " + vote_average + ";" + popularity;
    }
}

