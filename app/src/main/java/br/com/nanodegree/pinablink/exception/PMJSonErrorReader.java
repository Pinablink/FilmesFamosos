package br.com.nanodegree.pinablink.exception;

/**
 * Created by Pinablink on 13/04/2018.
 */
public class PMJSonErrorReader extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public PMJSonErrorReader() {
        super();
    }

    /**
     * @param message
     */
    public PMJSonErrorReader(String message) {
        super(message);
    }

    /**
     * @param message
     * @param throwable
     */
    public PMJSonErrorReader(String message, Throwable throwable) {
        super(message, throwable);
    }
}
