package org.spring_la_mia_pizzeria.controller;

import java.util.List;
import java.util.Optional;

import org.spring_la_mia_pizzeria.model.Pizza;
import org.spring_la_mia_pizzeria.repository.PizzaRepository;
import org.spring_la_mia_pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class PizzaController1 {

    @Autowired
    PizzaService service;
    @Autowired
    PizzaRepository pizzaRepository;

    @GetMapping("/")
    public List<Pizza> index() {
        return service.findAll();
    }

    @GetMapping("/search")
    public List<Pizza> search(@RequestParam String name) {
        return service.findByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> show(@PathVariable Integer id) {
        Optional<Pizza> pizzaTry = service.findById(id);
        if (pizzaTry.isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pizza>(pizzaTry.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Pizza> store(@RequestBody Pizza pizza) {

        return new ResponseEntity<Pizza>(service.create(pizza), HttpStatus.OK);

    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Pizza> edit(@PathVariable Integer id, @RequestBody Pizza pizza) {
        pizza.setId(id);
        return new ResponseEntity<Pizza>(service.update(pizza), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pizza> delete(@PathVariable Integer id) {
        if (service.findById(id).isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        return new ResponseEntity<Pizza>(HttpStatus.OK);
    }

}
