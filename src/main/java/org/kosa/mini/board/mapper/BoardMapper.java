package org.kosa.mini.board.mapper;

import java.util.List;

import org.kosa.mini.board.BoardVO;

public interface BoardMapper {

	List<BoardVO> list(BoardVO boardVO);
	BoardVO read(BoardVO boardVO);
	int delete(BoardVO boardVO);

}
