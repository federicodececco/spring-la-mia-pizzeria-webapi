package org.spring_la_mia_pizzeria.service;

import java.util.List;
import java.util.Optional;

import org.spring_la_mia_pizzeria.model.Ingredient;
import org.spring_la_mia_pizzeria.model.Order;
import org.spring_la_mia_pizzeria.model.Pizza;
import org.spring_la_mia_pizzeria.model.SpecialOffer;
import org.spring_la_mia_pizzeria.repository.OrderRepository;
import org.spring_la_mia_pizzeria.repository.PizzaRepository;
import org.spring_la_mia_pizzeria.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SpecialOfferRepository offerRepository;

    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    public List<Pizza> findByName(String query) {
        return pizzaRepository.findByNameContaining(query);
    }

    public Optional<Pizza> findById(Integer id) {
        return pizzaRepository.findById(id);
    }

    public Pizza create(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza update(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public void deleteById(Integer id) {
        Pizza pizza = findById(id).get();

        for (SpecialOffer offer : pizza.getSpecialOffers()) {
            offerRepository.deleteById(offer.getId());
        }
        pizzaRepository.deleteById(id);

    }

}
