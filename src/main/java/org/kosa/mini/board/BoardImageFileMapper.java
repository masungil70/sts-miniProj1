package org.kosa.mini.board;

import org.apache.ibatis.annotations.Mapper;
import org.kosa.mini.entity.BoardImageFileVO;

@Mapper
public interface BoardImageFileMapper {

	int insert(BoardImageFileVO boardImageFileVO);
	BoardImageFileVO findById(String board_image_file_id);
	int updateBoardNo(BoardImageFileVO boardImageFileVO);
	
}
