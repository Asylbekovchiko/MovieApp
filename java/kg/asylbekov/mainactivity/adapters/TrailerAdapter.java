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
import kg.asylbekov.mainactivity.Trailer;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder>{
    private ArrayList<Trailer> trailerArrayList;
    private onClickVideo clickVideo;





    public interface onClickVideo{
        void playVideo(String url);
    }


    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_layout, parent, false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        Trailer trailer = trailerArrayList.get(position);
        holder.name.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailerArrayList.size();
    }

    class TrailerHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public TrailerHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameVideo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickVideo != null){
                        clickVideo.playVideo(trailerArrayList.get(getAdapterPosition()).getKey());
                    }
                }
            });


        }
    }

    public void setTrailerArrayList(ArrayList<Trailer> trailerArrayList) {
        this.trailerArrayList = trailerArrayList;
        notifyDataSetChanged();
    }

    public void setClickVideo(onClickVideo clickVideo) {
        this.clickVideo = clickVideo;
    }
}
