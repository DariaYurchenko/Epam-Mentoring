package com.epam.exercises.sportbetting.generator;

public interface Generator<T> {

    String generate();

    void validate(String token, T t);

}
