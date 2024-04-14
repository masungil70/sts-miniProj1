package org.kosa.mini.board;

import org.apache.ibatis.annotations.Mapper;
import org.kosa.mini.entity.BoardTokenVO;

@Mapper
public interface BoardTokenMapper {

	int insert(String board_token);
	int updateStatus(BoardTokenVO boardTokenVO);

}
