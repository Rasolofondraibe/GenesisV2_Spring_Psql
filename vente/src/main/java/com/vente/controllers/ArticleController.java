package com.vente.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import com.vente.entities.Article;
import com.vente.entities.ArticleRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@Controller
@CrossOrigin

public class ArticleController {
    @Autowired
private ArticleRepository repo;

    
    @PostMapping("/insertarticle.do")
public ResponseEntity<Article> insert( @RequestParam String nom){
    Article o=new Article();
    o.setNom(nom);
    repo.save(o);
    return ResponseEntity.ok(o);
}
@GetMapping("/tocrudarticle.do")
public ResponseEntity<List<Article>> crudpage(){
    return ResponseEntity.ok(repo.findAll());
}
@PostMapping("/updatearticle.do")
public ResponseEntity<Article> update( @RequestParam String idArticle, @RequestParam String nom){
    Article o=new Article();
    o.setIdArticle(Integer.parseInt(idArticle));
    o.setNom(nom);
    repo.save(o);
    return ResponseEntity.ok(o);
}
@PostMapping("/deletearticle.do")
public ResponseEntity<HttpStatus> delete(@RequestParam Integer idArticle){
    repo.deleteById(idArticle);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}

}

