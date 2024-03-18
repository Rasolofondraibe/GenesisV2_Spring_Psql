package com.vente.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Article {
    @Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Integer id_article;
public Integer getIdArticle(){ return id_article; }
public void setIdArticle(Integer o){ id_article=o; }
private String nom;
public String getNom(){ return nom; }
public void setNom(String o){ nom=o; }

    
}

