package com.comsysto.agiledevstarter.web;

import com.comsysto.agiledevstarter.domain.Burger;
import com.comsysto.agiledevstarter.persistence.JpaBurgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/burgers")
public class BurgerController {
    private final JpaBurgerRepository burgerRepository;

    @Autowired
    public BurgerController(JpaBurgerRepository burgerRepository) {
        this.burgerRepository = burgerRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Iterable<Burger> getBurgers() {
        return burgerRepository.findAll();
    }

    @GetMapping(path = "/{burgerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Burger> getBurger(@PathVariable long burgerId) {
        return burgerRepository.findById(burgerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createBurger(@RequestBody Burger burger) {
        Burger createdBurger = burgerRepository.save(burger);

        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{burgerId}")
                .buildAndExpand(createdBurger.getId())
                .toUri())
                .build();
    }

    @DeleteMapping(path = "/{burgerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBurger(@PathVariable long burgerId) {
        burgerRepository.deleteById(burgerId);
    }
}
