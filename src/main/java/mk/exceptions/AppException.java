package mk.exceptions;

public class AppException extends RuntimeException {
    private String exceptionMessage;

    public AppException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage;
    }
}
