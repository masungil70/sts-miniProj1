package org.kosa.mini.board;

import org.apache.ibatis.annotations.Mapper;
import org.kosa.mini.entity.BoardImageFileVO;
import org.kosa.mini.entity.BoardVO;

@Mapper
public interface BoardImageFileMapper {

	int insert(BoardImageFileVO boardImageFileVO);
	BoardImageFileVO findById(String board_image_file_id);
	int updateBoardNo(BoardVO board);
	
}
