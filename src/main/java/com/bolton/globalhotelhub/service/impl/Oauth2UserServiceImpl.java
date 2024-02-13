package com.bolton.globalhotelhub.service.impl;

import com.bolton.globalhotelhub.entity.Users;
import com.bolton.globalhotelhub.service.Oauth2UserService;
import com.bolton.globalhotelhub.dto.common.TokenDTO;
import com.bolton.globalhotelhub.enums.UserRole;
import com.bolton.globalhotelhub.exception.CustomOauthException;
import com.bolton.globalhotelhub.repository.UserRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static com.bolton.globalhotelhub.constant.OAuth2TokenConstant.ADMIN_CLIENT_ID;
import static com.bolton.globalhotelhub.constant.OAuth2TokenConstant.CLIENT_ID;


@Service(value = "userService")
public class Oauth2UserServiceImpl implements UserDetailsService, Oauth2UserService {

    private static final Logger LOGGER = LogManager.getLogger(Oauth2UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public Oauth2UserServiceImpl(UserRepository userRepository, HttpServletRequest httpServletRequest) {
        this.userRepository = userRepository;
    }

    @Override
    public TokenDTO getUserTokenData(String username) {
        try {
            LOGGER.info("Find Username  : " + username);

            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            String clientId = user.getUsername();

            TokenDTO tokenDTO = new TokenDTO();

                Users usersEntity = userRepository.findByEmail(username);

                if (usersEntity == null) {
                    LOGGER.error("User Details not found : " + username);
                    throw new CustomOauthException("The username you entered is incorrect.");
                }


                tokenDTO.setId(usersEntity.getId());
                tokenDTO.setUserRole(usersEntity.getUserRole());
                tokenDTO.setFullName(usersEntity.getName());
                tokenDTO.setContactNumber(usersEntity.getContactNumber());
                tokenDTO.setEmail(usersEntity.getEmail());


            return tokenDTO;

        } catch (Exception e) {
            LOGGER.error("Function getUserTokenData  : " + e.getMessage());
            throw e;
        }
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {

            /**
             * get user client ID
             */
            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            String clientId = user.getUsername();

            switch (clientId) {

                case ADMIN_CLIENT_ID:

                   Users adminUsersEntity = userRepository.findByEmail(username);

                    if (adminUsersEntity == null) {
                        LOGGER.error("Invalid Grant  : Bad credentials, username : " + username);
                        throw new CustomOauthException("The username you entered is incorrect.");
                    }


                    return new User(adminUsersEntity.getEmail(), adminUsersEntity.getPassword(), getAdminUserAuthority(adminUsersEntity));

                case CLIENT_ID:

                    Users publicUsersEntity = userRepository.findByEmail(username);

                    if (publicUsersEntity == null) {
                        LOGGER.error("Invalid Grant  : Bad credentials, username : " + username);
                        throw new CustomOauthException("The username you entered is incorrect.");
                    }


                    if (!publicUsersEntity.getUserRole().equals(UserRole.PUBLIC_USER)) {
                        LOGGER.error("Invalid ClientId  : username : " + username);
                        throw new CustomOauthException("Invalid ClientId.");
                    }

                    return new User(publicUsersEntity.getEmail(), publicUsersEntity.getPassword(), getCashierAuthority(publicUsersEntity));

                default:
                    throw new CustomOauthException("incorrect user type.");
            }


            } catch(Exception e){
                LOGGER.error("Function loadUserByUsername  : " + e.getMessage());
                throw e;
            }

        }


    private List<SimpleGrantedAuthority> getAdminUserAuthority(Users users) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + users.getUserRole()));
    }

    private List<SimpleGrantedAuthority> getCashierAuthority(Users users) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + users.getUserRole()));
    }


}
