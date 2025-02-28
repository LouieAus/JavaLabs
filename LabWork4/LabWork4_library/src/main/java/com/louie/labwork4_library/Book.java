package com.louie.labwork4_library;

public class Book
{
    private final int article;
    private final String title;
    private final String author;
    private final boolean available;

    public Book(int article, String title, String author, boolean available) {
        this.article = article;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public int getArticle() {
        return article;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }
}
