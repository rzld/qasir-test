package com.rizaldi.qasirtest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void OnItemClick(MyData myData);
    }
    private Context context;
    private List<MyData> my_data;
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PRICE = "price";
    public static final String KEY_DESCRIPTION = "description";
    private OnItemClickListener listener;

    public CustomAdapter(Context context, List<MyData> my_data, OnItemClickListener listener) {
        this.context = context;
        this.my_data = my_data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        MyData myData = my_data.get(position);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        viewHolder.productName.setText(myData.getProduct_name());
        viewHolder.price.setText(formatRupiah.format((double)myData.getPrice()));
        viewHolder.stock.setText(String.valueOf(myData.getStock()));
        //load thumbnail image
        Glide.with(context).load(my_data.get(position).getImg_thumb()).into(viewHolder.thumbnailView);

        viewHolder.bind(myData, listener);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnailView;
        public TextView productName;
        public TextView price;
        public TextView stock;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnailView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            price = (TextView) itemView.findViewById(R.id.price);
            stock = (TextView) itemView.findViewById(R.id.stock);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }

        public void bind(final MyData myData, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //MyData myData1 = my_data.get(position);

                    Intent intent = new Intent(v.getContext(), ProductActivity.class);
                    intent.putExtra(KEY_NAME, myData.getProduct_name());
                    intent.putExtra(KEY_IMAGE, myData.getImg_large());
                    intent.putExtra(KEY_PRICE, myData.getPrice());
                    intent.putExtra(KEY_DESCRIPTION, myData.getDescription());
                    v.getContext().startActivity(intent);
                    //listener.OnItemClick(myData);
                }
            });
        }
    }
}
