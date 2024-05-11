package aespa.codeeasy.exception;

import java.io.IOException;

public class FileDeleteException extends IOException {
    public FileDeleteException() {
        super();
    }

    public FileDeleteException(String message) {
        super(message);
    }

    public FileDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
