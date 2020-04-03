package com.muhammadkhaled.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.QuizViewHolder> {

    private List<QuizListModel> quizListModel;
    private OnQuizItemClicked onQuizItemClicked;

    public QuizListAdapter(OnQuizItemClicked onQuizItemClicked) {
        this.onQuizItemClicked = onQuizItemClicked;
    }

    public void setQuizListModel(List<QuizListModel> quizListModel) {
        this.quizListModel = quizListModel;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_list_item, parent, false);

        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        QuizListModel quizList = quizListModel.get(position);

        holder.listTitle.setText(quizList.getName());

        Glide.with(holder.itemView.getContext())
                .load(quizList.getImage())
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(holder.listImage);
        String listDescription = quizList.getDesc();
        if (listDescription.length() > 150) {
            listDescription = listDescription.substring(0, 150);
        }

        holder.listDesc.setText(listDescription + "...");
        holder.listLevel.setText(quizList.getLevel());


    }

    @Override
    public int getItemCount() {
        if (quizListModel == null) {
            return 0;
        } else {
            return quizListModel.size();
        }
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView listImage;
        private TextView listTitle;
        private TextView listDesc;
        private TextView listLevel;
        private Button listBtn;

        QuizViewHolder(@NonNull View itemView) {
            super(itemView);

            listImage = itemView.findViewById(R.id.list_image);
            listTitle = itemView.findViewById(R.id.list_title);
            listDesc = itemView.findViewById(R.id.list_desc);
            listLevel = itemView.findViewById(R.id.list_difficulty);
            listBtn = itemView.findViewById(R.id.list_btn);

            listBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onQuizItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnQuizItemClicked {
        void onItemClicked(int position);
    }
}
