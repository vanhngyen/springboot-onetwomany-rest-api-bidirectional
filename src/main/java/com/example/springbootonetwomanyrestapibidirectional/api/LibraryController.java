package com.example.springbootonetwomanyrestapibidirectional.api;


import com.example.springbootonetwomanyrestapibidirectional.jpa.Library;
import com.example.springbootonetwomanyrestapibidirectional.repository.BookRepository;
import com.example.springbootonetwomanyrestapibidirectional.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

//RestController API
@RestController
@RequestMapping("/api/v1/libraries")
//@RequiredArgsConstructor
public class LibraryController {
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryController(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping //read data
    public ResponseEntity<Page<Library>> getAll(Pageable pageable){
        return ResponseEntity.ok(libraryRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getById(@PathVariable Integer id){
        Optional<Library> optionalLibrary = libraryRepository.findById(id);
        if(!optionalLibrary.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(libraryRepository.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Library> createLibrary(@Valid @RequestBody Library library){
        Library librarySaved = libraryRepository.save(library);
        //check header of service
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(librarySaved.getId()).toUri();
        return  ResponseEntity.created(location).body(librarySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> updateLibrary(@PathVariable Integer id, @Valid @RequestBody Library library){
        Optional<Library> optionalLibrary = libraryRepository.findById(id);
        if(!optionalLibrary.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        library.setId(optionalLibrary.get().getId());
        libraryRepository.save(library);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Library> deleteLibrary(@PathVariable Integer id){
        Optional<Library> optionalLibrary = libraryRepository.findById(id);
        if(!optionalLibrary.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        libraryRepository.delete(optionalLibrary.get());
        return  ResponseEntity.noContent().build();
    }

}

















