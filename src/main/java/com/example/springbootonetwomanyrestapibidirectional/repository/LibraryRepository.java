package com.example.springbootonetwomanyrestapibidirectional.repository;

import com.example.springbootonetwomanyrestapibidirectional.jpa.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library,Integer> {
}
