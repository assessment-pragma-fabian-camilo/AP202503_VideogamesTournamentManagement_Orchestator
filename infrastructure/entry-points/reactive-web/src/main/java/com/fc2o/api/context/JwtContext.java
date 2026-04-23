package com.fc2o.api.context;

import reactor.util.context.Context;

/**
 * Utility class to manage JWT token in reactive context.
 * Provides methods to store and retrieve JWT from Reactor's Context.
 */
public class JwtContext {

    public static final String JWT_KEY = "jwt_token";

    /**
     * Stores JWT token in Reactor's Context.
     *
     * @param token the JWT token
     * @return a Context with the token
     */
    public static Context withToken(String token) {
        return Context.of(JWT_KEY, token);
    }

    /**
     * Retrieves JWT token from Reactor's Context.
     * Returns an empty string if token is not found.
     *
     * @param context the Reactor Context
     * @return the JWT token or empty string
     */
    public static String getToken(Context context) {
        return context.get(JWT_KEY);
    }
}

