package nl.novi.jnoldenfacturatie.controllers;

import nl.novi.jnoldenfacturatie.exceptions.InExistingFactuurException;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value= NotFoundException.class)
    public ResponseEntity<Object> exception(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value= InExistingFactuurException.class)
    public ResponseEntity<Object> exception(InExistingFactuurException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
