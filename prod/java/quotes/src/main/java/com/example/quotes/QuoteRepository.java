package com.example.quotes;

import com.example.quotes.data.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuoteRepository extends JpaRepository<Quote,Integer> {

    @Query( nativeQuery = true, value =
            "SELECT id,quote,author FROM quotes ORDER BY RANDOM() LIMIT 1")
    Quote findRandomQuote();
}
