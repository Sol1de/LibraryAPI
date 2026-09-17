package com.slain.library.exceptions.reader;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The Reader was not found")
public class ReaderNotFoundException extends Exception {}
