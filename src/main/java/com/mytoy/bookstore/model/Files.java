package com.mytoy.bookstore.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
