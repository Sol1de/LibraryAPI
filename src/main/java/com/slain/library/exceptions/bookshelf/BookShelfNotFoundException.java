package com.slain.library.exceptions.bookshelf;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The Bookshelf was not found")
public class BookShelfNotFoundException extends Exception {}
