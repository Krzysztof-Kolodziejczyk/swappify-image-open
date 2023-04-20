package com.example.swappify_image_open.annotations;

import com.example.swappify_image_open.exceptions.ConstraintViolationException;
import com.example.swappify_image_open.exceptions.ExceptionMessages;
import com.example.swappify_image_open.utils.Headers;
import com.example.swappifyauthconnector.connector.AuthConnector;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
        Optional.ofNullable(token).map(Object::toString)
                .ifPresentOrElse(authConnector::checkAuthStatus,
                        () -> {
                            throw new ConstraintViolationException(ExceptionMessages.AUTHORIZATION_HEADER_NOT_FOUND);
                        });
    }
}
