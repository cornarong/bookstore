package com.mytoy.bookstore.config.security;


import com.mytoy.bookstore.service.RoleHierarchyService;
import com.mytoy.bookstore.service.impl.RoleHierarchyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

/*@Component
@RequiredArgsConstructor
public class SecurityInitializer implements ApplicationRunner {

    @Autowired
    private final RoleHierarchyService roleHierarchyService;

    @Autowired
    private final RoleHierarchyImpl roleHierarchy;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String allHierarchy = roleHierarchyService.findAllHierarchy();
        roleHierarchy.setHierarchy(allHierarchy); // 어플리케이션 run 시점에 roleHierarchyService에서 포맷팅한 문자열을 셋팅해준다.

    }
}*/
