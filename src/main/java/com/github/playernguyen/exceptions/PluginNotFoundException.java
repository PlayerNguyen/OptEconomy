package com.github.playernguyen.exceptions;

/**
 * This exception will be called when the plugin wasn't exist on your server (full-depend error)
 */
public class PluginNotFoundException extends Exception {

    public PluginNotFoundException() {
    }

    public PluginNotFoundException(String message) {
        super(message);
    }

    public PluginNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginNotFoundException(Throwable cause) {
        super(cause);
    }

    public PluginNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
