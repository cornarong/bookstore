package com.mytoy.bookstore.controller.api;

import com.mytoy.bookstore.service.BookService;
import com.mytoy.bookstore.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CrawlingApiController {

    private final BookService bookService;
    private final CrawlingService crawlingService;

    @PostMapping("/crawlingKorea")
    public void crawling(Authentication authentication) throws IOException {
        String uid = authentication.getName();
        crawlingService.crawling(uid);
    }
}
