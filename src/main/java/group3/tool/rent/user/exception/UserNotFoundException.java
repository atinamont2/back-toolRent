package group3.tool.rent.user.exception;

import group3.tool.rent.common.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}