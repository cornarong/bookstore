package com.mytoy.bookstore.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 내장타입 선언
@Getter
public class Address {

    private String abode;

    protected Address(){

    }

    public Address(String abode) {
        this.abode = abode;
    }
}
