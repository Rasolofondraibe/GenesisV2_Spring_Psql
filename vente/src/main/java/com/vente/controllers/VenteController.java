package com.vente.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import com.vente.entities.Vente;
import com.vente.entities.VenteRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin

public class VenteController {
    @Autowired
private VenteRepository repo;
@Autowired
private com.vente.entities.ArticleRepository repoArticle;

    
    @PostMapping("/insertvente.do")
public ResponseEntity<Vente> insert( @RequestParam String article, @RequestParam String nombre, @RequestParam String date){
    Vente o=new Vente();
    o.setArticle(repoArticle.findById(Integer.parseInt(article)).orElseThrow());o.setNombre(Double.parseDouble(nombre));o.setDate(java.time.LocalDate.parse(date));
    repo.save(o);
    return ResponseEntity.ok(o);
}
@GetMapping("/tocrudvente.do")
public ResponseEntity<List<Vente>> crudpage(){
    return ResponseEntity.ok(repo.findAll());
}
@PostMapping("/updatevente.do")
public Vente update( @RequestParam String idVente, @RequestParam String article, @RequestParam String nombre, @RequestParam String date){
    Vente o=new Vente();
    o.setIdVente(Integer.parseInt(idVente));
    o.setArticle(repoArticle.findById(Integer.parseInt(article)).orElseThrow());o.setNombre(Double.parseDouble(nombre));o.setDate(java.time.LocalDate.parse(date));
    repo.save(o);
    return ResponseEntity.ok(o);
}
@PostMapping("/deletevente.do")
public ResponseEntity<HttpStatus> delete(@RequestParam Integer idVente){
    repo.deleteById(idVente);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}

}

