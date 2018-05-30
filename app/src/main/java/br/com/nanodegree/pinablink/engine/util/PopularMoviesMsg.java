package br.com.nanodegree.pinablink.engine.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import br.com.nanodegree.pinablink.R;

/**
 * Created by Pinablink on 18/04/2018.
 */
public class PopularMoviesMsg {

    private String msg;

    public PopularMoviesMsg() {
        super();
    }

    public void showMessageErro(String msg, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        String title = context.getString(R.string.title_msg_erro);
        alert.setMessage(msg).setTitle(title);
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void showToastMsgInfo (String msg, Context context) {
        final int DURATION = Toast.LENGTH_SHORT;
        Toast.makeText(context, msg, DURATION).show();
    }


}
