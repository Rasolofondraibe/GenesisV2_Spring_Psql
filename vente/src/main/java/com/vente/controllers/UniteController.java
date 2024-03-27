package com.vente.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import com.vente.entities.Unite;
import com.vente.entities.UniteRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Controller
@CrossOrigin

public class UniteController {
    @Autowired
private UniteRepository repo;

    
    @PostMapping("/insertunite.do")
public ResponseEntity<Unite> insert( @RequestParam String unite){
    Unite o=new Unite();
    o.setUnite(unite);
    repo.save(o);
    return ResponseEntity.ok(o);
}
@GetMapping("/tocrudunite.do")
public ResponseEntity<Page<Unite>> crudpage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
    return ResponseEntity.ok((Page<Unite>)repo.findAll(PageRequest.of(page, size)));
}
@PostMapping("/updateunite.do")
public ResponseEntity<Unite> update( @RequestParam String idUnite, @RequestParam String unite){
    Unite o=new Unite();
    o.setIdUnite(Integer.parseInt(idUnite));
    o.setUnite(unite);
    repo.save(o);
    return ResponseEntity.ok(o);
}
@PostMapping("/deleteunite.do")
public ResponseEntity<HttpStatus> delete(@RequestParam Integer idUnite){
    repo.deleteById(idUnite);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}

}

