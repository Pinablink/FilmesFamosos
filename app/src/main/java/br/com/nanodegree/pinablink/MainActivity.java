package br.com.nanodegree.pinablink;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.net.URL;

import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkRun;
import br.com.nanodegree.pinablink.engine.parser.PopularMoviesParserData;
import br.com.nanodegree.pinablink.engine.network.PopularMoviesNetworkConfig;
import br.com.nanodegree.pinablink.engine.network.task.PopularMoviesNetworkTask;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesCertAcessNetwork;
import br.com.nanodegree.pinablink.engine.util.PopularMoviesMsg;


public class MainActivity extends AppCompatActivity {

    /**
     *
     */
    private PopularMoviesParserData parserData;

    /**
     *
     */
    private PopularMoviesNetworkConfig networkConfig;

    /**
     *
     */
    private PopularMoviesNetworkRun networkRun;

    /**
     *
     */
    private RecyclerView recycleViewPosterPresentation;

    /**
     *
     */
    private ProgressBar progressProcess;

    /**
     * Inicializa todos os recursos que a Activity vai necessitar para realização de suas tarefas
     */
    private void initResource() {
        this.parserData = new PopularMoviesParserData(MainActivity.this);
        this.networkConfig = new PopularMoviesNetworkConfig(MainActivity.this);
        this.networkRun = new PopularMoviesNetworkRun();
    }

    /**
     *
     */
    private void initResourceScreen() {
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
                    this.progressProcess,
                    this.parserData,
                    this.networkRun,
                    this.recycleViewPosterPresentation).execute(urlPopularMovies);

            setTitle(R.string.title_activity_main_default);
        } else {
            String msgErro = this.getString(R.string.app_erro_network_acess);
            new PopularMoviesMsg().showMessageErro(msgErro, MainActivity.this);
        }

    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popular_movies_app, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemSelected = item.getItemId();
        boolean isNetworkOk = PopularMoviesCertAcessNetwork.isNetworkAcessOK(this.getApplicationContext());

        if (isNetworkOk) {
            if (itemSelected == R.id.menu_item_popularMovies) {
                URL urlPopularMovies = this.networkConfig.getURLPopularMovies();
                new PopularMoviesNetworkTask(MainActivity.this,
                        this.progressProcess,
                        this.parserData,
                        this.networkRun,
                        this.recycleViewPosterPresentation).execute(urlPopularMovies);
                this.setTitle(R.string.title_activity_main_default);
                return true;
            } else if (itemSelected == R.id.menu_item_topRated) {
                URL urlTopRated = this.networkConfig.getURLTopRatedMovies();
                new PopularMoviesNetworkTask(MainActivity.this,
                        this.progressProcess,
                        this.parserData,
                        this.networkRun,
                        this.recycleViewPosterPresentation).execute(urlTopRated);
                this.setTitle(R.string.title_activity_main_top_rated);
                return true;
            }

        } else {
            String msgErro = this.getString(R.string.app_erro_network_acess);
            new PopularMoviesMsg().showMessageErro(msgErro, MainActivity.this);
        }

        return super.onOptionsItemSelected(item);
    }
}
