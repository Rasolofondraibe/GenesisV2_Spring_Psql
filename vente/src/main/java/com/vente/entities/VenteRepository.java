package com.vente.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenteRepository extends CrudRepository<Vente,Integer> {
    Page<Vente> findAll(Pageable pageable);    
}
