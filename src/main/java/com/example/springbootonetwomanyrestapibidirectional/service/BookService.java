package com.example.springbootonetwomanyrestapibidirectional.service;

import com.example.springbootonetwomanyrestapibidirectional.jpa.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    List<Book> listBook();

    Book getBookById(int id);
    boolean saveBook(Book book);
    boolean deleteBook(int id);
    boolean updateBook(Book book);
    List<Book> lisBookByLibrary(int library_id);
    Page<Book> findPaginated(int pageNo, int pageSize);
    Page<Book> findPaginatedShow(int pageNo,int pageSize);
    Page<Book> findPaginatedHidden(int pageNo,int pageSize);

    boolean checkBookName(String name,int library_id);
}
