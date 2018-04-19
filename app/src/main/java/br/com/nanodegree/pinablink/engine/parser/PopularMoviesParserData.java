package br.com.nanodegree.pinablink.engine.parser;


import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.nanodegree.pinablink.R;
import br.com.nanodegree.pinablink.dataObject.Movie;
import br.com.nanodegree.pinablink.dataObject.PopularMovies;
import br.com.nanodegree.pinablink.engine.annotation.Param;
import br.com.nanodegree.pinablink.engine.annotation.ParamInJson;
import br.com.nanodegree.pinablink.exception.PMJSonErrorReader;

/**
 * Created by Pinablink on 13/04/2018.
 */
public class PopularMoviesParserData {


    private String contentJson;


    public PopularMoviesParserData() {
        super();
    }


    public PopularMovies process(String pContentJson) {

        PopularMovies popularMoviesObject;

        try{
            if (pContentJson != null && !pContentJson.isEmpty()) {
                this.contentJson = pContentJson;
                popularMoviesObject = this.run();
            } else {
                popularMoviesObject = null;
            }
        }  catch (PMJSonErrorReader e) {
            popularMoviesObject = null;
        }

        return popularMoviesObject;
    }

    private PopularMovies run()
            throws PMJSonErrorReader {

        PopularMovies popularReturn = null;
        boolean existsStringJson = (this.contentJson != null && !this.contentJson.isEmpty());


        if (existsStringJson) {

            try {

                JSONObject jsonObject = new JSONObject(this.contentJson);
                popularReturn = this.createObjPopularMovies(jsonObject);

            } catch (JSONException e) {
                throw new PMJSonErrorReader();
            }

        } else {
            throw new PMJSonErrorReader();
        }

        return popularReturn;
    }

    private void setValueTp(String pConcatLeft,
                            String pConcatRight,
                            String pName,
                            int pValueType,
                            Method pMethod,
                            Movie pMovie,
                            JSONObject pReferMovie)
            throws Exception {

        switch (pValueType) {
            case Param.VALUE_TYPE_STRING:
                String vlStr = (pConcatLeft + (pReferMovie.getString(pName)) + pConcatRight).trim();
                pMethod.invoke(pMovie, vlStr);
                break;

            case Param.VALUE_TYPE_INT:
                int vlInt = pReferMovie.getInt(pName);
                pMethod.invoke(pMovie, vlInt);
                break;

            case Param.VALUE_TYPE_BOOLEAN:
                boolean vlBool = pReferMovie.getBoolean(pName);
                pMethod.invoke(pMovie, vlBool);
                break;

            case Param.VALUE_TYPE_LONG:
                long vlLong = pReferMovie.getLong(pName);
                pMethod.invoke(pMovie, vlLong);
                break;
        }
    }

    private void setValueTp(String pConcatLeft,
                            String pConcatRight,
                            String pName,
                            int pValueType,
                            Method pMethod,
                            PopularMovies pPopularMovies,
                            JSONObject pReferMovie)
            throws Exception {

        switch (pValueType) {
            case Param.VALUE_TYPE_STRING:
                String vlStr = (pConcatLeft + (pReferMovie.getString(pName)) + pConcatRight).trim();
                pMethod.invoke(pPopularMovies, vlStr);
                break;

            case Param.VALUE_TYPE_INT:
                int vlInt = pReferMovie.getInt(pName);
                pMethod.invoke(pPopularMovies, vlInt);
                break;

            case Param.VALUE_TYPE_BOOLEAN:
                boolean vlBool = pReferMovie.getBoolean(pName);
                pMethod.invoke(pPopularMovies, vlBool);
                break;

            case Param.VALUE_TYPE_LONG:
                long vlLong = pReferMovie.getLong(pName);
                pMethod.invoke(pPopularMovies, vlLong);
                break;
        }
    }

    private Movie createMovie(JSONObject pReferMovie)
            throws Exception {
        Movie movieReturn = new Movie();
        Method[] methods = movieReturn.getClass().getMethods();
        int pValueParamType = Param.VALUE_TYPE_STRING; //Valor default de inicialização


        for (Method method : methods) {
            boolean paramAnnotationPresent = method.isAnnotationPresent(ParamInJson.class);

            if (paramAnnotationPresent) {
                ParamInJson paramInJsonRefer = method.getAnnotation(ParamInJson.class);
                String pStringName  = paramInJsonRefer.name();
                pValueParamType = paramInJsonRefer.valueType();
                String sLeft   =   paramInJsonRefer.concatLeft();
                String sRight  =   paramInJsonRefer.concatRight();
                this.setValueTp(sLeft, sRight, pStringName, pValueParamType, method, movieReturn, pReferMovie);
            }
        }

        return movieReturn;
    }

    private void inputMovies(String idParam,
                             PopularMovies pPopularMovies,
                             JSONObject pJsonObject,
                             List<Movie> pListMovie,
                             Method method) throws PMJSonErrorReader {

        try {

            JSONObject referMovie = null;
            JSONArray arrayObject = pJsonObject.getJSONArray(idParam);
            int lenArray = arrayObject.length();
            int index = 0;
            Movie movieReferCreate;

            for (; index < lenArray; index++) {
                referMovie = arrayObject.getJSONObject(index);
                movieReferCreate = this.createMovie(referMovie);
                pListMovie.add(movieReferCreate);
            }

        } catch (Exception ex) {
            throw new PMJSonErrorReader();
        }
    }

    private PopularMovies createObjPopularMovies(JSONObject pJsonObject)
            throws JSONException, PMJSonErrorReader {
        PopularMovies popularMovies = new PopularMovies();
        Method[] methods = popularMovies.getClass().getMethods();

        this.inputData(methods, popularMovies, pJsonObject);

        return popularMovies;
    }

    private void inputData(Method[] methods,
                           PopularMovies refer,
                           JSONObject pJsonObject)
            throws PMJSonErrorReader {

        boolean isArrayData = false;
        int valueType = Param.VALUE_TYPE_STRING; // Valor padrão de inicialização
        final List<Movie> listMovie = new ArrayList<Movie>();

        for (Method method : methods) {
            String name = method.getName();
            boolean annotationParamPresent = method.isAnnotationPresent(ParamInJson.class);

            if (annotationParamPresent) {
                ParamInJson paramInJson = method.getAnnotation(ParamInJson.class);
                String paramSearchInJson = paramInJson.name();
                valueType = paramInJson.valueType();
                isArrayData = paramInJson.isArrayData();
                String strLeft  = paramInJson.concatLeft();
                String strRight = paramInJson.concatRight();

                try {

                    if (isArrayData) {
                        this.inputMovies(paramSearchInJson, refer, pJsonObject, listMovie, method);
                    } else {
                        this.setValueTp(strLeft, strRight, paramSearchInJson, valueType, method, refer, pJsonObject);
                    }

                } catch (Exception e) {
                    throw new PMJSonErrorReader();
                }
            }
        }

        // Adicionando os filmes processados  a partir do JSON da API
        refer.setListMovie(listMovie);
        refer.getTotalPages();

    }

}
