package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookDAO bookDAO;

    @PersistenceUnit
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @GetMapping("/")
    public String home(Model model){

        getAllBooks(model);
        model.addAttribute("book", new Book());
        return "home";
    }

    private void getAllBooks(Model model) {
        List<Book> allBooks = bookDAO.readBooks();
        model.addAttribute("allBooks", allBooks);
    }

    @GetMapping("/usun")
    public String deleteBook(Model model, @RequestParam int id){
                bookDAO.delete(id);
                model.addAttribute("book", new Book());
                getAllBooks(model);
                return "redirect:/";
    }

    @PostMapping("/addbook")
    public String metoda(Book book){
        bookDAO.save(book);
        return "redirect:/";
    }
}
