package com.example.demo.board;

import com.example.demo.login.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private LoginRepository loginRepository;

    //글 작성 처리
    public void write(Board board, MultipartFile file, String email) throws Exception {

        String projectPath = "/home/ec2-user/images";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        System.out.println("filename ==>");

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setUser(email);
        String name = loginRepository.findByEmail(email).get().getName().toString();
        board.setWriter(name);

        //board.setFilename(imgURL);
        board.setFilepath("/home/ec2-user/images/" + fileName);

        boardRepository.save(board);

    }


    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }


    //특정한 게시글 불러오기
    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }


    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
