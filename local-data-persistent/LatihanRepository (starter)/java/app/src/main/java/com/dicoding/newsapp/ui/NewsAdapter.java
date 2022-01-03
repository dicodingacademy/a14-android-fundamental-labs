package com.dicoding.newsapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.newsapp.R;
import com.dicoding.newsapp.data.local.entity.NewsEntity;
import com.dicoding.newsapp.databinding.ItemNewsBinding;
import com.dicoding.newsapp.utils.DateFormatter;

public class NewsAdapter extends ListAdapter<NewsEntity, NewsAdapter.MyViewHolder> {

    private final OnItemClickCallback onItemClickCallback;

    public NewsAdapter(OnItemClickCallback onItemClickCallback) {
        super(DIFF_CALLBACK);
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewsEntity news = getItem(position);
        holder.bind(news);

        ImageView ivBookmark = holder.binding.ivBookmark;
        if (news.isBookmarked()) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.getContext(), R.drawable.ic_bookmarked_white));
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.getContext(), R.drawable.ic_bookmark_white));
        }

        ivBookmark.setOnClickListener(view -> {
            onItemClickCallback.onBookmarkClick(news);
        });
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        final ItemNewsBinding binding;

        MyViewHolder(ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(NewsEntity news) {
            binding.tvItemTitle.setText(news.getTitle());
            binding.tvItemPublishedDate.setText(DateFormatter.formatDate(news.getPublishedAt()));
            Glide.with(itemView.getContext())
                    .load(news.getUrlToImage())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getUrl()));
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public static final DiffUtil.ItemCallback<NewsEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NewsEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull NewsEntity oldUser, @NonNull NewsEntity newUser) {
                    return oldUser.getTitle().equals(newUser.getTitle());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull NewsEntity oldUser, @NonNull NewsEntity newUser) {
                    return oldUser.equals(newUser);
                }
            };
}

interface OnItemClickCallback {
    void onBookmarkClick(NewsEntity data);
}