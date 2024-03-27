package com.vente.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepository extends CrudRepository<Article,Integer> {
    Page<Article> findAll(Pageable pageable);    
}
