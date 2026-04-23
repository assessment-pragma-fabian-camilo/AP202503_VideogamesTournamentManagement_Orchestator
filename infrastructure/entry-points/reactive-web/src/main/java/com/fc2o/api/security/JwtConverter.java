package com.fc2o.api.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JWT Converter that extracts roles from JWT token and creates Authentication.
 */
@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        LinkedHashSet<String> normalizedRoles = new LinkedHashSet<>();

        // Extract roles from app_metadata.roles claim
        Map<String, Object> appMetadata = jwt.getClaimAsMap("app_metadata");
        if (appMetadata != null) {
            Object rolesObject = appMetadata.get("roles");
            if (rolesObject instanceof List<?> roles) {
                normalizedRoles.addAll(
                        roles.stream()
                                .map(String::valueOf)
                                .map(String::trim)
                                .filter(role -> !role.isBlank())
                                .map(String::toUpperCase)
                                .map(role -> role.startsWith("ROLE_") ? role.substring("ROLE_".length()) : role)
                                .collect(Collectors.toSet())
                );
            }
        }

        if (normalizedRoles.contains("MOD")) {
            normalizedRoles.add("MODERATOR");
        }
        if (normalizedRoles.contains("MODERATOR")) {
            normalizedRoles.add("MOD");
        }

        return normalizedRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}

