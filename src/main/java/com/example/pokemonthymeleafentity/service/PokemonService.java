package com.example.pokemonthymeleafentity.service;

import com.example.pokemonthymeleafentity.model.Pokemon;
import com.example.pokemonthymeleafentity.repo.PokemonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {

    @Autowired
    PokemonRepo repository;

    public List<Pokemon> getAllPokemon(String sortField, String sortDir) {

        Sort poke = (sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());

        return repository.findAll(poke);
    }

}
