package com.slain.library.exceptions.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The Book was not found")
public class BookNotFoundException extends Exception {}
