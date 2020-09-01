package com.ecsite.demo.service;

import com.ecsite.demo.domain.Item;
import com.ecsite.demo.domain.Topping;
import com.ecsite.demo.repository.ItemMybatisRepository;
import com.ecsite.demo.repository.ToppingMybatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemMybatisRepository itemMybatisRepository;

    @Autowired
    private ToppingMybatisRepository toppingMybatisRepository;

    public List<Item> findAll() {
        return itemMybatisRepository.findAll();
    }

    public Item load(Integer id) {
        return itemMybatisRepository.load(id);
    }

    public List<Topping> findAllTopping(){
        return toppingMybatisRepository.findAll();
    }

    public List<Item> findByNameLike(String partOfName){
        return itemMybatisRepository.findByNameLike(partOfName);
    }

    public Page<Item> showListPaging(int page, int size, List<Item> itemList) {
        // 表示させたいページ数を-1しなければうまく動かない
        page--;
        // どの従業員から表示させるかと言うカウント値
        int startItemCount = page * size;
        // 絞り込んだ後の従業員リストが入る変数
        List<Item> list;

        if (itemList.size() < startItemCount) {
            // (ありえないが)もし表示させたい従業員カウントがサイズよりも大きい場合は空のリストを返す
            list = Collections.emptyList();
        } else {
            // 該当ページに表示させる従業員一覧を作成
            int toIndex = Math.min(startItemCount + size, itemList.size());
            list = itemList.subList(startItemCount, toIndex);
        }

        // 上記で作成した該当ページに表示させる従業員一覧をページングできる形に変換して返す
        Page<Item> itemPage
                = new PageImpl<Item>(list, PageRequest.of(page, size), itemList.size());
        return itemPage;
    }
}
