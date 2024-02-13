package com.bolton.globalhotelhub.config;

import com.bolton.globalhotelhub.dto.common.TokenDTO;
import com.bolton.globalhotelhub.service.Oauth2UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;



@Component
public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    private static final Logger LOGGER = LogManager.getLogger(CustomTokenEnhancer.class);
    private final Oauth2UserService oauth2UserService;

    public CustomTokenEnhancer(Oauth2UserService oauth2UserService) {
        this.oauth2UserService = oauth2UserService;
    }


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        LOGGER.info("Start Function CustomTokenEnhancer enhance");
        final Map<String, Object> additionalInfo = new HashMap<>();

        /**
         * get client ID
         */
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String clientId = user.getUsername();

        /**
         * get auth username
         */
        User userEntity = (User) oAuth2Authentication.getPrincipal();
        TokenDTO userTokenData = oauth2UserService.getUserTokenData(userEntity.getUsername());

        additionalInfo.put("id", userTokenData.getId());
        additionalInfo.put("fullName", userTokenData.getFullName());
        additionalInfo.put("mobileNumber", userTokenData.getContactNumber());
        additionalInfo.put("email", userTokenData.getEmail());

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);

        // set custom claims
        return super.enhance(oAuth2AccessToken, oAuth2Authentication);
    }

}
