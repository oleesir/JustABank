package com.olisa_td.transactionservice.service.account;

import com.olisa_td.transactionservice.dto.AccountResponse;
import reactor.core.publisher.Mono;

public interface AccountOperations {

    Mono<AccountResponse> getAccount(String url,String id);

}
