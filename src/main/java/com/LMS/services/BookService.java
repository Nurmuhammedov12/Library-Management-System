package com.LMS.services;

import com.LMS.domain.Book;
import com.LMS.domain.Teacher;
import com.LMS.dto.BookDTO;
import com.LMS.exception.Conflikt;
import com.LMS.repository.BookRepo;
import com.LMS.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepo teacherRepo;

    public void saveBook(Book book) {
        bookRepo.save(book);
    }


    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book findBookById(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(()->new Conflikt("This book doesn't have in database"));
        return book;
    }

    public void deleteBookById(Long id) {
        Book book = findBookById(id);
        bookRepo.delete(book);
    }

    public void updateBook(Long id, BookDTO bookDTO) {
        Book book = findBookById(id);
        boolean existBook = bookRepo.existsById(id);
        boolean belongWhom = bookDTO.getTitle().equals(book.getTitle());
        if (existBook && !belongWhom){
            new Conflikt("Title problem");
        }

        bookRepo.save(UpdateBookandPast(book, bookDTO));
    }

    public Book UpdateBookandPast(Book book, BookDTO bookDTO){
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setPublicationDate(bookDTO.getPublicationDate());
        return book;
    }

    public ResponseEntity<Map<String, String>> addBookForTeacher(Long id, Long bookId) {
        // Find Teacher
        Teacher teacher = teacherService.findById(id);
        //Find Book
        Optional<Book> optionalBook = bookRepo.findById(bookId);

        if(optionalBook.isEmpty()){
            Map<String,String> message = new HashMap<>();
            message.put("message", "Book is don't found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        Book book = optionalBook.get();

        //check exist book before add same book

        boolean existbookinteacher = teacher.getBooks().stream().anyMatch(b-> b.getId().equals(book.getId()));

        if (existbookinteacher){
            Map<String,String> message = new HashMap<>();
            message.put("message", "Book is already exist in this teacher");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }


        teacher.getBooks().add(book);
        teacherRepo.save(teacher);
        Map<String,String> message = new HashMap<>();
        message.put("message", "Book added successfully");
        return new ResponseEntity<>(message, HttpStatus.CREATED);


    }
}
