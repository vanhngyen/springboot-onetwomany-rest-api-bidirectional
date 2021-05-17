package com.example.springbootonetwomanyrestapibidirectional.repository;

import com.example.springbootonetwomanyrestapibidirectional.jpa.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {

}
