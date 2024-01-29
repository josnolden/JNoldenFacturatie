package nl.novi.jnoldenfacturatie.exceptions;

public class ExistingUsernameException extends RuntimeException {
    public ExistingUsernameException(String message){
        super(message);
    }

    public ExistingUsernameException(){
        super();
    }
}
