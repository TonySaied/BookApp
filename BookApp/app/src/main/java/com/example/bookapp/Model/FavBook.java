package com.example.bookapp.Model;

public class FavBook
{
    private String BookTitle;
    private String BookID;
    private String BookYear;
    private String BookRate;
    private String BookPublisher;
    private String BookDescription;
    private String BookPic;


    public FavBook(String bookTitle, String bookID, String bookYear, String bookRate, String bookPublisher, String bookDescription,String BookPic)
    {
        BookTitle = bookTitle;
        BookID = bookID;
        BookYear = bookYear;
        BookRate = bookRate;
        BookPublisher = bookPublisher;
        BookDescription = bookDescription;
        this.BookPic=BookPic;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getBookYear() {
        return BookYear;
    }

    public void setBookYear(String bookYear) {
        BookYear = bookYear;
    }

    public String getBookRate() {
        return BookRate;
    }

    public void setBookRate(String bookRate) {
        BookRate = bookRate;
    }

    public String getBookPublisher() {
        return BookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        BookPublisher = bookPublisher;
    }

    public String getBookDescription() {
        return BookDescription;
    }

    public void setBookDescription(String bookDescription) {
        BookDescription = bookDescription;
    }

    public String getBookPic() {
        return BookPic;
    }

    public void setBookPic(String bookPic) {
        BookPic = bookPic;
    }
}
