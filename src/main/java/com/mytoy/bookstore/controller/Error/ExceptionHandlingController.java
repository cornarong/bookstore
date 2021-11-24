package com.mytoy.bookstore.controller.Error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class ExceptionHandlingController implements ErrorController {

    // 에러 페이지 정의
    private final String ERROR_400_PAGE_PATH = "/error/400";
    private final String ERROR_403_PAGE_PATH = "/error/403";
    private final String ERROR_404_PAGE_PATH = "/error/404";
    private final String ERROR_500_PAGE_PATH = "/error/500";
    private final String ERROR_505_PAGE_PATH = "/error/505";
    private final String ERROR_ETC_PAGE_PATH = "/error/error";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, Model model) {

        // 에러 코드를 획득한다.
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // 에러 코드에 대한 상태 정보
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));

        if (status != null) {
            // HttpStatus와 비교해 페이지 분기를 나누기 위한 변수
            int statusCode = Integer.valueOf(status.toString());

            // 로그로 상태값을 기록 및 출력
            log.info("httpStatus : " + statusCode);

            // 400 error
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return ERROR_400_PAGE_PATH;
            }

            // 403 error
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return ERROR_403_PAGE_PATH;
            }

            // 404 error
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ERROR_404_PAGE_PATH;
            }

            // 500 error
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ERROR_500_PAGE_PATH;
            }

            // 505 error
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ERROR_505_PAGE_PATH;
            }
        }

        // 정의한 에러 외 모든 에러는 error/error 페이지로 보낸다.
        return ERROR_ETC_PAGE_PATH;
    }
}
