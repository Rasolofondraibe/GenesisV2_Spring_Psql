package com.vente.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Unite {
    @Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Integer id_unite;
public Integer getIdUnite(){ return id_unite; }
public void setIdUnite(Integer o){ id_unite=o; }
private String unite;
public String getUnite(){ return unite; }
public void setUnite(String o){ unite=o; }

    
}

