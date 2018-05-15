package br.com.nanodegree.pinablink.engine.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.nanodegree.pinablink.R;

/**
 * Created by Pinablink on 14/05/2018.
 */
public class PopularMoviesReviewAdapterViewHolder extends RecyclerView.ViewHolder {

    private TextView titleReviewTextView;
    private TextView contentReviewTextView;

    public PopularMoviesReviewAdapterViewHolder(View itemView) {
        super(itemView);

        this.titleReviewTextView = itemView.findViewById(R.id.userPostReview);
        this.contentReviewTextView = itemView.findViewById(R.id.textReview);

    }

    public TextView getUserReview () {
        return this.titleReviewTextView;
    }

    public TextView getContentReview () {
        return this.contentReviewTextView;
    }


}
