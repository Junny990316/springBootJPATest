package com.test.MyBook.controller;

import com.test.MyBook.entity.BookEntity;
import com.test.MyBook.exception.BusinessException;
import com.test.MyBook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookSimpleRestController {
    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public BookEntity create(@RequestBody BookEntity book) {
        return bookRepository.save(book);
    }

    @GetMapping
    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public BookEntity getBook(@PathVariable Long id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        BookEntity book = optionalBook.orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));
        return book;
    }

    @GetMapping("/isbn/{isbn}")
    public BookEntity getBookByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("요청하신 bnNumber에 해당하는 책이 없습니다.", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not fount", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
        return ResponseEntity.ok(id + " Book 이 삭제 되었습니다");
    }

    @PutMapping("/{id}")
    public BookEntity updateBook(@PathVariable Long id, @RequestBody BookEntity bookDetail) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("book not found", HttpStatus.NOT_FOUND));
        book.setTitle(bookDetail.getTitle());
        book.setAuthor(book.getAuthor());
        book.setGenre(book.getGenre());
        book.setIsbn(book.getIsbn());
        BookEntity updateBook = bookRepository.save(book);
        return updateBook;
    }


}
