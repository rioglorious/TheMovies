package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Credits;

import java.util.List;

public class CreditsCastAdapter extends RecyclerView.Adapter<CreditsCastAdapter.CardViewViewHolder>
{
    private Context context;
    private List<Credits.Cast> CreditsList;
    private List<Credits.Cast> getCreditsList()
    {
        return CreditsList;
    }
    public void setCreditsList(List<Credits.Cast> CreditsList) {
        this.CreditsList = CreditsList;
    }
    public CreditsCastAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_credits_cast, parent, false);
        return new CreditsCastAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        final Credits.Cast results = getCreditsList().get(position);
        holder.originalname_cast.setText(results.getOriginal_name());
        holder.character_cast.setText(results.getCharacter());
        Glide.with(context)
                .load(Const.IMG_URL + results.getProfile_path()).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.img_profile);
    }

    @Override
    public int getItemCount() {
        return getCreditsList().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_profile;
        TextView originalname_cast, character_cast;
        CardView cv_cast;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_profile = itemView.findViewById(R.id.cast_profile);
            originalname_cast = itemView.findViewById(R.id.originalname_cast);
            character_cast = itemView.findViewById(R.id.character_cast);
            cv_cast = itemView.findViewById(R.id.cv_cast);

        }
    }
}
