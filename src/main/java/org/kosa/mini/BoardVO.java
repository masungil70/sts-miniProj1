package org.kosa.mini;

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
	private String writer;
	private String title;
	private String content;

}
