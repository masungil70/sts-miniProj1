package org.kosa.mini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class JsonController {
	
	//문자열 전달  
	@RequestMapping(value = "/json1")
	@ResponseBody
	public String json1() {
		return "Hello REST!!";
	}
	
	//map객체로 생성하여 json 문자열 전달   
	@RequestMapping(value = "/json2")
	@ResponseBody
	public Map<String, Object> json2() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "hong");
		map.put("name", "홍길동");
		return map;
	}

	//UserVO 객체를 json 문자열 전달   
	@RequestMapping(value = "/json3")
	@ResponseBody
	public UserVO json3() {
		return UserVO.builder().uid("hong").pwd("1004").name("홍길동").email("hong@naver.com").build();
	}
	
	//UserVO 객체를  배열로 생성하여 json 문자열 전달  
	@RequestMapping(value = "/json4")
	@ResponseBody
	public List<UserVO> json4() {
		List<UserVO> list = new ArrayList<UserVO>();
		for (int i=0;i<10;i++) {
			list.add(UserVO.builder().uid("hong" + i).pwd("1004").name("홍길동" + i).email("hong@naver.com").build());
		}
		return list;
	}
	
	//매개 변수로 JSON 객체로 전달받고, json 문자열 전달   
	@RequestMapping(value = "/json5")
	@ResponseBody
	public Map<String, Object> json5(@RequestBody Map<String, Object> map) {
		log.info("map = {} ", map.toString());
		return map;
	}
	
	//매개변수의 값을 경로로 전달하는 경우 예제  
	@RequestMapping(value= "/json/{id}")
	@ResponseBody
	public Map<String, Object> jsonPath(@PathVariable("id") String id) throws Exception {
		log.info("path = {} ", id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", "홍길동");
		map.put("age", 10);
		return map;
	}
	
	@RequestMapping(value = "/json11")
	public ResponseEntity<Map<String, Object>> json11() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "홍길동");
		map.put("age", 10);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}

		
}
