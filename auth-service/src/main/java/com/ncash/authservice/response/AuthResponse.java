package com.ncash.authservice.response;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class AuthResponse {

    private String Token;

}
