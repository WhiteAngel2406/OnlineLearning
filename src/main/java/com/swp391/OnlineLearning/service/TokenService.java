package com.swp391.OnlineLearning.service;

import com.swp391.OnlineLearning.model.Token;
import com.swp391.OnlineLearning.model.User;

public interface TokenService {
    static final int TOKEN_VALIDITY_MINUTES = 60;
    Token create(User newUser);

    void save(Token newToken);

    Token checkValidToken(String token);

    void delete(Token token);
}
