package com.kt.common.exception;

/**
 * ApiException
 */
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = -325408487145948952L;
    private final String errorKey;
    private final Object[] values;

    /**
     * Constructor
     *
     * @param errorKey String
     * @param values   Object...
     */
    public ApiException(final String errorKey, final Object... values) {
        this.errorKey = errorKey;
        this.values = values;
    }

    /**
     * Constructor
     *
     * @param errorKey String
     */
    public ApiException(final String errorKey) {
        this(errorKey, new Object[0]);
    }

    /**
     * getErrorKey
     *
     * @return String
     */
    public String getErrorKey() {
        return errorKey;
    }

    /**
     * getValues
     *
     * @return Object[]
     */
    public Object[] getValues() {
        return values;
    }
}
