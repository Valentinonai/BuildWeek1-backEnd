package exceptions;

public class VenditoreNotFoundException extends RuntimeException {
    public VenditoreNotFoundException(String message) {
        super(message);
    }
}
