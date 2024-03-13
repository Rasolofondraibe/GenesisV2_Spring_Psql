package com.n.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import com.n.entities.Book;
import com.n.entities.BookRepository;

@Controller

public class BookController {
    @Autowired
private BookRepository repo;

    
    @PostMapping("/insertbook.do")
public RedirectView insert(String id,String nomBook,String page,String type){
    Book o=new Book();
    o.setNomBook(nomBook);o.setPage(Integer.parseInt(page));o.setType(type);
    repo.save(o);
    return new RedirectView("tocrudbook.do");
}
@GetMapping("/tocrudbook.do")
public String crudpage(Model model){
    model.addAttribute("o", repo.findAll());
    model.addAttribute("viewpage", "book");
    
    return "layout/layout";
}
@PostMapping("/updatebook.do")
public RedirectView update(String id,String nomBook,String page,String type){
    Book o=new Book();
    o.setId(Integer.parseInt(id));
    o.setNomBook(nomBook);o.setPage(Integer.parseInt(page));o.setType(type);
    repo.save(o);
    return new RedirectView("tocrudbook.do");
}
@PostMapping("/deletebook.do")
public RedirectView delete(Integer id){
    repo.deleteById(id);
    return new RedirectView("tocrudbook.do");
}

}

