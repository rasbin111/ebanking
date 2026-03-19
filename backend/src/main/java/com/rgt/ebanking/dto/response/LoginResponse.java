package com.rgt.ebanking.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse{
    String accessToken;
    String refreshToken;
    String email;
    String username;
}
