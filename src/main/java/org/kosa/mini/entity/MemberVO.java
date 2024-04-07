package org.kosa.mini.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberVO {
	
	private String member_id;
	private String member_pwd;
	private String member_name;
	private String member_address;
	private String member_phone_number;
	private String member_gender;
	
	public boolean isEqualsPwd(String pwd) {
		return this.member_pwd.equals(pwd);		
	}
}
