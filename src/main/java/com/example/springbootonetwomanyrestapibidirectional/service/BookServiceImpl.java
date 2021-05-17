package com.example.springbootonetwomanyrestapibidirectional.service;
import com.example.springbootonetwomanyrestapibidirectional.jpa.Book;
import com.example.springbootonetwomanyrestapibidirectional.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    @Override
    public List<Book> listBook() {
        try{
            List<Book> books = bookRepository.getAllBookStatus();
            return books;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getBookById(int id) {
        try{
            Book book = bookRepository.findById(id).get();
            return book;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveBook(Book book) {
        try {
            bookRepository.save(book);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBook(int id) {
        try{
            Book book = bookRepository.findById(id).get();
            book.setStatus(3);
            bookRepository.save(book);
            return true;

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBook(Book book) {
        try{
            bookRepository.save(book);
            return true;

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Book> lisBookByLibrary(int library_id) {
        try{
            List<Book> books = bookRepository.findAllByLibrary(library_id);

            return books;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.bookRepository.findPaginateBookStatus(pageable);
    }

    @Override
    public Page<Book> findPaginatedShow(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.bookRepository.findPaginateBookStatusShow(pageable);
    }

    @Override
    public Page<Book> findPaginatedHidden(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.bookRepository.findPaginateBookStatusHidden(pageable);    }

    @Override
    public boolean checkBookName(String name, int library_id) {
        Book book = bookRepository.findByBookName(name,library_id);
        if (book==null)
        {
            return true;
        }else{
            return false;
        }
    }
}
