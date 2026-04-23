package com.fc2o.supabase.user;

import com.fc2o.model.user.gateways.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserSupabaseAdapter implements UserRepository {
    @Override
    public Mono<Boolean> isActiveUser(String id) {
        return Mono.just(Boolean.TRUE);
    }
}
