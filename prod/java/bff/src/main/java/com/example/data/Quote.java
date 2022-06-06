package com.example.data;

import java.util.Objects;

public class Quote
{
    private Integer id;
    private String quote;
    private String author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
        Quote quote1 = (Quote) o;
        return Objects.equals(id, quote1.getId()) &&
                Objects.equals(quote, quote1.getQuote()) &&
                Objects.equals(author, quote1.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quote, author);
    }
}
