package com.example.bookapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book //implements Parcelable
{
    private String title;
    private String year;
    private String rate;
    private String Publisher;
    private String description;

    private String pic;
    private String id;
    private String favStatus;

    public Book()
    {

    }
    public Book(String title, String year, String rate, String publisher, String description, String pic)
    {
        this.title = title;
        this.year = year;
        this.rate = rate;
        Publisher = publisher;
        this.description = description;
        this.pic=pic;
    }



    /*
    protected Book(Parcel in) {
        //title = in.readString();
        pic = in.readString();
        //Publisher = in.readString();
        //year = in.readString();
        //rate = in.readString();
        //description = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }
    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeString(title);
        dest.writeString(pic);
        //dest.writeString(Publisher);
        //dest.writeString(year);
        //dest.writeString(rate);
        //dest.writeString(description);
    }*/
}
