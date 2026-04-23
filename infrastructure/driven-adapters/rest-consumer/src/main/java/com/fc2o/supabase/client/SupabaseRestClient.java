package com.fc2o.supabase.client;

import com.fc2o.supabase.context.JwtContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SupabaseRestClient {

    private final WebClient webClient;

    public SupabaseRestClient(@Qualifier("supabaseWebClient") WebClient supabaseWebClient) {
        this.webClient = supabaseWebClient;
    }

    public <T> Flux<T> select(String table, Map<String, String> filters, Class<T> responseType) {
        return Flux.deferContextual(ctx -> {
            String token = JwtContext.getToken(ctx);
            return webClient.get()
                    .uri(uriBuilder -> {
                        uriBuilder.path("/" + table);
                        if (filters != null) {
                            filters.forEach((key, value) -> uriBuilder.queryParam(key, "eq." + value));
                        }
                        return uriBuilder.build();
                    })
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(responseType)
                    .onErrorMap(t -> new RuntimeException("Error fetching data from Supabase: " + t.getMessage(), t));
        });
    }

    public <T> Mono<T> insert(String table, Object payload, Class<T> responseType) {
        return Mono.deferContextual((ctx) -> {
            String token = JwtContext.getToken(ctx);
            return webClient.post()
                    .uri("/" + table)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .header("Prefer", "return=representation")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToFlux(responseType)
                    .singleOrEmpty();
        });
    }

    public <T> Mono<T> updateById(String table, String id, Object payload, Class<T> responseType) {
        return Mono.deferContextual((ctx) -> {
            String token = JwtContext.getToken(ctx);
            return webClient.patch()
                    .uri(uriBuilder -> uriBuilder.path("/" + table).queryParam("id", "eq." + id).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .header("Prefer", "return=representation")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToFlux(responseType)
                    .singleOrEmpty();
        });
    }

    public Mono<Void> deleteById(String table, String id) {
        return Mono.deferContextual((ctx) -> {
            String token = JwtContext.getToken(ctx);
            return webClient.delete()
                    .uri(uriBuilder -> uriBuilder.path("/" + table).queryParam("id", "eq." + id).build())
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Void.class);
        });
    }

    public String buildQuery(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return "";
        }

        return filters.entrySet().stream()
                .map(entry -> entry.getKey() + "=eq." + entry.getValue())
                .collect(Collectors.joining("&"));
    }
}

