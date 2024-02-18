package com.LMS.controller;

import com.LMS.domain.Book;
import com.LMS.dto.BookDTO;
import com.LMS.services.BookService;
import com.LMS.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {


    @Autowired
    private BookService bookService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/saveBook")
    public ResponseEntity<Map<String, String>> saveBook(@Valid @RequestBody Book book){
        bookService.saveBook(book);
        Map<String, String> message = new HashMap<>();
        message.put("message", "Created Successfully");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/query")
    public ResponseEntity<Book> findBookById(@RequestParam("id") Long id){
        Book book = bookService.findBookById(id);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteBook(@RequestParam("id") Long id){
        bookService.deleteBookById(id);
        Map<String,String> message = new HashMap<>();
        message.put("message", "Deleted successfully");
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String,String>> updateBook(@RequestParam("id") Long id, @RequestBody BookDTO bookDTO){
        bookService.updateBook(id,bookDTO);
        Map<String,String> message = new HashMap<>();
        message.put("message", "Updated successfully");
        return ResponseEntity.ok(message);
    }

    @PostMapping("/teacher/{id}/book") //http://localhost:8080/books/1/book?bookId=1
    public ResponseEntity<Map<String,String>> addBookForTeacher(@PathVariable("id") Long id,
                                                                @RequestParam("bookId") Long bookId){
        return bookService.addBookForTeacher(id, bookId);
    }
}
