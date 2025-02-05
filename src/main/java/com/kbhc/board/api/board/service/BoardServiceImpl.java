package com.kbhc.board.api.board.service;

import com.kbhc.board.api.board.dto.BoardRequest;
import com.kbhc.board.api.board.dto.BoardResponse;
import com.kbhc.board.api.board.entity.Board;
import com.kbhc.board.api.board.repository.BoardRepository;
import com.kbhc.board.api.user.entity.Member;
import com.kbhc.board.api.user.repository.MemberRepository;
import com.kbhc.board.core.model.Response;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.kbhc.board.core.enums.Message.*;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    @Value("${file.upload-dir}")
    private String filePath;

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    public BoardServiceImpl(BoardRepository boardRepository
            , MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public Response<Boolean> createBoard(BoardRequest request, MultipartFile file, String writerEmail) throws Exception {

        Board board = new Board(request);

        Member member = memberRepository.findByEmail(writerEmail);

        if (Objects.isNull(member)) {
            return Response.failure(FAILURE_USER_NOT_FOUND.getValue());
        }

        board.setMember(member);

        if (Objects.nonNull(file) && !file.isEmpty()) {

            String fileName = null;
            try {
                fileName = this.saveFile(file);
            } catch (IOException e) {
                log.error("파일 저장 실패.", e);
                Response.failure(FAILURE_BOARD_SAVE.getValue());
            }

            board.setFilePath(filePath);
            board.setFileName(fileName);
        }

        boardRepository.save(board);

        return Response.success(SUCCESS_BOARD_SAVE.getValue(), true);
    }

    private String saveFile(MultipartFile file) throws Exception {
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + "_" + file.getOriginalFilename();
        File uploadFile = new File(filePath, fileName);

        try {
            if (!uploadFile.exists()) {
                boolean isDirCreated = uploadFile.mkdirs();

                if (!isDirCreated) {
                    throw new IOException("디렉토리 생성 실패");
                }
            }

            file.transferTo(uploadFile);
        } catch (IOException e) {
            throw new IOException("파일 저장 중 오류 발생", e);
        }

        return fileName;
    }

    @Override
    @Cacheable(value = "boardCache", key = "#boardId", cacheManager = "cacheManager")
    public Response<BoardResponse> findBoard(Long boardId) throws Exception {

        Board board = boardRepository.findById(boardId).orElse(null);

        if (Objects.isNull(board)) {
            return Response.failure(FAILURE_BOARD_NOT_FOUND.getValue());
        }

        return Response.success(SUCCESS_BOARD_SEARCH.getValue(), new BoardResponse(board));
    }

    @Override
    @CachePut(value = "boardCache", key = "#id")
    public Response<BoardResponse> updateBoard(Long id, BoardRequest request, MultipartFile file) throws Exception {
        Board board = boardRepository.findById(id).orElse(null);

        if (Objects.isNull(board)) {
            return Response.failure(FAILURE_BOARD_NOT_FOUND.getValue());
        }

        board.setTitle(request.getTitle());
        board.setContent(request.getContent());

        if (Objects.nonNull(file) && !file.isEmpty()) {

            this.deleteFile(board.getFileName());

            String fileName = null;

            try {
                 fileName = this.saveFile(file);
            } catch (IOException e) {
                log.error("파일 저장 실패.", e);
                Response.failure(FAILURE_BOARD_SAVE.getValue());
            }

            board.setFilePath(filePath);
            board.setFileName(fileName);
        }

        boardRepository.save(board);

        return Response.success(SUCCESS_BOARD_UPDATE.getValue(), new BoardResponse(board));
    }

    @Override
    @CacheEvict(value = "boardCache", key = "#id")
    public Response<Boolean> deleteBoard(Long id) throws Exception {

        Board board = boardRepository.findById(id).orElse(null);

        if (Objects.isNull(board)) {
            return Response.failure(FAILURE_BOARD_NOT_FOUND.getValue());
        }

        String fileName = board.getFileName();
        if (Objects.nonNull(fileName) && !fileName.isBlank()) {
            this.deleteFile(fileName);
        }

        boardRepository.delete(board);

        return Response.success(SUCCESS_BOARD_DELETE.getValue(), true);
    }

    private void deleteFile(String fileName) {
        File file = new File(filePath, fileName);

        if (file.exists()) {
            if (file.delete()) {
                log.info("파일 삭제 성공, {}", filePath + fileName);
            } else {
                log.info("파일 삭제 실패, {}", filePath + fileName);
            }
        }
    }


}
