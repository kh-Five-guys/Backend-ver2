package com.finalproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.finalproject.dto.BoardDTO;
import com.finalproject.dto.CategorieDTO;
import com.finalproject.dto.CommentDTO;
import com.finalproject.dto.FileDTO;
import com.finalproject.mapper.BoardMapper;

@Service
public class BoardService {
	private BoardMapper mapper;

	public BoardService(BoardMapper mapper) {
		this.mapper = mapper;
	}
	
	// 게시판 목록 조회 =========================================================
	public List<BoardDTO> selectBoardList(int pageNo, int pageContentEa, String sortOrder, String searchType, String searchKeyword) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("pageNo", pageNo);
	    map.put("pageContentCount", pageContentEa);
	    map.put("sortOrder", sortOrder);
	    map.put("searchType", searchType);
	    map.put("searchKeyword", searchKeyword);
	    return mapper.selectBoardList(map);
	}

	// 게시글 수(vo)
	public int selectBoardTotalCount(String searchType, String searchKeyword) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("searchType", searchType);
	    map.put("searchKeyword", searchKeyword);
	    return mapper.selectBoardTotalCount(map);
	}
	
	
	// 카테고리별 게시판 목록 조회 =========================================================
	public List<BoardDTO> selectBoardListByCategory(int pageNo, int pageContentEa, Integer category, String sortOrder,
			String searchType, String searchKeyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("pageNo", pageNo);
		map.put("pageContentCount", pageContentEa);
		map.put("category", category);
		map.put("sortOrder", sortOrder);
		map.put("searchType", searchType);
		map.put("searchKeyword", searchKeyword);
		return mapper.selectBoardListByCategory(map);
	}

	// 카테고리별 게시글 수 조회
	public int selectBoardTotalCountByCategory(int category, String searchType, String searchKeyword) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("category", category);
	    map.put("searchType", searchType);
	    map.put("searchKeyword", searchKeyword);
	    return mapper.selectBoardTotalCountByCategory(map);
	}
	
	
	// 인기글 조회 =========================================================
	public List<BoardDTO> selectPopularBoardList(int pageNo, int pageContentEa, String sortOrder, String searchType, String searchKeyword, int minLikes) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("pageNo", pageNo);
	    map.put("pageContentCount", pageContentEa);
	    map.put("sortOrder", sortOrder);
	    map.put("searchType", searchType);
	    map.put("searchKeyword", searchKeyword);
	    map.put("minLikes", minLikes);
	    return mapper.selectPopularBoardList(map);
	}

	public int selectPopularBoardTotalCount(String sortOrder, String searchType, String searchKeyword, int minLikes) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("sortOrder", sortOrder);
	    map.put("searchType", searchType);
	    map.put("searchKeyword", searchKeyword);
	    map.put("minLikes", minLikes);
	    return mapper.selectPopularBoardTotalCount(map);
	}
	
	

	// 게시글 상세조회
	public BoardDTO selectBoard(int bno) {
		return mapper.selectBoard(bno);
	}

	// 게시글 댓글 조회
	public List<CommentDTO> selectCommentList(int bno) {
		return mapper.selectCommentList(bno);
	}

	// 게시글 조회수 증가
	public int updateBoardCount(int bno) {
		return mapper.updateBoardCount(bno);
	}


	// ========================== 게시글 좋아요/ 싫어요
	
	public int insertBoardLike(int bno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("id", userId);
	    map.put("bno", bno);
	    return mapper.insertBoardLike(map);
	}

	public int deleteBoardLike(int bno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("id", userId);
	    map.put("bno", bno);
	    return mapper.deleteBoardLike(map);
	}

	public int selectBoardLikeCount(int bno) {
	    return mapper.selectBoardLikeCount(bno);
	}

	public int insertBoardHate(int bno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("id", userId);
	    map.put("bno", bno);
	    return mapper.insertBoardHate(map);
	}

	public int deleteBoardHate(int bno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("id", userId);
	    map.put("bno", bno);
	    return mapper.deleteBoardHate(map);
	}

	public int selectBoardHateCount(int bno) {
	    return mapper.selectBoardHateCount(bno);
	}
	
	
	
	// ========================== 댓글 좋아요/ 싫어요
	public int insertCommentLike(int cno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("userId", userId);
	    map.put("cno", cno);
	    return mapper.insertCommentLike(map);
	}

	public int deleteCommentLike(int cno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("userId", userId);
	    map.put("cno", cno);
	    return mapper.deleteCommentLike(map);
	}

	public int selectCommentLikeCount(int cno) {
	    return mapper.selectCommentLikeCount(cno);
	}

	public int insertCommentHate(int cno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("userId", userId);
	    map.put("cno", cno);
	    return mapper.insertCommentHate(map);
	}

	public int deleteCommentHate(int cno, String userId) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("userId", userId);
	    map.put("cno", cno);
	    return mapper.deleteCommentHate(map);
	}

	public int selectCommentHateCount(int cno) {
	    return mapper.selectCommentHateCount(cno);
	}
	
	// ========================== 댓글 추가
	public int insertComment(CommentDTO dto) {
		return mapper.insertComment(dto);
	}
	
	// ========================== 댓글 삭제
	public int deleteComment(int cno) {
		return mapper.deleteComment(cno);
	}
	
	// ========================== 카테고리 목록 조회
	public List<CategorieDTO> selectCategorieList() {
        return mapper.selectCategorieList();
    }

	public int getBoardNo() {
		return mapper.getBoardNo();
	}

	// 게시글 삽입
	public int insertBoard(BoardDTO dto) {
		System.out.println("서비스안에서 확인 : " + dto);
		return mapper.insertBoard(dto);
	}

	// 첨부파일
	public int insertFile(FileDTO fileDTO) {
		return mapper.insertFile(fileDTO);
	}

	public int getNextFileNo() {
		return mapper.getNextFileNo();
	}

	public List<FileDTO> selectFileList(int bno) {
        List<FileDTO> fileList = mapper.selectFileList(bno);

        for (FileDTO file : fileList) {
            String filePath = file.getPath();
            String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
            file.setFileName(fileName);
        }

        return fileList;
	}

	public FileDTO selectImageFile(int fno) {
		return mapper.selectImageFile(fno);
	}

	public FileDTO selectFile(int fno) {
		return mapper.selectFile(fno);
	}

	public int deleteBoard(int bno) {
		return mapper.deleteBoard(bno);
	}

	// 파일정보삭제(없애는 후보)
	public int deleteFileInfo(int fno) {
		return mapper.deleteFileInfo(fno);
		
	}

	// 게시글 업데이트
	public int updateBoard(BoardDTO dto) {
		return mapper.updateBoard(dto);
		
	}

	public int deleteBoardFileList(int boardNo, int[] fnoList) {
		return mapper.deleteBoardFileList(boardNo, fnoList);
		
	}

	public List<BoardDTO> selectMainBoardList() {
		return mapper.selectMainBoardList();
	}

}
	
	
	

