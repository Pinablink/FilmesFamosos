package br.com.nanodegree.pinablink;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
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
import br.com.nanodegree.pinablink.engine.listener.PopularMoviesPosScrollOnClick;
import br.com.nanodegree.pinablink.engine.network.task.ActivityFilmesFamosos;
import br.com.nanodegree.pinablink.engine.network.task.AsyncTaskNetworkDelegator;
import br.com.nanodegree.pinablink.engine.network.task.PopularMoviesNetworkTask;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesCertAcessNetwork;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesMsg;

public class MainActivity extends ActivityFilmesFamosos
        implements AsyncTaskNetworkDelegator,
        LoaderCallbacks<PopularMovies>, PopularMoviesPosScrollOnClick {

    private RecyclerView recycleViewPosterPresentation;
    private ProgressBar progressProcess;
    private static int LOADER_POPULAR_MOVIES = 0;
    private static int LOADER_TOP_RATED_MOVIES;
    private URL urlPopularMovies;
    private URL urlTopRated;
    private String strTitle;
    private Parcelable parcelable;

    protected void loadUrl () {
        this.urlPopularMovies = this.networkConfig.getURLPopularMovies();
        this.urlTopRated = this.networkConfig.getURLTopRatedMovies();
    }

    protected void initResourceScreen() {
        this.recycleViewPosterPresentation = (RecyclerView) findViewById(R.id.recyclerview_presentation_movies);
        this.progressProcess = (ProgressBar) findViewById(R.id.mProgress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initResourceScreen();
        initResource();
        loadUrl();
        boolean isNetworkOk = PopularMoviesCertAcessNetwork.isNetworkAcessOK(this.getApplicationContext());
        LoaderCallbacks<PopularMovies> callBack = MainActivity.this;

        //Carga Inicial
        if (isNetworkOk) {
            Bundle bundle = new Bundle ();
            bundle.putInt(ID_BUNDLE_URL_PARAM, POPULAR_MOVIES);
            getSupportLoaderManager().initLoader(MainActivity.LOADER_POPULAR_MOVIES, bundle, callBack);
            this.strTitle = this.getString(R.string.title_activity_main_default);
            setTitle(this.strTitle);
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
            LoaderCallbacks<PopularMovies> callBack = MainActivity.this;

            if (itemSelected == R.id.menu_item_popularMovies) {
                Bundle bundle = new Bundle ();
                bundle.putInt(ID_BUNDLE_URL_PARAM, POPULAR_MOVIES);
                getSupportLoaderManager().restartLoader(MainActivity.LOADER_POPULAR_MOVIES, bundle, callBack);
                this.strTitle = this.getString(R.string.title_activity_main_default);
                this.setTitle(this.strTitle );
                return true;
            } else if (itemSelected == R.id.menu_item_topRated) {
                Bundle bundle = new Bundle ();
                bundle.putInt(ID_BUNDLE_URL_PARAM, TOP_RATED_MOVIES);
                getSupportLoaderManager().restartLoader(MainActivity.LOADER_TOP_RATED_MOVIES, bundle, callBack);
                this.strTitle = this.getString(R.string.title_activity_main_top_rated);
                this.setTitle(this.strTitle);
                return true;
            } else if (itemSelected == R.id.menu_item_myFavorites) {
                Intent intentFavorite = new Intent(MainActivity.this, FavoriteActivity.class);
                this.startActivity(intentFavorite);
            }

        } else {
            String msgErro = this.getString(R.string.app_erro_network_acess);
            new PopularMoviesMsg().showMessageErro(msgErro, MainActivity.this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<PopularMovies> onCreateLoader(int id, Bundle args) {
        PopularMoviesNetworkTask popularMoviesTask = null;
        boolean existsKey = args.containsKey(ID_BUNDLE_URL_PARAM);

        if (existsKey) {
            int codSearch = args.getInt(ID_BUNDLE_URL_PARAM);
            URL urlRefer = null;

            switch (codSearch) {

                case POPULAR_MOVIES:
                    urlRefer = this.urlPopularMovies;
                    break;

                case TOP_RATED_MOVIES:
                    urlRefer = this.urlTopRated;
                    break;
            }

            popularMoviesTask =
                    new PopularMoviesNetworkTask(MainActivity.this, this,
                            urlRefer, this.parserData, this.networkRun);

        }

        return popularMoviesTask;
    }

    @Override
    public void onLoadFinished(Loader<PopularMovies> loader, PopularMovies data) {
        if (data == null) {
            this.progressProcess.setVisibility(View.INVISIBLE);
            String msgErroJsonParser = this.getString(R.string.app_json_not_exists);
            new PopularMoviesMsg().showMessageErro(msgErroJsonParser, MainActivity.this);
        } else {
            PopularMovies popMovies = (PopularMovies)data;
            List<Movie> refListMovies = popMovies.getListMovie();
            PopularMoviesPosterAdapter popularMoviesPosterAdapter = new PopularMoviesPosterAdapter(refListMovies, this);
            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
            this.recycleViewPosterPresentation.setLayoutManager(layoutManager);
            this.recycleViewPosterPresentation.setAdapter(popularMoviesPosterAdapter);
            this.recycleViewPosterPresentation.setVisibility(View.VISIBLE);
            this.progressProcess.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<PopularMovies> loader) {
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLE_ACTIVITY , this.strTitle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        boolean existsBundleRestore =
                savedInstanceState.containsKey(KEY_TITLE_ACTIVITY);

        if (existsBundleRestore) {
            this.strTitle = savedInstanceState.getString(KEY_TITLE_ACTIVITY);
            this.setTitle(this.strTitle);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPosScroll() {

    }
}

