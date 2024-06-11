package pe.edu.utp.exceptions;

public class EmpAlreadyExistsException extends RuntimeException {
    public EmpAlreadyExistsException(String message) {
        super(message);
    }
}
