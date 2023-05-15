package com.example.swappify_image_open.exceptions;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class CommandExecutor<T> {

    public void execute(T arg, Consumer<T> consumer) {
        try {
            consumer.accept(arg);
        }catch (FeignException ex){
            throw new AuthViolationException();
        }
    }
}
