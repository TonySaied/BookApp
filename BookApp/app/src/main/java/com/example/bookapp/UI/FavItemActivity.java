package com.example.bookapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.bookapp.BookAdapter;
import com.example.bookapp.Model.FavBook;
import com.example.bookapp.FavBookAdapter;
import com.example.bookapp.FavBookDB;
import com.example.bookapp.R;

import java.util.ArrayList;
import java.util.List;

public class FavItemActivity extends AppCompatActivity {

    RecyclerView favItemRecyclerView;
    private FavBookDB favBookDB;
    private ArrayList<FavBook> favBookList=new ArrayList<>();
    FavBookAdapter favBookAdapter;

    private FavBookAdapter.RecyclerViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_item);

        favItemRecyclerView=findViewById(R.id.favItemTRecycler);
        favBookDB=new FavBookDB(this);
        favItemRecyclerView.setHasFixedSize(true);

        favItemRecyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));



        listener= new FavBookAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick(View v, int position)
            {
                Intent i=new Intent(FavItemActivity.this, extradesc.class);
                i.putExtra("title",favBookList.get(position).getBookTitle());
                i.putExtra("pic",favBookList.get(position).getBookPic());
                i.putExtra("pub",favBookList.get(position).getBookPublisher());
                i.putExtra("year",favBookList.get(position).getBookYear());
                i.putExtra("rate",favBookList.get(position).getBookRate());
                i.putExtra("description",favBookList.get(position).getBookDescription());
                startActivity(i);
            }
        };

        LoadData();
    }

    private void LoadData()
    {
        if(favBookList !=null)
        {
            favBookList.clear();
        }
        SQLiteDatabase db=favBookDB.getReadableDatabase();
        Cursor cur=favBookDB.select_all_fav();
        try
        {
            while (cur.moveToNext()) {
                String title = cur.getString(cur.getColumnIndex(favBookDB.BOOK_TITLE));
                String id = cur.getString(cur.getColumnIndex(favBookDB.KEY_ID));
                String year = cur.getString(cur.getColumnIndex(favBookDB.BOOK_YEAR));
                String rate = cur.getString(cur.getColumnIndex(favBookDB.BOOK_RATE));
                String publisher = cur.getString(cur.getColumnIndex(favBookDB.BOOK_PUBLISHER));
                String description = cur.getString(cur.getColumnIndex(favBookDB.BOOK_DESCRIPTION));
                String pic=cur.getString(cur.getColumnIndex(favBookDB.BOOK_PIC));

                FavBook favItem = new FavBook(title, id, year,rate,publisher,description,pic);
                favBookList.add(favItem);
            }
        }
        finally
        {
            if(cur !=null &&cur.isClosed())
                cur.close();
            db.close();
        }
        favBookAdapter =new FavBookAdapter(this,favBookList,  listener);

        favItemRecyclerView.setAdapter(favBookAdapter);
    }
}