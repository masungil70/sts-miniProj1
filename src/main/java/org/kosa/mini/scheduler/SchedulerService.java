package org.kosa.mini.scheduler;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kosa.mini.board.BoardImageFileMapper;
import org.kosa.mini.board.BoardTokenMapper;
import org.kosa.mini.entity.BoardImageFileVO;
import org.kosa.mini.entity.BoardTokenVO;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class SchedulerService {
	private final BoardTokenMapper  	 boardTokenMapper;
	private final BoardImageFileMapper  boardImageFileMapper;

	
	@Scheduled(fixedDelay = 60000) // 60초마다 실행
	public void run2() {
		log.info("60초마다 실행");
	}
	
	//Cron 표현식을 사용하여 작업을 예약할 수 있다.
	/*
	첫 번째 * 부터
	초(0-59)
	분(0-59)
	시간(0-23)
	일(1-31)
	월(1-12)
	요일(0-6) (0: 일, 1: 월, 2:화, 3:수, 4:목, 5:금, 6:토)
	Spring @Scheduled cron은 6자리 설정만 허용하며 연도 설정을 할 수 없다.
	
	Cron 표현식 :
		* : 모든 조건(매시, 매일, 매주처럼 사용)을 의미
		? : 설정 값 없음 (날짜와 요일에서만 사용 가능)
		- : 범위를 지정할 때
		, : 여러 값을 지정할 때
		/ : 증분값, 즉 초기값과 증가치 설정에 사용
		L : 마지막 - 지정할 수 있는 범위의 마지막 값 설정 시 사용 (날짜와 요일에서만 사용 가능)
		W : 가장 가까운 평일(weekday)을 설정할 때
	 */
	// 매일 오전 04시에 실행
	@Scheduled(cron = "0 0 04 * * *") 
	public void run3() {
		log.info("매일 오전 04시에 실행");
	}
	 
	// 매달 10일,20일 04시에 실행
	@Scheduled(cron = "0 0 04 10,20 * ?") 
	public void run4() {
		log.info("매달 10일,20일 04시에 실행");
	}
	 
	// 매달 마지막날 04시에 실행
	@Scheduled(cron = "0 0 04 L * ?") 
	public void run5() {
		log.info("매달 마지막날 04시에 실행");
	}
	 
	// 1시간 마다 실행 ex) 01:00, 02:00, 03:00 ...
	@Scheduled(cron = "0 0 0/1 * * *") 
	public void run6() {
		log.info("1시간 마다 실행");
	}
	 
	// 매일 9시00분-9시55분, 18시00분-18시55분 사이에 5분 간격으로 실행
	@Scheduled(cron = "0 0/5 9,18 * * *") 
	public void run7() {
		log.info("매일 9시00분-9시55분, 18시00분-18시55분 사이에 5분 간격으로 실행");
	}
	 
	// 매일 9시00분-18시55분 사이에 5분 간격으로 실행
	@Scheduled(cron = "0 0/5 9-18 * * *") 
	public void run8() {
		log.info("매일 9시00분-18시55분 사이에 5분 간격으로 실행");
	}
	 
	// 매달 1일 10시30분에 실행
	@Scheduled(cron = "0 30 10 1 * *") 
	public void run9() {
		log.info("// 매달 1일 10시30분에 실행");
	}
	 
	// 매년 3월내 월-금 04시30분에 실행
	@Scheduled(cron = "0 30 04 ? 3 1-5") 
	public void run10() {
		log.info("매년 3월내 월-금 04시30분에 실행");
	}
	 
	// 매달 마지막 토요일 10시30분에 실행
	@Scheduled(cron = "0 30 10 ? * 6L") 
	public void run11() {
		log.info("매달 마지막 토요일 10시30분에 실행");
	}
	
	@Scheduled(fixedDelay = 60000) // 60초마다 실행 
	public void fileTokenAutoDelete() {
		System.out.println("첨부 파일 업로드 중 완료되지 않음 파일을 삭제한다");
		//현재 30분 전에 생성되고 임시 상태인 token 목록을 얻는다 
		List<BoardTokenVO> boardTokenList = boardTokenMapper.listToken();
		if (boardTokenList.size() != 0) {
			log.info("fileTokenList : " + boardTokenList);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", boardTokenList);
			log.info("map : " + map);

			//board_token을 기준으로 BoardImageFile 목록을 얻는다 
			List<BoardImageFileVO> boardImageFileList = boardImageFileMapper.getBoardImageFileList(map);
			for (BoardImageFileVO fileUpload : boardImageFileList) {
				log.info("삭제 파일 : " + fileUpload.getReal_filename());
				//저장소에 저장된 파일을 삭제한다 
				new File(fileUpload.getReal_filename()).delete();
			}
			if (boardImageFileList.size() != 0) {
				//게시물 내용에 추가된 이미지 정보를 삭제한다
				boardImageFileMapper.deleteBoardToken(map);
			}
			//임시로 사용된 게시물 토큰을 삭제한다
			boardTokenMapper.deletes(map);
		}
	}
	
}
