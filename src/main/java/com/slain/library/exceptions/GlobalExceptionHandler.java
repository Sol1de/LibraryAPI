package com.slain.library.exceptions;

import com.slain.library.exceptions.author.AuthorNotFoundException;
import com.slain.library.exceptions.book.BookNotFoundException;
import com.slain.library.exceptions.bookshelf.BookShelfNotFoundException;
import com.slain.library.exceptions.library.LibraryNotFoundException;
import com.slain.library.exceptions.reader.ReaderNotFoundException;
import com.slain.library.exceptions.shelf.ShelfNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            BookNotFoundException.class,
            AuthorNotFoundException.class,
            ReaderNotFoundException.class,
            ShelfNotFoundException.class,
            BookShelfNotFoundException.class,
            LibraryNotFoundException.class
    })

    public ResponseEntity<Map<String, Object>> handleNotFound(Exception exception) {
        ResponseStatus response = exception.getClass().getAnnotation(ResponseStatus.class);

        return ResponseEntity
                .status(response.code())
                .body(Map.of(
                        "status", response.code().value(),
                        "message", response.reason()
                ));
    }
}
