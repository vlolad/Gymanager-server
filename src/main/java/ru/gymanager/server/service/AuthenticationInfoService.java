package ru.gymanager.server.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@Slf4j
public class AuthenticationInfoService {

    public static String getCurrentUserLogin() {
        try {
            return getAuthUser().getName();
        } catch (AuthenticationException e) {
            throw new RuntimeException("Can't find current user;");
        }
    }

    //TODO handler
    private static Authentication getAuthUser() throws AuthenticationException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null || StringUtils.isBlank(currentUser.getName())) {
            throw new AuthenticationException("Current user didn't authenticate!");
        }
        return currentUser;
    }
}
