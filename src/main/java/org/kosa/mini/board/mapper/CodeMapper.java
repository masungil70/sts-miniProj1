package org.kosa.mini.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa.mini.board.BoardVO;
import org.kosa.mini.board.CodeVO;
import org.kosa.mini.board.PageRequestVO;

@Mapper
public interface CodeMapper {

	List<CodeVO> getList();
}
