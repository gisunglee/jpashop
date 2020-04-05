package jpabook.jpashop.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BookForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public BookForm() {
    }
}
