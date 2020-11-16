package com.github.playernguyen.exceptions;

/**
 * Inheritance exception class.
 * This class will throw by cannot load the true storage type.
 * For example: whether the storage is <i>mysqu</i> -> throw this class.
 */
public class InvalidStorageTypeException extends Exception {

    public InvalidStorageTypeException() {
    }

    public InvalidStorageTypeException(String message) {
        super(message);
    }
}
