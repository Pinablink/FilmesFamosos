package br.com.nanodegree.pinablink;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.engine.persistence.PopularMoviesAddRemoveFav;
import br.com.nanodegree.pinablink.engine.persistence.PopularMoviesStateFav;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesBase64ImageExtractor;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesMsg;

public class DetailFavActivity
        extends AppCompatActivity {

    private TextView txtvTitle;
    private TextView txtvSinopse;
    private TextView txtvVoteAverage;
    private TextView txtvReleaseDate;
    private ImageView imgBackDrop;
    private Movie refMovie;
    private ScrollView scrollViewRefer;
    private ProgressBar progressBarRefer;
    private final String STATE_REF_MOVIE = "MOVIE_CURRENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fav);
        ActionBar actionBar = getSupportActionBar();
        this.txtvTitle  = (TextView) this.findViewById(R.id.dfavMTitle);
        this.txtvSinopse = (TextView) this.findViewById(R.id.dfavMSinopse);
        this.txtvVoteAverage = (TextView) this.findViewById(R.id.dfavMVoteAverage);
        this.txtvReleaseDate = (TextView) this.findViewById(R.id.dfavMReleaseDate);
        this.imgBackDrop = (ImageView) this.findViewById(R.id.dfavBackDrop_image);
        this.scrollViewRefer = (ScrollView) this.findViewById(R.id.dfavscrollViewDetail);
        this.progressBarRefer = (ProgressBar) this.findViewById(R.id.dfavProgress);
        this.actionBarEnabledDisplayHome(actionBar);
    }

    private void actionBarEnabledDisplayHome (ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.refMovie == null) {
            Intent mIntent = this.getIntent();
            String keyPutExtra = this.getApplicationContext().
                    getString(R.string.name_movie_trans_activity);
            this.refMovie = mIntent.getExtras().getParcelable(keyPutExtra);
        }

        final String strTitle = this.refMovie.getTitle();
        final String strSinopse = this.refMovie.getOverview();
        final String strVoteAverage = this.refMovie.getVoteAverage();
        final String strReleaseDate = this.refMovie.getReleaseDate();
        final String strBackDropImage64 = this.refMovie.getBackDropImageBase64();

        Bitmap bitmap = PopularMoviesBase64ImageExtractor.getBitmapStrEncoded(strBackDropImage64);

        this.txtvTitle.setText(strTitle);
        this.txtvSinopse.setText(strSinopse);
        this.txtvVoteAverage.setText(strVoteAverage);
        this.txtvReleaseDate.setText(strReleaseDate);
        this.imgBackDrop.setImageBitmap(bitmap);

        this.scrollViewRefer.setVisibility(View.VISIBLE);
        this.progressBarRefer.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_REF_MOVIE, this.refMovie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.refMovie = savedInstanceState.getParcelable(STATE_REF_MOVIE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popular_movies_detail_fav_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = this.getApplicationContext();
        int idSelected = item.getItemId();
        int idMenuItem = R.id.menu_item_detail_fav;
        String msg = context.getString(R.string.app_remove_favorite);

        if (idSelected == idMenuItem) {
            PopularMoviesStateFav.removeFav(context, this.refMovie);
            PopularMoviesAddRemoveFav popularMoviesRemoveFav =
                    new PopularMoviesAddRemoveFav(this.refMovie, context);
            popularMoviesRemoveFav.remove();
            new PopularMoviesMsg().showToastMsgInfo(msg, context);
            Intent intentReturn = new Intent(context, FavoriteActivity.class);
            startActivity(intentReturn);
        }


        return super.onOptionsItemSelected(item);
    }
}
