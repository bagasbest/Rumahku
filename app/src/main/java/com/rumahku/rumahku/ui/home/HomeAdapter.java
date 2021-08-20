package com.rumahku.rumahku.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rumahku.rumahku.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final ArrayList<HomeModel> homeList = new ArrayList<>();
    public void setData(ArrayList<HomeModel> items) {
        homeList.clear();
        homeList.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @NotNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeAdapter.ViewHolder holder, int position) {
        holder.bind(homeList.get(position));
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title, location;
        CardView cardView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            location = itemView.findViewById(R.id.location);
            cardView = itemView.findViewById(R.id.cv);
        }

        public void bind(HomeModel homeModel) {
            Glide
                    .with(itemView.getContext())
                    .load(R.drawable.logo)
                    .into(icon);

            title.setText(homeModel.title);
            location.setText(homeModel.location);

            cardView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), HomeDetail.class);
                intent.putExtra(HomeDetail.EXTRA_HOME, homeModel);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
