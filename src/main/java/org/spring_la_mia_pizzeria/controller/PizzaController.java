package org.spring_la_mia_pizzeria.controller;

import org.spring_la_mia_pizzeria.model.*;
import org.spring_la_mia_pizzeria.repository.IngredientsRepository;
import org.spring_la_mia_pizzeria.repository.PizzaRepository;
import org.spring_la_mia_pizzeria.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @GetMapping
    public String index(Model model) {

        List<Pizza> pizzas = repository.findAll();
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Pizza pizza = repository.findById(id).get();
        model.addAttribute("pizza", pizza);
        model.addAttribute("ingredients", pizza.getIngredients());
        return "pizzas/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "name") String name, Model model) {

        List<Pizza> pizzas = repository.findByNameContaining(name);
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientsRepository.findAll());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientsRepository.findAll());
            return "pizzas/create";
        }

        repository.save(formPizza);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("pizza", repository.findById(id).get());
        model.addAttribute("ingredients", ingredientsRepository.findAll());
        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientsRepository.findAll());
            return "pizzas/edit";
        }

        repository.save(formPizza);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Pizza pizza = repository.findById(id).get();
        // for (SpecialOffer offer : pizza.getSpecialOffers()) {
        // offerRepository.delete(offer);
        // } dunot works
        repository.delete(pizza);
        return "redirect:/";
    }

    @GetMapping("/{id}/order")
    public String order(@PathVariable Integer id, Model model) {
        Order order = new Order();
        order.setPizza(repository.findById(id).get());
        model.addAttribute(order);
        return "orders/create";
    }

}
