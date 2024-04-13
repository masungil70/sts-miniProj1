package org.kosa.mini.member.admin;

import org.apache.ibatis.annotations.Mapper;
import org.kosa.mini.entity.MemberVO;

@Mapper
public interface AdminMemberMapper {

	MemberVO login(MemberVO boardVO);

}
