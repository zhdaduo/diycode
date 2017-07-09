package com.example.bill.delta.bean.user.event;


import com.example.bill.delta.bean.user.Token;

public class RefreshTokenEvent {
    private Token token;

    public RefreshTokenEvent(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
