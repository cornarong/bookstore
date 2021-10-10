package com.mytoy.bookstore.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 내장타입 선언
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
