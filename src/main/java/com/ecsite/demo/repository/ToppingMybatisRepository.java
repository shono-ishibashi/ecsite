package com.ecsite.demo.repository;

import com.ecsite.demo.domain.Topping;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ToppingMybatisRepository {

    private final SqlSessionTemplate sqlSessionTemplate;

    private final String STATMENT = "com.ecsite.demo.mapper.ToppingMapper";

    public List<Topping> findAll() {
        return sqlSessionTemplate.selectList(STATMENT + ".findAll");
    }
}
