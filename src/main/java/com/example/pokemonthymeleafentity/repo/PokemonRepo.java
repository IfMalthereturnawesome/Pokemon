package com.example.pokemonthymeleafentity.repo;

import com.example.pokemonthymeleafentity.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PokemonRepo extends JpaRepository<Pokemon, Long> {

}
