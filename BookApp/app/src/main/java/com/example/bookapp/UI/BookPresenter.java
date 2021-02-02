package com.example.bookapp.UI;

import com.example.bookapp.Model.Book;

public class BookPresenter
{
    BookView view;
    Book book;

    public BookPresenter(BookView view)
    {
        this.view = view;
    }
    public Book getBookFromDatabase()
    {
        return new Book(book.getTitle(),book.getYear(),book.getRate(),book.getPublisher(),book.getDescription(),book.getPic());
    }
    public void getBookTitle()
    {
        view.onGetBookTitle(getBookFromDatabase().getTitle());
    }
    public void getBookTitleYear()
    {
        view.onGetBookTitle(getBookFromDatabase().getYear());
    }
    public void getBookRate()
    {
        view.onGetBookTitle(getBookFromDatabase().getRate());
    }
    public void getBookPublisher()
    {
        view.onGetBookTitle(getBookFromDatabase().getPublisher());
    }
    public void getBookDescription()
    {
        view.onGetBookTitle(getBookFromDatabase().getDescription());
    }
    public void getBookPic()
    {
        view.onGetBookTitle(getBookFromDatabase().getPic());
    }
}
