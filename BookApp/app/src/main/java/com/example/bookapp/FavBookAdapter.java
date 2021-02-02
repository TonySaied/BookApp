package com.example.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.Model.FavBook;
import com.example.bookapp.UI.FavItemActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavBookAdapter extends RecyclerView.Adapter<FavBookAdapter.ViewHolder>
{

    LayoutInflater inflater;
    Context context;
    ArrayList<FavBook> favBookList;
    private FavBookDB favBookDB;
    private RecyclerViewClickListener listener;

    public FavBookAdapter(Context context, ArrayList<FavBook> favBookList, RecyclerViewClickListener listener)
    {
        this.context = context;
        this.inflater=LayoutInflater.from(context);
        this.favBookList = favBookList;
        this.listener=listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        favBookDB=new FavBookDB(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.book_title.setText(favBookList.get(position).getBookTitle());
        Picasso.get().load(favBookList.get(position).getBookPic()).into(holder.book_img);
    }

    @Override
    public int getItemCount() {
    return favBookList.size();
}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView book_title;
        ImageView book_img;
        Button fav_btn;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            book_title=itemView.findViewById(R.id.bookTitle);
            book_img=itemView.findViewById(R.id.bookImage);
            fav_btn=itemView.findViewById(R.id.bookFavBtn);
            fav_btn.setBackgroundResource(R.drawable.red);

            itemView.setOnClickListener(this);

            fav_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Book removed from favorites", Toast.LENGTH_SHORT).show();
                    int position=getAdapterPosition();
                    FavBook favBook=favBookList.get(position);
                    favBookDB.removeFav(favBook.getBookID());

                    removeItem(position);
                }
            });

        }

        @Override
        public void onClick(View v)
        {
            listener.onClick(v,getAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener
    {
        void onClick(View v,int position);
    }

    private void removeItem(int position)
    {
        favBookList.remove(position);
        notifyItemChanged(position+favBookList.size());
        notifyItemRangeChanged(position,favBookList.size());
    }
}