package com.example.demo.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
  // 글 작성자 id refernece 걸어서 가지고 오기

    private String title;

    private String content;

    private String filename;

    private String filepath;

    public void setFilename(String fileName) {
    }
}
