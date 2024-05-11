package aespa.codeeasy.exception;

import java.io.IOException;

public class FolderDeleteException extends IOException {
    public FolderDeleteException() {
        super();
    }

    public FolderDeleteException(String message) {
        super(message);
    }

    public FolderDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}