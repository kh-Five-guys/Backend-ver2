package com.finalproject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.finalproject.dto.BoardDTO;
import com.finalproject.dto.CategorieDTO;
import com.finalproject.dto.CommentDTO;
import com.finalproject.dto.FileDTO;

@Mapper
public interface BoardMapper {

	// 게시판 목록 조회
	List<BoardDTO> selectBoardList(Map<String, Object> map);

	// 게시글 수 조회(페이지vo)
	int selectBoardTotalCount(Map<String, Object> map);
	
	// 카테고리별 게시판 목록 출력
	List<BoardDTO> selectBoardListByCategory(Map<String, Object> map);

	// 카테고리별 게시판 수 출력
	int selectBoardTotalCountByCategory(Map<String, Object> map);

	// 인기글 게시판 목록 출력
	List<BoardDTO> selectPopularBoardList(Map<String, Object> map);

	// 인기글 게시판 수 출력
	int selectPopularBoardTotalCount(Map<String, Object> map);
	
	
	
	
	// 게시글 상세조회
	BoardDTO selectBoard(int bno);

	// 게시글 댓글 조회
	List<CommentDTO> selectCommentList(int bno);

	// 게시글 조회수 증가
	int updateBoardCount(int bno);
	
	// 게시글 좋아요/싫어요
	int insertBoardLike(Map<String, Object> map);
	int deleteBoardLike(Map<String, Object> map);
	int selectBoardLikeCount(int bno);
	int insertBoardHate(Map<String, Object> map);
	int deleteBoardHate(Map<String, Object> map);
	int selectBoardHateCount(int bno);
	
	// 댓글 좋아요/싫어요
	int insertCommentLike(Map<String, Object> map);
    int deleteCommentLike(Map<String, Object> map);
    int selectCommentLikeCount(int cno);
    int insertCommentHate(Map<String, Object> map);
    int deleteCommentHate(Map<String, Object> map);
    int selectCommentHateCount(int cno);
    
    // 댓글 추가
    int insertComment(CommentDTO dto);
    
    // 댓글 삭제
    int deleteComment(int cno);
    
    
    // 카테고리 목록 출력
    List<CategorieDTO> selectCategorieList();

    
    // 게시글 번호 가져오기
	int getBoardNo();

	// 게시글 작성
	int insertBoard(BoardDTO dto);

	int insertFile(FileDTO fileDTO);

	int getNextFileNo();

	List<FileDTO> selectFileList(int bno);

	// 없애는거 후보임
	FileDTO selectImageFile(int fno);

	FileDTO selectFile(int fno);

	int deleteBoard(int bno);

	int deleteFileInfo(int fno);

	int updateBoard(BoardDTO dto);

	int deleteBoardFileList(int boardNo, int[] fnoList);

	List<BoardDTO> selectMainBoardList();


    

    
    


    
    
	
}
