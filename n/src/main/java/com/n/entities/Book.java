package com.n.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Book {
    @Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Integer id;
public Integer getId(){ return id; }
public void setId(Integer o){ id=o; }
private String nom_book;
public String getNomBook(){ return nom_book; }
public void setNomBook(String o){ nom_book=o; }
private Integer page;
public Integer getPage(){ return page; }
public void setPage(Integer o){ page=o; }
private String type;
public String getType(){ return type; }
public void setType(String o){ type=o; }

    
}

