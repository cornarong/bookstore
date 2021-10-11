package com.mytoy.bookstore.service.impl;

import com.mytoy.bookstore.model.RoleHierarchy;
import com.mytoy.bookstore.repository.RoleHierarchyRepository;
import com.mytoy.bookstore.service.RoleHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    @Autowired
    private RoleHierarchyRepository roleHierarchyRepository;

    /**
     * 계층 권한 포맷팅
     * 모든 Hierarchy를 List로 가져와 규칙에 맞게 만들고 문자열로 반환.
     * @return ROLE_ADMIN > ROLE_MANAGER > ROLE_USER
     */
    @Transactional(readOnly = false)
    public String findAllHierarchy(){

        List<RoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> itr = rolesHierarchy.iterator();
        StringBuilder concatedRoles = new StringBuilder();
        while (itr.hasNext()){
            RoleHierarchy roleHierarchy = itr.next();
            if(roleHierarchy.getParentName() != null){
                concatedRoles.append(roleHierarchy.getParentName().getChildName());
                concatedRoles.append(" > ");
                concatedRoles.append(roleHierarchy.getChildName());
                concatedRoles.append("\n");
            }
        }
        return concatedRoles.toString();
    }
}
