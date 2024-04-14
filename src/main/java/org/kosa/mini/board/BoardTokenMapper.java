package org.kosa.mini.board;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardTokenMapper {

	int insert(String board_token);
	int updateStatusComplate(String board_token);

}
