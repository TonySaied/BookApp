package com.example.bookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bookapp.Model.Book;

import java.util.ArrayList;

public class FavBookDB extends SQLiteOpenHelper
{
    private static int DB_VERSION=1;
    private static String DB_NAME="Post.db";
    private static String TABLE_NAME="FavBookTable";

    public static String KEY_ID="id";
    public static String BOOK_TITLE="bookTitle";

    //
    public static String BOOK_YEAR="bookYear";
    public static String BOOK_RATE="bookRate";
    public static String BOOK_PUBLISHER="bookPublisher";
    public static String BOOK_DESCRIPTION="bookDescription";
    public static String FAVORITE_STATUS = "fStatus";
    public static String BOOK_PIC="bookPic";
    private static String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+KEY_ID+" TEXT,"+BOOK_TITLE+" TEXT,"+BOOK_YEAR+" TEXT,"
            +BOOK_RATE+" TEXT,"+BOOK_PUBLISHER+" TEXT,"+BOOK_DESCRIPTION+" TEXT,"+ FAVORITE_STATUS+" TEXT,"+BOOK_PIC+" TEXT)";


    public FavBookDB(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(" DROP TABLE  IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public  boolean insertData (String key_id,String book_title,String book_year ,String book_rate,String book_publisher,String book_description,String book_pic)
    {
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID , key_id);
        contentValues.put(BOOK_TITLE,book_title);
        contentValues.put(BOOK_YEAR ,book_year);
        contentValues.put(BOOK_RATE,book_rate);
        contentValues.put(BOOK_PUBLISHER,book_publisher);
        contentValues.put(BOOK_DESCRIPTION,book_description);
        contentValues.put(BOOK_PIC,book_pic);
        int r = (int) db.insert(TABLE_NAME ,null , contentValues);
        if(r== -1 )
        {
            return  false ;
        }
        else
            return  true ;

    }
    public ArrayList<Book> getData()
    {
        ArrayList <Book> arrayList= new ArrayList();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT "+BOOK_TITLE+" , "+BOOK_YEAR+" , "+BOOK_RATE+" , "+BOOK_PUBLISHER+" , "+BOOK_DESCRIPTION+" , "+BOOK_PIC+" from "+TABLE_NAME,null);
        while (res.moveToNext())
        {
            String book_title = res.getString(0);
            String book_year = res.getString(1);
            String book_rate=res.getString(2);
            String book_publisher=res.getString(3);
            String book_description=res.getString(4);
            String book_pic=res.getString(5);
            arrayList.add( new Book(book_title,book_year,book_rate,book_publisher,book_description,book_pic));
        }

        return arrayList;

    }
    public Integer Delete (String id )
    {
        SQLiteDatabase db =this.getWritableDatabase() ;
        return db.delete(TABLE_NAME , "id = ?" ,new String[]{id}) ;
    }

    //creates new table
    public void insertEmpty()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        for (int i=0;i<55;i++)
        {
            cv.put(KEY_ID,i);
            cv.put(FAVORITE_STATUS,"0");

            db.insert(TABLE_NAME,null,cv);
        }
    }

    public void insertIntoTheDB(String book_title,String book_year,String book_rate,String book_publisher,String book_description, String id,String fav_stat,String book_pic)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(BOOK_TITLE,book_title);
        cv.put(BOOK_YEAR,book_year);
        cv.put(BOOK_RATE,book_rate);
        cv.put(BOOK_PUBLISHER,book_publisher);
        cv.put(BOOK_DESCRIPTION,book_description);
        cv.put(KEY_ID,id);
        cv.put(FAVORITE_STATUS,fav_stat);
        cv.put(BOOK_PIC,book_pic);

        db.insert(TABLE_NAME,null,cv);
        Log.d("FavDB Status",book_title+", favstatus - "+FAVORITE_STATUS+" - . "+cv);
    }

    //read all data
    public Cursor read_all_data(String id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from "+TABLE_NAME+" where "+KEY_ID+"="+id+"";
        return db.rawQuery(sql,null,null);
    }
    //remove line from DB
    public void removeFav(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="UPDATE "+TABLE_NAME+" SET "+FAVORITE_STATUS+" ='0' WHERE "+KEY_ID+"="+id+"";
        db.execSQL(sql);
        Log.d("remove", id);
    }
    //select all favorite list
    public Cursor select_all_fav()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from "+TABLE_NAME+" where "+FAVORITE_STATUS+"= '1'";
        return db.rawQuery(sql,null,null);
    }
}