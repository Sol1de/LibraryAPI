package com.slain.library.exceptions.author;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The Author was not found")
public class AuthorNotFoundException extends Exception {}
