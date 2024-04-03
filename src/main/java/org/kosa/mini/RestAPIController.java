package org.kosa.mini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/board")
public class RestAPIController {
	
	@GetMapping("/all")
	public ResponseEntity<List<BoardVO>> listBoard() {
		log.info("listBoard 메서드 호출");
		List<BoardVO> list = new ArrayList<BoardVO>();
		for (int i = 0; i < 10; i++) {
			BoardVO vo = new BoardVO();
			vo.setBno(String.valueOf(i));
			vo.setWriter("이순신"+i);
			vo.setTitle("안녕하세요"+i);
			vo.setContent("새 상품을 소개합니다."+i);
			list.add(vo);
		}
		
		return new ResponseEntity<List<BoardVO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{bno}", method = RequestMethod.GET)
	public ResponseEntity<BoardVO> findBoard (@PathVariable("bno") String bno) {
		log.info("findBoard 메서드 호출");
		BoardVO vo = new BoardVO();
		vo.setBno(bno);
		vo.setWriter("홍길동");
		vo.setTitle("안녕하세요");
		vo.setContent("홍길동 글입니다");
		return new ResponseEntity<BoardVO>(vo, HttpStatus.OK);
	}	
	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> addBoard (@RequestBody BoardVO boardVO) {
		ResponseEntity<String>  resEntity = null;
		try {
			log.info("addBoard 메서드 호출");
			log.info(boardVO.toString());
			resEntity = new ResponseEntity<String>("ADD_SUCCEEDED", HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}	
	
	//수정하기
	@RequestMapping(value = "/{bno}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateBoard (@PathVariable("bno") String bno, @RequestBody BoardVO boardVO) {
		ResponseEntity<String>  resEntity = null;
		try {
			log.info("updateBoard 메서드 호출");
			log.info(boardVO.toString());
			resEntity = new ResponseEntity<String>("MOD_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}
	
	//삭제하기
	@RequestMapping(value = "/{bno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBoard (@PathVariable("bno") String bno) {
		ResponseEntity<String>  resEntity = null;
		try {
			log.info("deleteBoard 메서드 호출");
			log.info(bno.toString());
			resEntity = new ResponseEntity<String>("REMOVE_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}	

		
}
