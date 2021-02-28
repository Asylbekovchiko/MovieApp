package kg.asylbekov.mainactivity.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kg.asylbekov.mainactivity.R;
import kg.asylbekov.mainactivity.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>{
    private ArrayList<Review> reviewArrayList;
    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
    Review review = reviewArrayList.get(position);
    holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {

        /*
        return reviewArrayList.size();
*/
        return 0;
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView content;
        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
        author = itemView.findViewById(R.id.author);
        content = itemView.findViewById(R.id.contents);

        }
    }

    public void setReviewArrayList(ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
        notifyDataSetChanged();
    }
}
