package com.olisa_td.accountservice.service.user;

import com.olisa_td.accountservice.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface UserOperations {

    Mono<UserResponse> getUser(String url,String id);

}
