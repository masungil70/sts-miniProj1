package org.kosa.mini.code;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosa.mini.entity.BoardVO;
import org.kosa.mini.entity.CodeVO;
import org.kosa.mini.page.PageRequestVO;

@Mapper
public interface CodeMapper {

	List<CodeVO> getList();
}
