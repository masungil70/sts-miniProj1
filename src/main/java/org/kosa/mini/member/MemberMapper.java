package org.kosa.mini.member;

import org.apache.ibatis.annotations.Mapper;
import org.kosa.mini.entity.MemberVO;

@Mapper
public interface MemberMapper {

	MemberVO login(MemberVO boardVO);

}
