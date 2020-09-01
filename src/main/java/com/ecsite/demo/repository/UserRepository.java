package com.ecsite.demo.repository;

import com.ecsite.demo.domain.User;
import com.ecsite.demo.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SqlSessionTemplate sqlSessionTemplate;

    private final String STATEMENT = "com.ecsite.demo.mapper.UserMapper";


    public UserDetailsImpl findByEmail(String email){
        return sqlSessionTemplate.selectOne(STATEMENT + ".load",email);
    }

    public void insert(User user){
        sqlSessionTemplate.insert(STATEMENT + ".insert",user);
    }
}
