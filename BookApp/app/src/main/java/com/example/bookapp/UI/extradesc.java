package com.example.bookapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bookapp.R;
import com.squareup.picasso.Picasso;

public class extradesc extends AppCompatActivity {

    TextView extraDesc_title;
    ImageView extraDesc_pic;
    TextView extraDesc_Publisher;
    TextView extraDesc_year;
    TextView extraDesc_rate;
    TextView extraDesc_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extradesc);

        extraDesc_title=findViewById(R.id.extra_title);
        extraDesc_Publisher=findViewById(R.id.extra_publisher);
        extraDesc_year=findViewById(R.id.extra_year);
        extraDesc_rate=findViewById(R.id.extra_rate);
        extraDesc_description=findViewById(R.id.extra_description);
        extraDesc_pic=findViewById(R.id.extra_pic);

        String bundle_title="Title not sent";
        String bundle_publisher="No pub";
        String bundle_year="NaN";
        String bundle_rate="No rate";
        String bundle_description="No description";


        String bundle_pic="";
        Bundle extras=getIntent().getExtras();
        if(extras !=null)
        {
            bundle_title=extras.getString("title");
            bundle_publisher=extras.getString("pub");
            bundle_year=extras.getString("year");
            bundle_rate=extras.getString("rate");
            bundle_description=extras.getString("description");
            bundle_pic=extras.getString("pic");
        }



        Picasso.get().load(bundle_pic).into(extraDesc_pic);
        extraDesc_title.setText(bundle_title);
        extraDesc_Publisher.setText("Publisher: "+bundle_publisher);
        extraDesc_year.setText("Year: "+bundle_year);
        extraDesc_rate.setText("Rate: "+bundle_rate);
        extraDesc_description.setText("Description: "+bundle_description);
    }
}