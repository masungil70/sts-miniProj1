package org.kosa.mini.entity;

import org.springframework.web.multipart.MultipartFile;

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
	private String view_count;
	private String bwriter;
	
	//첨부파일 
	private MultipartFile file;
	
}
