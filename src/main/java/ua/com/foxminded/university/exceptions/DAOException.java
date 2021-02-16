package ua.com.foxminded.university.exceptions;

public class DAOException extends Exception {

    private final static String ERROR_MESSAGE = "There is no record in the database with id ";

    public <T> DAOException(T t) {
        super(ERROR_MESSAGE + t);
    }
}
