package com.example.fitgain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutAdapter extends  RecyclerView.Adapter<WorkoutAdapter.MyViewHolder> {

    String []title, description;
    int []images;
    long []videos;
    int []calories;
    Context context;

    public WorkoutAdapter(Context ct, String[] title, String[] description, int[] img, long[]video, int[]calories){
        context =ct;
        this.title = title;
        this.description = description;
        images = img;
        videos = video;
        this.calories = calories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.workout_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.mText1.setText(title[position]);
        holder.mText2.setText(description[position]);
        holder.mImage.setImageResource(images[position]);
        //holder.mvideos.setVideoURI(videos[position]);

        holder.recyclerViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int clickedPosition = holder.getAdapterPosition();
                Intent intent = new Intent(context, Actualworkout.class);
                intent.putExtra("title",title[clickedPosition]);
                intent.putExtra("Des",description[clickedPosition]);
               //intent.putExtra("img",images[clickedPosition]);
                intent.putExtra("videos",String.valueOf(videos[clickedPosition]));
                intent.putExtra("calories",(calories[clickedPosition]));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return title.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mText1, mText2;
        ImageView mImage;
        ConstraintLayout recyclerViewLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mText1 = itemView.findViewById(R.id.workout_VideoTitle);
            mText2 = itemView.findViewById(R.id.workout_VideoDescrib);
            mImage = itemView.findViewById(R.id.workout_VideoImage);
            recyclerViewLayout = itemView.findViewById(R.id.recyclerViewLayout);
        }
    }


}
