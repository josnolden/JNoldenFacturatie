package nl.novi.jnoldenfacturatie.exceptions;

public class InExistingFactuurException extends RuntimeException {
    public InExistingFactuurException(String message) {
        super(message);
    }
    public InExistingFactuurException() {
        super();
    }
}
