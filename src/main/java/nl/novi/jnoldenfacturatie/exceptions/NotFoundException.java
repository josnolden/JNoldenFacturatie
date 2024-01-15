package nl.novi.jnoldenfacturatie.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException() {
        super();
    }
}
