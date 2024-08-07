package com.finalproject.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
//import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource; // Resource import 추가
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders; // HttpHeaders import 추가
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.finalproject.dto.BoardDTO;
import com.finalproject.dto.CategorieDTO;
import com.finalproject.dto.CommentDTO;
import com.finalproject.dto.FileDTO;
import com.finalproject.dto.UserDTO;
import com.finalproject.service.BoardService;
import com.finalproject.vo.BoardPaggingVo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardController {
    private final BoardService service;
    
    // 인기글 추천수 컷
    private int minLikes=5;
    
    // 메인페이지에 보여줄 인기글 수
    private int mainBoardCount = 100;

    public BoardController(BoardService service) {
        this.service = service;
    }


    @GetMapping("/board/list")
    public ResponseEntity<Map<String, Object>> selectBoardList(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageContentEa,
            @RequestParam(required = false) Integer category,
            @RequestParam(defaultValue = "date") String sortOrder,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(defaultValue = "false") boolean popular) {  // 인기글 여부 파라미터 추가

    	System.out.println("타입 : " + searchType);
        List<BoardDTO> boardList;
        int totalCount;
        

        if (popular) {
            boardList = service.selectPopularBoardList(pageNo, pageContentEa, sortOrder, searchType, searchKeyword, minLikes);
            totalCount = service.selectPopularBoardTotalCount(sortOrder, searchType, searchKeyword, minLikes);
        } else if (category != null) {
            boardList = service.selectBoardListByCategory(pageNo, pageContentEa, category, sortOrder, searchType, searchKeyword);
            totalCount = service.selectBoardTotalCountByCategory(category, searchType, searchKeyword);
        } else {
            boardList = service.selectBoardList(pageNo, pageContentEa, sortOrder, searchType, searchKeyword);
            totalCount = service.selectBoardTotalCount(searchType, searchKeyword);
        }

        BoardPaggingVo vo = new BoardPaggingVo(totalCount, pageNo, pageContentEa);

        Map<String, Object> response = new HashMap<>();
        response.put("list", boardList);
        response.put("pagging", vo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @GetMapping("/board/mainlist")
    public ResponseEntity<Map<String, Object>> selectMainBoardList() {
        List<BoardDTO> boardList = service.selectMainBoardList(); // 상위 100개 가져오기
        
        System.out.println(boardList); // 디버깅 용도

        Map<String, Object> response = new HashMap<>();
        response.put("list", boardList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    

    
    // 게시글 상세조회
    @GetMapping("/board/{bno}")
    public ResponseEntity<Map<String, Object>> boardView(@PathVariable int bno, HttpSession session) {
        
        // 글번호에 해당하는 게시글 조회
        BoardDTO dto = service.selectBoard(bno);
        
        // 해당 게시글의 댓글 조회 
        List<CommentDTO> commentList = service.selectCommentList(bno);
        
        // 게시글 조회수 업데이트
        HashSet<Integer> set = (HashSet<Integer>) session.getAttribute("history");
        
        // 파일 리스트
        List<FileDTO> fileList = service.selectFileList(bno);
        System.out.println(fileList);
        
        // 세션에 방문한 게시글 목록이 없을 때
        if(set == null) {
            set = new HashSet<Integer>();
            session.setAttribute("history", set);
        }
        if(set.add(bno)) {
            service.updateBoardCount(bno);
        }
        
        // Map을 사용하여 데이터를 반환
        Map<String, Object> response = new HashMap<>();
        response.put("board", dto);
        response.put("commentList", commentList);
        
        System.out.println(fileList);
        response.put("fileList", fileList );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @GetMapping("/download/{fno}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int fno) throws UnsupportedEncodingException {
        // 파일 정보 읽어옴
        System.out.println("fno 확인 : " + fno);
        FileDTO dto = service.selectFile(fno);

        // 파일 경로에서 파일 이름 추출
        String filePath = dto.getPath();
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);

        // UUID 제거 로직 추가
        if (fileName.contains("_")) {
            fileName = fileName.substring(fileName.indexOf("_") + 1);
        }
        dto.setFileName(fileName); // 파일 이름 설정

        System.out.println("dto 확인 : " + dto);
        System.out.println("이름확인 : " + dto);

        try {
            File file = new File(dto.getPath());
            Resource resource = new UrlResource(file.toURI());

            // 파일 이름을 URL 인코딩
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName)
                        .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    
    
    
    


    @PostMapping("/board/write")
    public ResponseEntity<String> boardWrite(@RequestParam("boardTitle") String boardTitle,
                                             @RequestParam("boardContent") String boardContent,
                                             @RequestParam("userId") String userId,
                                             @RequestParam("categorieNo") int categorieNo,
                                             @RequestParam(value = "file", required = false) MultipartFile[] files,
                                             HttpSession session) throws IllegalStateException, IOException {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        BoardDTO dto = new BoardDTO();
        dto.setBoardTitle(boardTitle);
        dto.setBoardContent(boardContent);
        dto.setUserId(userId);
        dto.setCategorieNo(categorieNo);

        // 게시글 새번호 받아옴
        int bno = service.getBoardNo();
        dto.setBoardNo(bno);
        // 해당 게시글 DB에 등록
        service.insertBoard(dto);

        // 파일 업로드 처리
        handleFileUpload(dto.getBoardNo(), files);
        return new ResponseEntity<>("글이 성공적으로 작성되었습니다.", HttpStatus.OK);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // 게시글 삭제
    @DeleteMapping("/board/delete/{bno}")
    public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable int bno, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(Map.of("status", "fail", "message", "로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        BoardDTO board = service.selectBoard(bno);
        if (board == null || !board.getUserId().equals(user.getUserId())) {
            return new ResponseEntity<>(Map.of("status", "fail", "message", "작성자만 삭제할 수 있습니다."), HttpStatus.FORBIDDEN);
        }

        // 첨부파일 삭제 로직 추가
        List<FileDTO> fileList = service.selectFileList(bno);
        for (FileDTO fileDTO : fileList) {
            File file = new File(fileDTO.getPath());
            if (file.exists()) {
                file.delete();
            }
        }

        service.deleteBoard(bno);

        return new ResponseEntity<>(Map.of("status", "success", "message", "게시글이 삭제되었습니다."), HttpStatus.OK);
    }
    
    
    
    // 이미지 업로드
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("upload") MultipartFile upload) {
        String fileUrl = handleFileUpload(upload);

        if (fileUrl != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("url", fileUrl);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            File file = new File("c:\\fileupload\\" + filename);
            Resource resource = new UrlResource(file.toURI());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/file/delete")
    public ResponseEntity<?> deleteFiles(@RequestBody List<Integer> fileNos) {
        try {
            for (int fno : fileNos) {
                service.deleteFileInfo(fno);
            }
            return ResponseEntity.ok().body(Map.of("status", "success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PostMapping("/board/update/{bno}")
    public ResponseEntity<String> updateBoard(@PathVariable int bno,
                                              @RequestParam("boardTitle") String boardTitle,
                                              @RequestParam("boardContent") String boardContent,
                                              @RequestParam("userId") String userId,
                                              @RequestParam("categorieNo") int categorieNo,
                                              @RequestParam(value = "file", required = false) MultipartFile[] files,
                                              @RequestParam(value = "dfile", required = false) int[] fnoList,
                                              HttpSession session) throws IllegalStateException, IOException {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        BoardDTO board = service.selectBoard(bno);
        if (board == null || !board.getUserId().equals(user.getUserId())) {
            return new ResponseEntity<>("작성자만 수정할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        board.setBoardTitle(boardTitle);
        board.setBoardContent(boardContent);
        board.setCategorieNo(categorieNo);
        service.updateBoard(board);

        System.out.println("삭제할 파일리스트 : " + Arrays.toString(fnoList));
        handleFileDeletion(bno, fnoList);
        handleFileUpload(bno, files);

        return new ResponseEntity<>("글이 성공적으로 수정되었습니다.", HttpStatus.OK);
    }

    // 파일 처리 관련 메서드들
    private void handleFileUpload(int boardNo, MultipartFile[] files) throws IOException {
        if (files != null) {
            String uploadDir = "c:\\fileupload";
            File root = new File(uploadDir);
            if (!root.exists()) {
                root.mkdirs();
            }

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    int fno = service.getNextFileNo();
                    String uuidFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    File destinationFile = new File(uploadDir, uuidFileName);
                    file.transferTo(destinationFile);
                    FileDTO fileDTO = new FileDTO(destinationFile, boardNo, fno);
                    service.insertFile(fileDTO);
                }
            }
        }
    }

    private String handleFileUpload(MultipartFile upload) {
        try {
            String uploadDir = "c:\\fileupload";
            File root = new File(uploadDir);
            if (!root.exists()) {
                root.mkdirs();
            }

            String fileName = UUID.randomUUID().toString() + "_" + upload.getOriginalFilename();
            File file = new File(uploadDir, fileName);
            upload.transferTo(file);
            return "http://localhost:9999/files/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleFileDeletion(int boardNo, int[] fnoList) {
        if (fnoList != null) {
            List<FileDTO> deleteFileList = service.selectFileList(boardNo);
            for (int fno : fnoList) {
                deleteFileList.stream()
                        .filter(fileDTO -> fileDTO.getFno() == fno)
                        .findFirst()
                        .ifPresent(fileDTO -> {
                            File file = new File(fileDTO.getPath());
                            if (file.exists()) {
                                file.delete();
                            }
                        });
            }
            service.deleteBoardFileList(boardNo, fnoList);
        }
    }

    
    
    
    

    
    
    
    
    
    
    
    
    // 게시글 좋아요
    @GetMapping("/boardLike/{bno}")
    public ResponseEntity<Map<String, Object>> boardLike(@PathVariable int bno, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            map.put("code", 2);
            map.put("msg", "로그인 하셔야 이용하실수 있습니다.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED); // 401 Unauthorized 반환
        } else {
            try {
                service.insertBoardLike(bno, user.getUserId());
                map.put("msg", "해당 게시글에 좋아요 하셨습니다.");
            } catch (Exception e) {
                service.deleteBoardLike(bno, user.getUserId());
                map.put("msg", "해당 게시글에 좋아요를 취소하셨습니다.");
            }
            map.put("code", 1);
        }
        int count = service.selectBoardLikeCount(bno);
        map.put("count", count);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 게시글 싫어요
    @GetMapping("/boardHate/{bno}")
    public ResponseEntity<Map<String, Object>> boardHate(@PathVariable int bno, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            map.put("code", 2);
            map.put("msg", "로그인 하셔야 이용하실수 있습니다.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED); // 401 Unauthorized 반환
        } else {
            try {
                service.insertBoardHate(bno, user.getUserId());
                map.put("msg", "해당 게시글에 싫어요 하셨습니다.");
            } catch (Exception e) {
                service.deleteBoardHate(bno, user.getUserId());
                map.put("msg", "해당 게시글에 싫어요를 취소하셨습니다.");
            }
            map.put("code", 1);
        }
        int count = service.selectBoardHateCount(bno);
        map.put("count", count);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    
    // 댓글 좋아요
    @GetMapping("/commentLike/{cno}")
    public ResponseEntity<Map<String, Object>> commentLike(@PathVariable int cno, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            map.put("code", 2);
            map.put("msg", "로그인 하셔야 이용하실 수 있습니다.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        } else {
            try {
                service.insertCommentLike(cno, user.getUserId());
                map.put("msg", "해당 댓글에 좋아요 하셨습니다.");
            } catch (Exception e) {
                service.deleteCommentLike(cno, user.getUserId());
                map.put("msg", "해당 댓글에 좋아요를 취소하셨습니다.");
            }
            map.put("code", 1);
        }
        int count = service.selectCommentLikeCount(cno);
        map.put("count", count);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 댓글 싫어요
    @GetMapping("/commentHate/{cno}")
    public ResponseEntity<Map<String, Object>> commentHate(@PathVariable int cno, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            map.put("code", 2);
            map.put("msg", "로그인 하셔야 이용하실 수 있습니다.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        } else {
            try {
                service.insertCommentHate(cno, user.getUserId());
                map.put("msg", "해당 댓글에 싫어요 하셨습니다.");
            } catch (Exception e) {
                service.deleteCommentHate(cno, user.getUserId());
                map.put("msg", "해당 댓글에 싫어요를 취소하셨습니다.");
            }
            map.put("code", 1);
        }
        int count = service.selectCommentHateCount(cno);
        map.put("count", count);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    
    // 댓글 추가
    @PostMapping("/comment/add")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO dto, HttpSession session) {
    	System.out.println(dto);
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        dto.setUserId(user.getUserId());
        try {
            service.insertComment(dto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그에 오류를 출력합니다.
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    // 댓글 삭제
    @DeleteMapping("/comment/delete")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteComment(@RequestParam int cno, @RequestParam int bno) {
        int result = service.deleteComment(cno);
        System.out.println(result);

        Map<String, String> response = new HashMap<>();
        if (result > 0) {
            response.put("status", "success");
        } else {
            response.put("status", "fail");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    
    // 카테고리 선택
    @GetMapping("/categories")
    public ResponseEntity<List<CategorieDTO>> getCategories() {
        List<CategorieDTO> categories = service.selectCategorieList();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
       
}