package com.fc2o.api.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SupabaseJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new UsernamePasswordAuthenticationToken(jwt, jwt.getSubject(), authorities);
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Object appMetadata = jwt.getClaims().getOrDefault("app_metadata", Collections.emptyMap());
        if (!(appMetadata instanceof Map<?, ?> metadataMap)) {
            return List.of();
        }

        Object roles = metadataMap.get("roles");
        if (!(roles instanceof List<?> rolesList)) {
            return List.of();
        }

        LinkedHashSet<String> normalizedRoles = rolesList.stream()
                .map(String::valueOf)
                .map(String::trim)
                .filter(role -> !role.isBlank())
                .map(String::toUpperCase)
                .map(role -> role.startsWith("ROLE_") ? role.substring("ROLE_".length()) : role)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // Keep MOD and MODERATOR interoperable because business docs use both terms.
        if (normalizedRoles.contains("MOD")) {
            normalizedRoles.add("MODERATOR");
        }
        if (normalizedRoles.contains("MODERATOR")) {
            normalizedRoles.add("MOD");
        }

        return normalizedRoles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}

