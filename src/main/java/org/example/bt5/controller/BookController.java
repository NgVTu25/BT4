package org.example.bt5.controller;


import lombok.RequiredArgsConstructor;
import org.example.bt5.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books/api")
public class BookController {

    private final BookService bookService;

    @GetMapping("/{db}")
    public ResponseEntity<Page<?>> getBooks(@PathVariable String db, Pageable pageable) {
        return ResponseEntity.ok(bookService.findAllPaging(db, pageable));
    }

    @PostMapping("/{db}")
    public ResponseEntity<Object> createBook(@PathVariable String db, @RequestBody Object book) {
        bookService.saveBook(book, db);
        return ResponseEntity.ok(bookService.saveBook(book, db));
    }

    @GetMapping("/{db}/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable String db, @PathVariable String id) {
        Object book = bookService.searchBookById(db, id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{db}/search")
    public ResponseEntity<Page<?>> searchBooks(
            @PathVariable String db,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String content,
            Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.searchBooks(db, title, author, content, pageable));
    }

    @PutMapping("/{db}/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable String db,
            @PathVariable String id,
            @RequestBody Object book
    ) {
        boolean status = bookService.updateBook(db.trim().toLowerCase(), id, book);

        if (!status) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Update failed: Book not found"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Update success",
                "id", id
        ));
    }

    @DeleteMapping("/{db}")
    public ResponseEntity<?> deleteBook(
            @PathVariable String db,
            @RequestBody List<String> ids
    ) {
        boolean status = bookService.deleteBooks(db, ids);

        if (!status) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Delete failed"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Delete success",
                "id", ids
        ));
    }

    @GetMapping("/{db}/statistic")
    public ResponseEntity<Map<String, Object>> statisticByAuthor(
            @PathVariable String db,
            @RequestParam String author
    ) {
        return ResponseEntity.ok(bookService.statisticByAuthor(db, author));
    }
}