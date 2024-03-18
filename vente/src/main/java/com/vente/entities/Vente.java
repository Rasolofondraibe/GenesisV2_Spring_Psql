package com.vente.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Vente {
    @Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Integer id_vente;
public Integer getIdVente(){ return id_vente; }
public void setIdVente(Integer o){ id_vente=o; }
@jakarta.persistence.ManyToOne
@jakarta.persistence.JoinColumn(name="id_article")
private Article id_article;
public Article getArticle(){ return id_article; }
public void setArticle(Article o){ id_article=o; }
private Double nombre;
public Double getNombre(){ return nombre; }
public void setNombre(Double o){ nombre=o; }
private java.time.LocalDate date;
public java.time.LocalDate getDate(){ return date; }
public void setDate(java.time.LocalDate o){ date=o; }

    
}

