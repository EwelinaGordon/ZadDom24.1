package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookDAO bookDAO;

    @GetMapping("/")
    public String home(Model model) {
        getAllBooks(model, "");
        model.addAttribute("book", new Book());
        return "home";
    }

    private void getAllBooks(Model model, String sortCode) {
        List<Book> allBooks = bookDAO.readBooks(sortCode);
        model.addAttribute("allBooks", allBooks);
    }

    @GetMapping("/usun")
    public String deleteBook(Model model, @RequestParam long id) {
        bookDAO.delete(id);
        model.addAttribute("book", new Book());
        return "redirect:/";
    }

    @GetMapping("/edytuj")
    public String editBook(Model model, @RequestParam long id) {
        Book book = bookDAO.readBookByID(id);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/edytuj")
    public String editBook(Book book) {
        bookDAO.updateBook(book);
        return "redirect:/";
    }


    @GetMapping("/sort")
    public String sort(Model model,@RequestParam String sortCode){
        getAllBooks(model, sortCode);
        model.addAttribute("book", new Book());
        return "home";
    }

    @PostMapping("/addbook")
    public String metoda(Book book) {
        bookDAO.save(book);
        return "redirect:/";
    }
}
