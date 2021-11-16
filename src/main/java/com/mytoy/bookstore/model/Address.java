package com.mytoy.bookstore.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 내장타입 선언
@Getter
public class Address {

    private String postcode;
    private String address;
    private String detailAddress;

    protected Address(){

    }

    public Address(String postcode, String address, String detailAddress) {
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
