package br.com.nanodegree.pinablink.engine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import br.com.nanodegree.pinablink.R;
import br.com.nanodegree.pinablink.dataObject.Review;
import br.com.nanodegree.pinablink.engine.holder.PopularMoviesReviewAdapterViewHolder;

/**
 * Created by Pinablink on 14/05/2018.
 */
public class PopularMoviesReviewAdapter
        extends RecyclerView.Adapter<PopularMoviesReviewAdapterViewHolder> {

    private List<Review> refLisReview;
    private Context refContext;

    public PopularMoviesReviewAdapter (List<Review> pListReview) {
        super();
        this.refLisReview = pListReview;
    }

    @Override
    public PopularMoviesReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.refContext =  parent.getContext();
        int idLayoutHolder = R.layout.review_user;
        LayoutInflater layoutInflater = LayoutInflater.from(this.refContext);
        View viewReturn = layoutInflater.inflate(idLayoutHolder, parent, false);
        return new PopularMoviesReviewAdapterViewHolder(viewReturn);
    }

    @Override
    public void onBindViewHolder(PopularMoviesReviewAdapterViewHolder holder, int position) {
        TextView tAuthor = holder.getUserReview();
        TextView tContent = holder.getContentReview();

        Review reviewObj = this.refLisReview.get(position);
        String sAuthor = reviewObj.getAuthor();
        String sContent = reviewObj.getContent();

        tAuthor.setText(sAuthor);
        tContent.setText(sContent);
    }

    @Override
    public int getItemCount() {
        return (this.refLisReview != null ? this.refLisReview.size() : 0);
    }
}
