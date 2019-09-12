package org.springrest.assingment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springrest.assingment.entity.Book;
import org.springrest.assingment.entity.BookRepository;

@RestController
@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AppRestController {
	
	@Autowired
	BookRepository bookRepo;
	public BookRepository getRepository() {
        return bookRepo;
    }
 
    public void setRepository(BookRepository repository) {
        this.bookRepo = repository;
    }
    
    @PostMapping(value = "/book/post",consumes=MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity postBook(@RequestBody Book book)
   	{
    	try {
			bookRepo.save(book);
			return new ResponseEntity("Success", HttpStatus.OK);
			
		}catch(Exception ex) {
			return new ResponseEntity("Bad Request", HttpStatus.BAD_REQUEST);
		}
   	
   	}
    
    @PutMapping(value = "/book/put",consumes=MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity putBook(@RequestBody Book book)
   	{
    	try {
			Book bookList = bookRepo.findById(book.getBookId()).orElseThrow(() -> new NoSuchElementException("Book id '" + book.getBookId() + "' does not exist"));
			bookRepo.save(book);
			return new ResponseEntity("Success", HttpStatus.OK);
			
		}catch(Exception ex) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
    	
   	}
    
    @SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/book/{id}")
	public ResponseEntity deleteBookByID(@PathVariable long id)
	{
    	try {
		Book bookList = bookRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Book id '" + id + "' does not exist"));
		bookRepo.deleteById(id);
		return new ResponseEntity("Success", HttpStatus.OK);
		
	}catch(Exception ex) {
		return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
		
		
	}
    
    @GetMapping(value = "/books",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Book> getAllBooks()
	{
		List<Book> bookList = (ArrayList)bookRepo.findAll();
		return bookList;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/book/{id}")
	@ExceptionHandler({NoSuchElementException.class})
	public ResponseEntity getBookByID(@PathVariable long id) {
	Book bookList =null;
	{
		try {
			bookList = bookRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Book id '" + id + "' does not exist"));
			return new ResponseEntity(bookList, HttpStatus.OK);
			
		}catch(Exception ex) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	}
}
