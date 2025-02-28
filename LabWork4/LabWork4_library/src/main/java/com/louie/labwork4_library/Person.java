package com.louie.labwork4_library;

public class Person
{
    private final String person;
    private final String book;

    public Person(String person, String book)
    {
        this.person = person;
        this.book = book;
    }

    public String getPerson() {
        return person;
    }

    public String getBook() {
        return book;
    }
}
