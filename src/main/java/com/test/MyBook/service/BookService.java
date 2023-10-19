package com.test.MyBook.service;

import com.test.MyBook.dto.BookReqDto;
import com.test.MyBook.dto.BookResDto;
import com.test.MyBook.entity.BookEntity;
import com.test.MyBook.exception.BusinessException;
import com.test.MyBook.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookResDto saveBook(BookReqDto bookReqDto) {
        BookEntity book = modelMapper.map(bookReqDto, BookEntity.class);
        BookEntity savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookResDto.class);
    }

    @Transactional(readOnly = true)
    public BookResDto getBookById(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("book not found", HttpStatus.NOT_FOUND));
        BookResDto bookResDto = modelMapper.map(book, BookResDto.class);
        return bookResDto;
    }

    @Transactional(readOnly = true)
    public List<BookResDto> getBooks() {
        List<BookEntity> bookList = bookRepository.findAll();

        List<BookResDto> bookResDtoList = bookList.stream()
                .map(book -> modelMapper.map(book, BookResDto.class))
                .collect(Collectors.toList());
        return bookResDtoList;
    }

    public BookResDto updateBook(String isbn, BookReqDto bookReqDto) {
        BookEntity existBook = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("book not found", HttpStatus.NOT_FOUND));
        existBook.setTitle(bookReqDto.getTitle());
        return modelMapper.map(existBook, BookResDto.class);
    }

    public void updateBookForm(BookReqDto bookReqDto) {
        BookEntity existBook = bookRepository.findById(bookReqDto.getId())
                .orElseThrow(() -> new BusinessException(bookReqDto.getId()+ "book not found", HttpStatus.NOT_FOUND)) ;
        existBook.setTitle(bookReqDto.getTitle());
    }

    public void deleteBook(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("book not found", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
    }
}
