package org.kosa.mini.board;

import java.util.List;

import org.kosa.mini.entity.BoardVO;
import org.kosa.mini.page.PageRequestVO;
import org.kosa.mini.page.PageResponseVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * MVC 
 * Model : B/L 로직을 구현하는 부분(service + dao)  
 * View  : 출력(jsp) 
 * Controller : model와 view에 대한 제어를 담당 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
	private static final long serialVersionUID = 1L;
      
	private final BoardMapper  boardMapper;

    public PageResponseVO<BoardVO> getList(PageRequestVO pageRequestVO) {
    	List<BoardVO> list = boardMapper.getList(pageRequestVO);
        int total = boardMapper.getTotalCount(pageRequestVO);
        
        log.info("list {} ", list);
        log.info("total  = {} ", total);

        PageResponseVO<BoardVO> pageResponseVO = PageResponseVO.<BoardVO>withAll()
                .list(list)
                .total(total)
                .size(pageRequestVO.getSize())
                .pageNo(pageRequestVO.getPageNo())
                .build();

        return pageResponseVO;
	}
	
	public BoardVO view(BoardVO board)  {
		//view Count의 값을 증가한다. 
		//만약 값을 증가 하지 못하면 게시물이 존재하지 않는 경우임  
		if (0 == boardMapper.incViewCount(board)) {
			return null; 
		}
		//view Count의 값이 증가된 객체를 얻는다
		BoardVO resultVO = boardMapper.view(board);
		log.info(resultVO.getView_count());
		log.info(resultVO.toString());
		return resultVO;
	}
	
	public int delete(BoardVO board)  {
		return boardMapper.delete(board);
	}

	
	public BoardVO updateForm(BoardVO board)  {
		return boardMapper.view(board);
	}
	
	public int update(BoardVO board) {
		return boardMapper.update(board);
	}
	
	public int insert(BoardVO board)  {
		return boardMapper.insert(board);
	}
	
}











