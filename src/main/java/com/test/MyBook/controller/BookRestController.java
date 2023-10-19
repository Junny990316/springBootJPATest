package com.test.MyBook.controller;

import com.test.MyBook.dto.BookReqDto;
import com.test.MyBook.dto.BookResDto;
import com.test.MyBook.service.BookService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @PostMapping
    public BookResDto saveBook(@RequestBody BookReqDto bookReqDto) {
        return bookService.saveBook(bookReqDto);
    }

    @GetMapping("/{id}")
    public BookResDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<BookResDto> getBooks() {
        return bookService.getBooks();
    }

    @PutMapping("/{isbn}")
    public BookResDto updateBook(@PathVariable String isbn, @RequestBody BookReqDto bookReqDto) {
        return bookService.updateBook(isbn, bookReqDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(id + " 가 삭제처리 되었습니다.");
    }
}
