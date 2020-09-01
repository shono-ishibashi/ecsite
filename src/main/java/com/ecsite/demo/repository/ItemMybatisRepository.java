package com.ecsite.demo.repository;

import com.ecsite.demo.domain.Item;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemMybatisRepository {

    private final SqlSessionTemplate sqlSessionTemplate;

    private final String STATMENT = "com.ecsite.demo.mapper.ItemMapper";

    public Item load(Integer id){
        return sqlSessionTemplate.selectOne(STATMENT + ".load", id);
    }

    public List<Item> findAll(){
        return sqlSessionTemplate.selectList(STATMENT + ".findAll");
    }

    public List<Item> findByNameLike(String partOfName){
        return sqlSessionTemplate.selectList(STATMENT + ".findByNameLike",partOfName);
    }

}
