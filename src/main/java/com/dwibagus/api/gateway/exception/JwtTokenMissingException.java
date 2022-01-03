package com.dwibagus.api.gateway.exception;

import javax.naming.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {
    private static final long serialVersionId = 1L;

    public JwtTokenMissingException(String msg) {super(msg);}
}
