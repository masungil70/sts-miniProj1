package org.kosa.mini.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kosa.mini.board.BoardVO;
import org.kosa.mini.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTests {

    @Autowired(required = false)
    private BoardMapper boardMapper;

    @Test
    public void testClearAll() {
    	boardMapper.allDelete();
    }

    @Test
    public void testCreateSampleData() {
    	for (int i=0;i<200;i++) {
	    	BoardVO boardVO = BoardVO.builder()
	    			.btitle("게시물 제목 " + i)
	    			.bcontent("게시물 내용 " + i)
	    			.build();
	    	
	    	if (i % 2 == 0) {
	    		boardVO.setMember_id("msa1");
	    	} else {
	    		boardVO.setMember_id("msa2");
	    	}
	    	
	    	boardMapper.insert(boardVO);
    	}
    }
}

