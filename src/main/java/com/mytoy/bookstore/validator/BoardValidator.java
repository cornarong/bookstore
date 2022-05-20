package com.mytoy.bookstore.validator;

import com.mytoy.bookstore.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator { // 유효성 검사 커스텀
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz); // 실질적으로 클라이언트와 서버가 DTO로 데이터를 교환하니 DTO.clas를 사용해야한다.
    }

    @Override
    public void validate(Object target, Errors errors) {
        Board board = (Board)target; // 형변환
        if(StringUtils.isEmpty(board.getContent())) {
            errors.rejectValue("content", "key", "내용을 입력하세요.");
        }
    }
}
