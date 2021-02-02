package com.example.bookapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookapp.BookAdapter;
import com.example.bookapp.Model.Book;
import com.example.bookapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class firstpage extends AppCompatActivity  {

    private static String TIME_JSON_URL = "https://run.mocky.io/v3/e36cc68e-5e78-4f11-950b-fc6570bc5d03";
    private static String SPORT_JSON_URL = "https://run.mocky.io/v3/f393cb45-4081-4e33-a45e-c2966a90f2f8";
    private static String LIFE_JSON_URL = "https://run.mocky.io/v3/b5adf54a-9e8a-47eb-a055-7ba3249aae9c";
    private static String LOVE_JSON_URL = "https://run.mocky.io/v3/a1f58cce-5dc9-44e7-a5a8-1daa0824250e";
    RecyclerView firstPageRecycler;

    ArrayList<Book> books;
    BookAdapter bookAdapter;
    ImageView bookImage;
    Button favbutton;
    EditText SearchBook;
    Spinner sortSpinner;


    private BookAdapter.RecyclerViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        firstPageRecycler=findViewById(R.id.firstPagerecycler);
        books=new ArrayList<>();
        firstPageRecycler.setHasFixedSize(true);
        bookImage=findViewById(R.id.bookImage);
        favbutton=findViewById(R.id.bookFavBtn);
        SearchBook=findViewById(R.id.search_firsPage);




        sortSpinner=findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.sort,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Sort by Rate"))
                {
                    Toast.makeText(parent.getContext(),"Sorted Ascendigly",Toast.LENGTH_SHORT).show();
                    Collections.sort(books, new Comparator<Book>() {
                        @Override
                        public int compare(Book o1, Book o2) {
                            return o1.getRate().compareTo(o2.getRate());
                        }
                    });
                    bookAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SearchBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                filter(s.toString());
            }


        });

        Intent intent=getIntent();
        if(intent.getStringExtra("EXTRA").equals("1"))
        {
            extract_time();
        }
        else if(intent.getStringExtra("EXTRA").equals("2"))
        {
            extract_sport();
        }
        else if(intent.getStringExtra("EXTRA").equals("3"))
        {
            extract_life();
        }
        else if(intent.getStringExtra("EXTRA").equals("4"))
        {
            extract_love();
        }

        listener=new BookAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick(View v, int position)
            {
                Intent i=new Intent(firstpage.this, extradesc.class);
                i.putExtra("title",books.get(position).getTitle());
                i.putExtra("pic",books.get(position).getPic());
                i.putExtra("pub",books.get(position).getPublisher());
                i.putExtra("year",books.get(position).getYear());
                i.putExtra("rate",books.get(position).getRate());
                i.putExtra("description",books.get(position).getDescription());
                startActivity(i);
            }
        };
    }
    //used for searching
    private void filter(String text)
    {
        ArrayList<Book> filteredList=new ArrayList<>();
        for(Book book:books)
        {
            if(book.getTitle().toLowerCase().startsWith(text.toLowerCase()))
            {
                filteredList.add(book);
            }
        }
        bookAdapter.filterList(filteredList);
    }



    private void extract_time()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, TIME_JSON_URL, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray jsonArray= response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject BookObj = jsonArray.getJSONObject(i);
                        final JSONObject volumeInfo= BookObj.getJSONObject("volumeInfo");
                        Book book = new Book();

                        book.setTitle(volumeInfo.getString("title"));

                        book.setPublisher(volumeInfo.getString("publisher"));

                        if(volumeInfo.has("publishedDate"))
                        {
                            book.setYear(volumeInfo.getString("publishedDate"));
                        }
                        else
                        {
                            book.setYear("No Year available");
                        }
                        if(volumeInfo.has("description"))
                        {
                            book.setDescription(volumeInfo.getString("description"));
                        }
                        else
                        {
                            book.setDescription("No Description Available");
                        }
                        if(volumeInfo.has("averageRating"))
                        {
                            book.setRate(volumeInfo.getString("averageRating"));
                        }
                        else
                        {
                            book.setRate("No Rate Available");
                        }


                        //
                        book.setPic(volumeInfo.getJSONObject("imageLinks").getString("thumbnail"));
                        book.setFavStatus("0");
                        book.setId(String.valueOf(i));

                        books.add(book);
                        Log.e("onResponse:",String.valueOf(books.size()));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
                firstPageRecycler.setLayoutManager(gridLayoutManager);

                bookAdapter = new BookAdapter(getApplicationContext(), books,listener);
                firstPageRecycler.setAdapter(bookAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                 Log.d("tag","onErrorResponse: "+error.getMessage());
            }
        });


        queue.add(jsonArrayRequest);
    }


    private void extract_sport()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, SPORT_JSON_URL, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray jsonArray= response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject BookObj = jsonArray.getJSONObject(i);
                        JSONObject volumeInfo= BookObj.getJSONObject("volumeInfo");
                        Book book = new Book();

                        book.setTitle(volumeInfo.getString("title"));
                        book.setPublisher(volumeInfo.getString("publisher"));
                        if(volumeInfo.has("publishedDate"))
                        {
                            book.setYear(volumeInfo.getString("publishedDate"));
                        }
                        else
                        {
                            book.setYear("No Year available");
                        }
                        if(volumeInfo.has("description"))
                        {
                            book.setDescription(volumeInfo.getString("description"));
                        }
                        else
                        {
                            book.setDescription("No Description Available");
                        }
                        if(volumeInfo.has("averageRating"))
                        {
                            book.setRate(volumeInfo.getString("averageRating"));
                        }
                        else
                        {
                            book.setRate("No Rate Available");
                        }

                        book.setPic(volumeInfo.getJSONObject("imageLinks").getString("thumbnail"));

                        book.setFavStatus("0");
                        book.setId(String.valueOf(i));

                        books.add(book);
                        Log.e("onResponse:",String.valueOf(books.size()));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
                firstPageRecycler.setLayoutManager(gridLayoutManager);

                bookAdapter = new BookAdapter(getApplicationContext(), books,listener);
                firstPageRecycler.setAdapter(bookAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("tag","onErrorResponse: "+error.getMessage());
            }
        });


        queue.add(jsonArrayRequest);
    }
    private void extract_life()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, LIFE_JSON_URL, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray jsonArray= response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject BookObj = jsonArray.getJSONObject(i);
                        JSONObject volumeInfo= BookObj.getJSONObject("volumeInfo");
                        Book book = new Book();

                        book.setTitle(volumeInfo.getString("title"));
                        book.setPublisher(volumeInfo.getString("publisher"));

                        if(volumeInfo.has("publishedDate"))
                        {
                            book.setYear(volumeInfo.getString("publishedDate"));
                        }
                        else
                        {
                            book.setYear("No Year available");
                        }
                        if(volumeInfo.has("description"))
                        {
                            book.setDescription(volumeInfo.getString("description"));
                        }
                        else
                        {
                            book.setDescription("No Description Available");
                        }
                        if(volumeInfo.has("averageRating"))
                        {
                            book.setRate(volumeInfo.getString("averageRating"));
                        }
                        else
                        {
                            book.setRate("No Rate Available");
                        }

                        book.setPic(volumeInfo.getJSONObject("imageLinks").getString("thumbnail"));

                        book.setFavStatus("0");
                        book.setId(String.valueOf(i));

                        books.add(book);
                        Log.e("onResponse:",String.valueOf(books.size()));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
                firstPageRecycler.setLayoutManager(gridLayoutManager);

                bookAdapter = new BookAdapter(getApplicationContext(), books,listener);
                firstPageRecycler.setAdapter(bookAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("tag","onErrorResponse: "+error.getMessage());
            }
        });


        queue.add(jsonArrayRequest);
    }
    private void extract_love()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, LOVE_JSON_URL, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray jsonArray= response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject BookObj = jsonArray.getJSONObject(i);
                        JSONObject volumeInfo= BookObj.getJSONObject("volumeInfo");
                        Book book = new Book();

                        book.setTitle(volumeInfo.getString("title"));
                        book.setPublisher(volumeInfo.getString("publisher"));
                        if(volumeInfo.has("publishedDate"))
                        {
                            book.setYear(volumeInfo.getString("publishedDate"));
                        }
                        else
                        {
                            book.setYear("No Year available");
                        }
                        if(volumeInfo.has("description"))
                        {
                            book.setDescription(volumeInfo.getString("description"));
                        }
                        else
                        {
                            book.setDescription("No Description Available");
                        }
                        if(volumeInfo.has("averageRating"))
                        {
                            book.setRate(volumeInfo.getString("averageRating"));
                        }
                        else
                        {
                            book.setRate("No Rate Available");
                        }

                        book.setPic(volumeInfo.getJSONObject("imageLinks").getString("thumbnail"));

                        book.setFavStatus("0");
                        book.setId(String.valueOf(i));

                        books.add(book);
                        Log.e("onResponse:",String.valueOf(books.size()));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
                firstPageRecycler.setLayoutManager(gridLayoutManager);

                bookAdapter = new BookAdapter(getApplicationContext(), books ,listener);
                firstPageRecycler.setAdapter(bookAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("tag","onErrorResponse: "+error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }


}