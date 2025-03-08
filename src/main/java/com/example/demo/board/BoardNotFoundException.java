package com.example.demo.board;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException(String message) {
        super(message);
    }
}
