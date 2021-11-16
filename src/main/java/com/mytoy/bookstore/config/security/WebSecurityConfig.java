package com.mytoy.bookstore.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


    // 정적 자원 관리 (WebIgnore 설정) -> 보안필터를 거치지 않는다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 사용자 정의 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF : 이용자가 의도하지 하지 않은 요청을 하도록 함 (설정 해제)
            .authorizeRequests()
                // 구체적인 경로가 먼저 오고 그것보다 큰 범위의 경로가 뒤에 오도록 한다.
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/","/account/**", "/api/**", "/board/**", "/store/**", "/files/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/account/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            // * usersByUsernameQuery : 사용자 목록을 가져오는 쿼리, 테이블명이나 컬럼명 등은 자유이나 출력 결과의 컬럼은 3개여야 하며 [사용자이름], [비밀번호], [Enabled]를 순서대로 지정
            .usersByUsernameQuery(" select uid, password, enabled "
                    + "from user "
                    + "where uid = ?")
            // * authoritiesByUsernameQuery : 권한을 불러오는 쿼리, 출력 결과의 컬럼은 2개여야 하며 [사용자이름], [권한명]를 순서대로 지정
            .authoritiesByUsernameQuery("select u.uid, r.name "
                    + "from user_role ur inner join user u on ur.user_id = u.id "
                    + "inner join role r on ur.role_id = r.id "
                    + "where u.uid = ?");
    }

    // Password 암호화 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}