package com.test.MyBook.controller;

import com.test.MyBook.dto.BookReqDto;
import com.test.MyBook.dto.BookResDto;
import com.test.MyBook.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/bookspage")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/index")
    public ModelAndView index() {
        List<BookResDto> bookResDtoList = bookService.getBooks();
        return new ModelAndView("index", "books", bookResDtoList);
    }

    @GetMapping("/signup")
    public String showSignUpForm(BookReqDto book) {
        return "add-book";
    }

    @PostMapping("/addbook")
    public String addBook(@Valid BookReqDto book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }
        bookService.saveBook(book);
        return "redirect:/bookspage/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        BookResDto bookResDto = bookService.getBookById(id);
        model.addAttribute("book", bookResDto);
        return "update-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") Long id, @Valid BookReqDto book,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            return "update-book";
        }
        bookService.updateBookForm(book);
        return "redirect:/bookspage/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/bookspage/index";
    }
}
