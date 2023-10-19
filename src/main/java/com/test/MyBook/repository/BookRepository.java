package com.test.MyBook.repository;


import com.test.MyBook.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
        Optional<BookEntity> findByIsbn(String isbn);

        List<BookEntity> findByAuthor(String title);
}
