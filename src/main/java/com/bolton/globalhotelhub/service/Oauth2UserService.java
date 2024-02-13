package com.bolton.globalhotelhub.service;

import com.bolton.globalhotelhub.dto.common.TokenDTO;

public interface Oauth2UserService {

    TokenDTO getUserTokenData(String username);

}
