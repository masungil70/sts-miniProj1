package org.kosa.mini.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa.mini.entity.BoardFileVO;
import org.kosa.mini.entity.BoardVO;
import org.kosa.mini.page.PageRequestVO;

@Mapper
public interface BoardFileMapper {

	List<BoardFileVO> getList(BoardVO boardVO);
	BoardFileVO view(BoardFileVO boardFileVO);
	BoardFileVO getBoardFileVO(BoardVO boardVO);
	int delete(BoardFileVO boardFileVO);
	int insert(BoardFileVO boardFileVO);

}
