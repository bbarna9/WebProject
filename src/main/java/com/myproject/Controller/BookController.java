package com.myproject.Controller;

import com.myproject.Model.Book;
import com.myproject.Service.BookNotFoundException;
import com.myproject.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {
    @Autowired private BookService service;

    @GetMapping("/books")
    public String showBookList(Model model){
        List<Book> listBooks = service.listAll();
        model.addAttribute("listBooks", listBooks);

        return "books";
    }

    @GetMapping("/books/new")
    public String showAddPage(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("pageTitle", "Add New Book");
        return "book_page";
    }

    @PostMapping("/books/save")
    public String saveBook(Book book, RedirectAttributes ra){
        service.save(book);
        ra.addFlashAttribute("message", "The book has been saved successfully");
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditPage(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Book book = service.get(id);
            model.addAttribute("book", book);
            model.addAttribute("pageTitle", "Edit Existing Book (ID: " + id + ")");
            return "book_page";
        } catch (BookNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/books";
        }
    }

    @GetMapping("/books/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The book with ID " + id + " has been deleted successfully.");
        } catch (BookNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/books";
    }

}
