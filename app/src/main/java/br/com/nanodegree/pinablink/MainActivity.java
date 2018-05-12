package br.com.nanodegree.pinablink;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataObject.PopularMovies;
import br.com.nanodegree.pinablink.engine.adapter.PopularMoviesPosterAdapter;
import br.com.nanodegree.pinablink.engine.network.task.ActivityTask;
import br.com.nanodegree.pinablink.engine.network.task.PopularMoviesNetworkTask;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesCertAcessNetwork;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesMsg;


public class MainActivity
        extends ActivityTask {


    private RecyclerView recycleViewPosterPresentation;
    private ProgressBar progressProcess;

    protected void initResourceScreen() {
        this.recycleViewPosterPresentation = (RecyclerView) findViewById(R.id.recyclerview_presentation_movies);
        this.progressProcess = (ProgressBar) findViewById(R.id.mProgress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initResourceScreen();
        initResource();//strTopRated
        boolean isNetworkOk = PopularMoviesCertAcessNetwork.isNetworkAcessOK(this.getApplicationContext());
        //Carga Inicial

        if (isNetworkOk) {
            URL urlPopularMovies = this.networkConfig.getURLPopularMovies();

            new PopularMoviesNetworkTask(MainActivity.this,
                    this.parserData,
                    this.networkRun).execute(urlPopularMovies);

            setTitle(R.string.title_activity_main_default);
        } else {
            String msgErro = this.getString(R.string.app_erro_network_acess);
            new PopularMoviesMsg().showMessageErro(msgErro, MainActivity.this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popular_movies_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemSelected = item.getItemId();
        boolean isNetworkOk = PopularMoviesCertAcessNetwork.isNetworkAcessOK(this.getApplicationContext());

        if (isNetworkOk) {
            if (itemSelected == R.id.menu_item_popularMovies) {
                URL urlPopularMovies = this.networkConfig.getURLPopularMovies();
                new PopularMoviesNetworkTask(MainActivity.this,
                        this.parserData,
                        this.networkRun).execute(urlPopularMovies);
                this.setTitle(R.string.title_activity_main_default);
                return true;
            } else if (itemSelected == R.id.menu_item_topRated) {
                URL urlTopRated = this.networkConfig.getURLTopRatedMovies();
                new PopularMoviesNetworkTask(MainActivity.this,
                        this.parserData,
                        this.networkRun).execute(urlTopRated);
                this.setTitle(R.string.title_activity_main_top_rated);
                return true;
            }

        } else {
            String msgErro = this.getString(R.string.app_erro_network_acess);
            new PopularMoviesMsg().showMessageErro(msgErro, MainActivity.this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostFinished(Object popularMovies) {
        this.progressProcess.setVisibility(View.INVISIBLE);

        if (popularMovies == null) {
            String msgErroJsonParser = this.getString(R.string.app_json_not_exists);
            new PopularMoviesMsg().showMessageErro(msgErroJsonParser, MainActivity.this);
        } else {
            this.recycleViewPosterPresentation.setVisibility(View.VISIBLE);
            PopularMovies popMovies = (PopularMovies)popularMovies;
            List<Movie> refListMovies = popMovies.getListMovie();
            PopularMoviesPosterAdapter popularMoviesPosterAdapter = new PopularMoviesPosterAdapter(refListMovies);
            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
            this.recycleViewPosterPresentation.setLayoutManager(layoutManager);
            this.recycleViewPosterPresentation.setAdapter(popularMoviesPosterAdapter);
        }
    }

    @Override
    public void onSearchImages(Object pPopularMovies) {
        PopularMovies popMovies = (PopularMovies)pPopularMovies;
        List<Movie> listMovie = popMovies.getListMovie();
        Iterator<Movie> iterMovie = listMovie.iterator();

        while (iterMovie.hasNext()) {
            Movie refMovieLine = iterMovie.next();
            String strPosterPath = refMovieLine.getPosterPath();
            RequestCreator requestCreatorRefLine0 = Picasso.with(MainActivity.this).load(strPosterPath);
            refMovieLine.setRefRequesImg(requestCreatorRefLine0);
        }
    }

    @Override
    public void onInitProgressBar() {
        this.progressProcess.setVisibility(View.VISIBLE);
    }
}
