package com.example.bill.delta.bean.user.event;


import com.example.bill.delta.bean.user.Token;

public class TokenEvent {
    private Token token;

    public TokenEvent(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
