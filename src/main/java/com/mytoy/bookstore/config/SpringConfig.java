package com.mytoy.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    /* 브라우저에서 지원하는 get or post타입 전송될 때 Form태그 아래 hidden 타입으로 지정해놓은 타입으로 변경되어 서버에서 받게된다 */
    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return hiddenHttpMethodFilter;
    }


/* 웹 보안상 내부 프로젝트 외 로컬 시스템에 접근이 불가능 하므로 루트에서 외부경로를 매핑하여 파일을 불러올 수 있도록 처리한다. */

    // 1. 뷰단에서 이곳 url 으로 접근하면~

    @Value("/profile/**")
    private String profileName;
    @Value("/thumbnail/**")
    private String thumbnailName;

    // 2. 이곳 물리경로 에서 파일을 읽어온다.

    // ## AWS 서버 ##
    @Value("file:/home/ec2-user/bookstore/profile/")
    private String profilePath;
    @Value("file:/home/ec2-user/bookstore/thumbnail/")
    private String thumbnailPath;

    // ## 윈도우 로컬 ##
//    @Value("file:///D:/study/mytoy/profile_image/")
//    private String profilePath;
//    @Value("file:///D:/study/mytoy/thumbnail_image/")
//    private String thumbnailPath;

    // 3. 핸들러, 로케이션 설정.

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(profileName)
                .addResourceLocations(profilePath);
        registry.addResourceHandler(thumbnailName)
                .addResourceLocations(thumbnailPath);
    }
}
