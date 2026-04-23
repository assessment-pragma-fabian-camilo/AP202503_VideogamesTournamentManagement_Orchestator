package com.fc2o.api.context;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Mono;

/**
 * WebFilter that extracts JWT from Authorization header and stores it in Reactor's Context.
 * This ensures the token is available throughout the entire reactive chain.
 */
@Component
@RequiredArgsConstructor
public class JwtWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> extractToken(exchange, securityContext.getAuthentication()))
                .defaultIfEmpty("")
                .flatMap(token -> chain.filter(exchange)
                        .contextWrite(ctx -> token.isEmpty() ? ctx : ctx.put("jwt_token", token)));
    }

    /**
     * Extracts JWT token from Authorization header.
     * Format: "Bearer <token>"
     *
     * @param exchange the server exchange
     * @param authentication the spring security authentication
     * @return the JWT token without "Bearer " prefix, or empty if not found
     */
    private String extractToken(ServerWebExchange exchange, Authentication authentication) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return "";
    }
}



