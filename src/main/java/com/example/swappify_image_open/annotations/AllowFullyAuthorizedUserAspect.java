package com.example.swappify_image_open.annotations;

import com.example.swappify_image_open.exceptions.CommandExecutor;
import com.example.swappifyauthconnector.connector.AuthConnector;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class AllowFullyAuthorizedUserAspect {

    private final AuthConnector authConnector;

    @Before("@annotation(AllowFullyAuthorizedUser)")
    public void checkAuth(JoinPoint joinPoint) {
        var token = joinPoint.getArgs()[1];
        var executor = new CommandExecutor<String>();
        Optional.ofNullable(token)
                .map(Object::toString)
                .ifPresent(it -> executor.execute(it, authConnector::checkAuthStatus));
    }
}
