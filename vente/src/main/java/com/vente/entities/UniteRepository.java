package com.vente.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniteRepository extends CrudRepository<Unite,Integer> {
    Page<Unite> findAll(Pageable pageable);    
}
