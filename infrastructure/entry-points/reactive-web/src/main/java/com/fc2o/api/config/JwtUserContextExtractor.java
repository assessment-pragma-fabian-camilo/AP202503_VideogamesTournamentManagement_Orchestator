package com.fc2o.api.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUserContextExtractor {

    public Mono<String> extractUserId() {
        return extractJwt().map(Jwt::getSubject);
    }

    @SuppressWarnings("unchecked")
    public Mono<Set<String>> extractRoles() {
        return extractJwt().map(jwt -> {
            Object appMetadata = jwt.getClaims().getOrDefault("app_metadata", Collections.emptyMap());
            if (!(appMetadata instanceof java.util.Map<?, ?> metadataMap)) {
                return Set.<String>of();
            }

            Object roles = metadataMap.get("roles");
            if (!(roles instanceof java.util.List<?> roleList)) {
                return Set.<String>of();
            }

            return roleList.stream()
                    .map(String::valueOf)
                    .map(String::trim)
                    .filter(role -> !role.isBlank())
                    .map(String::toUpperCase)
                    .map(role -> role.startsWith("ROLE_") ? role.substring("ROLE_".length()) : role)
                    .collect(Collectors.toSet());
        });
    }

    private Mono<Jwt> extractJwt() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .map(Authentication::getPrincipal)
                .cast(Jwt.class);
    }
}

