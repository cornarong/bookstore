package com.mytoy.bookstore.config.security;

import com.mytoy.bookstore.model.RoleHierarchy;
import com.mytoy.bookstore.service.RoleHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.security.PermitAll;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /*
    *   스프링 시큐리티 규칙
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //.csrf().disable()// 테스트를 위해 잠시 설정해제.
            .authorizeRequests()
                .antMatchers("/admin/**").permitAll() // 구체적인 경로가 먼저 오고 그것보다 큰 범위의 경로가 뒤에 오도록 한다.
                .antMatchers("/manager/**").permitAll() // anyRole : 여러 권한 중 한개만 있어도..
                .antMatchers("/","/account/**", "/css/**", "/images/**", "/api/**", "/board/**", "/store/**").permitAll()
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

    // AuthenticationManagerBuilder 사용자 생성?
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            .usersByUsernameQuery(" select uid, password, enabled "
                    + "from user "
                    + "where uid = ?")
            .authoritiesByUsernameQuery("select u.uid, r.name "
                    + "from user_role ur inner join user u on ur.user_id = u.id "
                    + "inner join role r on ur.role_id = r.id "
                    + "where u.uid = ?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//  설정 오류 수정 필요.
//    @Bean
//    public

    public AccessDecisionManager affirmativeBased() {
        AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecisionVoters());
        return affirmativeBased;
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoter = Arrays.asList(roleVoter());
        accessDecisionVoter.add(roleVoter()); // Hieracchy된 voter가 파라미터로 들어온다.
        return accessDecisionVoter;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter() {
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        return roleHierarchy;
    }

}