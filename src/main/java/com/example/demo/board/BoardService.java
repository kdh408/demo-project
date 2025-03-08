package com.example.demo.board;

import com.example.demo.login.LoginRepository;
import com.example.demo.login.PrincipalDetails;
//import com.example.demo.board.BoardNotFoundException;

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
    public void write(Board board, MultipartFile file) throws Exception {

        //PrincipalDetails principalDetails=new PrincipalDetails();

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        System.out.println("filename ==>");

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        //board.setUser(loginRepository.findByEmail(principalDetails.getUsername()));

        boardRepository.save(board);

    }


    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }


    //특정한 게시글 불러오기
    public Board boardView(Integer id) {

        //return boardRepository.findById(id).get();
        return boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("Board with ID " + id + " not found"));

    }

    public void update(Board board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        System.out.println("filename ==>");

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);

    }

    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
