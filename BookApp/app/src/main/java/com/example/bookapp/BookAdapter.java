package com.example.bookapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bookapp.Model.Book;
import com.example.bookapp.UI.BookView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>
{
    ArrayList<Book> books;
    LayoutInflater inflater;
    Context context;

    public FavBookDB favBookDB;
    private RecyclerViewClickListener listener;

    public BookAdapter(Context context,ArrayList<Book> books, RecyclerViewClickListener listener)
    {
        this.context=context;
        this.inflater=LayoutInflater.from(context);
        this.books = books;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        favBookDB=new FavBookDB(context);

        SharedPreferences prefs=context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        boolean firstStart=prefs.getBoolean("firstStart",true);
        if(firstStart)
        {
            createTableOnFirstStart();
        }

        View view=inflater.inflate(R.layout.book_list,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.BookTitle.setText(books.get(position).getTitle());
        Picasso.get().load(books.get(position).getPic()).into(holder.BookImage);
        //Glide.with(context).load(books.get(position).getPic()).into(holder.BookImage);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void filterList(ArrayList<Book> filteredList)
    {
        books=filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView BookImage;
        TextView BookTitle;
        Button favBookBtn;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            BookImage=itemView.findViewById(R.id.bookImage);
            BookTitle=itemView.findViewById(R.id.bookTitle);
            favBookBtn=itemView.findViewById(R.id.bookFavBtn);



            itemView.setOnClickListener(this);
            favBookBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    int position=getAdapterPosition();
                    Book book=books.get(position);

                    if(book.getFavStatus().equals("0"))
                    {
                        StyleableToast.makeText(context, "Added to favorites", R.style.favToast).show();
                        book.setFavStatus("1");
                        favBookDB.insertIntoTheDB(book.getTitle(),book.getYear(),book.getRate(),book.getPublisher(),book.getDescription(),book.getId(),book.getFavStatus(),book.getPic());
                        favBookBtn.setBackgroundResource(R.drawable.red);
                    }
                    else
                    {
                        Toast.makeText(context,"Removed from Favorites",Toast.LENGTH_SHORT).show();
                        book.setFavStatus("0");
                        favBookDB.removeFav(book.getId());
                        favBookBtn.setBackgroundResource(R.drawable.shadow);
            }
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
    private void createTableOnFirstStart()
    {
        favBookDB.insertEmpty();
        SharedPreferences prefs =context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
    private void readCursorData(Book book,ViewHolder viewHolder)
    {
        Cursor cursor=favBookDB.read_all_data(book.getId());
        SQLiteDatabase db=favBookDB.getReadableDatabase();
        try
        {
            while ((cursor.moveToNext()))
            {
                String item_fav_status=cursor.getString(cursor.getColumnIndex(FavBookDB.FAVORITE_STATUS));
                book.setFavStatus(item_fav_status);

                if(item_fav_status !=null && item_fav_status.equals("1"))
                {
                    viewHolder.favBookBtn.setBackgroundResource(R.drawable.red);
                }
                else if(item_fav_status !=null && item_fav_status.equals("0"))
                {
                    viewHolder.favBookBtn.setBackgroundResource(R.drawable.shadow);
                }
            }
        }
        finally {
            if(cursor !=null &&cursor.isClosed())
                cursor.close();
            db.close();
        }
    }
}
