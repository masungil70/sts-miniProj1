package org.kosa.mini.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardVO {
	
	private String bno;
	private String btitle;
	private String bcontent;
	private String member_id;
	private String bdate;
	private String bViewCount;
	private String bwriter;
	
	//검색키
	private String searchKey;

}
