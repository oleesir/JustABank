package com.olisa_td.transactionservice.service.user;

import com.olisa_td.transactionservice.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface UserOperations {

    Mono<UserResponse> getUser(String url,String id);

}
