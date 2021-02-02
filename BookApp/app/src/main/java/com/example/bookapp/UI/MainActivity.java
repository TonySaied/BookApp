package com.example.bookapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookapp.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    /*@BindView(R.id.Choose_textView)
    TextView Choose_textView;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.ChoiceBtn)
    Button ChoiceBtn;*/

    RadioGroup radioGroup;
    RadioButton radioBtn1;
    RadioButton radioBtn2;
    RadioButton radioBtn3;
    RadioButton radioBtn4;
    TextView textView;
        Button choiceBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);
        choiceBtn=findViewById(R.id.ChoiceBtn);

        radioBtn1=findViewById(R.id.radio_one);
        radioBtn2=findViewById(R.id.radio_two);
        radioBtn3=findViewById(R.id.radio_three);
        radioBtn4=findViewById(R.id.radio_four);

        choiceBtn.setOnClickListener(this);
        /*choiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(radioBtn1.isChecked())
                {
                    Intent i = new Intent(MainActivity.this, firstpage.class);
                    i.putExtra("EXTRA","1");
                    startActivity(i);
                }
                else if(radioBtn2.isChecked())
                {
                    Intent i = new Intent(MainActivity.this, firstpage.class);
                    i.putExtra("EXTRA","2");
                    startActivity(i);
                }
                else if(radioBtn3.isChecked())
                {
                    Intent i = new Intent(MainActivity.this, firstpage.class);
                    i.putExtra("EXTRA","3");
                    startActivity(i);
                }
                else if(radioBtn4.isChecked())
                {
                    Intent i = new Intent(MainActivity.this, firstpage.class);
                    i.putExtra("EXTRA","4");
                    startActivity(i);
                }
                else
                {
                    StyleableToast.makeText(getApplicationContext(),"Check Book Category First",R.style.mainToast).show();

                }
            }
        });*/
        /*radioGroup = findViewById(R.id.radioGroup);
        textView = findViewById(R.id.textView);
        choiceBtn=findViewById(R.id.ChoiceBtn);
        choiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,firstpage.class);
                startActivity(i);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==R.id.fav_mainMenu)
        {
            Intent i=new Intent(MainActivity.this, FavItemActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ChoiceBtn)
        {
            if(radioBtn1.isChecked())
            {
                Intent i = new Intent(MainActivity.this, firstpage.class);
                i.putExtra("EXTRA","1");
                startActivity(i);
            }
            else if(radioBtn2.isChecked())
            {
                Intent i = new Intent(MainActivity.this, firstpage.class);
                i.putExtra("EXTRA","2");
                startActivity(i);
            }
            else if(radioBtn3.isChecked())
            {
                Intent i = new Intent(MainActivity.this, firstpage.class);
                i.putExtra("EXTRA","3");
                startActivity(i);
            }
            else if(radioBtn4.isChecked())
            {
                Intent i = new Intent(MainActivity.this, firstpage.class);
                i.putExtra("EXTRA","4");
                startActivity(i);
            }
            else
            {
                StyleableToast.makeText(getApplicationContext(),"Check Book Category First",R.style.mainToast).show();

            }
        }
    }
}