package com.example.pokemonthymeleafentity.controller;

import com.example.pokemonthymeleafentity.model.Pokemon;
import com.example.pokemonthymeleafentity.repo.PokemonRepo;
import com.example.pokemonthymeleafentity.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    PokemonService service;

    PokemonRepo pokemonRepo;

    public HomeController(PokemonRepo pokemonRepo) {
        this.pokemonRepo = pokemonRepo;
    }

    @RequestMapping("/")
    public String index(Model model){

        return viewPage(model,"name", "asc");
    }


    @RequestMapping("/sorted")
    public String viewPage(Model model, @Param("sortField")String sortField,
                       @Param("sortDir")String sortDir) {

        List<Pokemon> sort = service.getAllPokemon(sortField,sortDir);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("pokemons", sort);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Pokemon pokemon = pokemonRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid pokemon Id:" + id));

        model.addAttribute("pokemon", pokemon);
        return "update-pokemon";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Pokemon pokemon) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(Pokemon pokemon, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        pokemonRepo.save(pokemon);
        return "redirect:/";

    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, Pokemon pokemon,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            pokemon.setId(id);
            return "update-pokemon";
        }

        pokemonRepo.save(pokemon);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Pokemon pokemon = pokemonRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        pokemonRepo.delete(pokemon);
        return "redirect:/";
    }



}
