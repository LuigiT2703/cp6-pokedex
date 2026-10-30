package br.com.fiap.resource;

import br.com.fiap.bo.PokemonBO;
import br.com.fiap.to.PokemonTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/pokedex") //localhost:8080/pokedex
public class PokemonResource {
    private final PokemonBO pokemonBO = new PokemonBO();

    @GetMapping
    public ResponseEntity<List<PokemonTO>> findAll() throws SQLException {
        return ResponseEntity.ok(pokemonBO.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) throws SQLException {
        PokemonTO pokemon = pokemonBO.findByCodigo(codigo);
        if (pokemon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon não encontrado");
        }
        return ResponseEntity.ok(pokemon);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PokemonTO pokemon) throws SQLException {
        PokemonTO response = pokemonBO.save(pokemon);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo, @RequestBody @Valid PokemonTO pokemon) throws SQLException {
        pokemon.setCodigo(codigo); // pega o código da URL
        PokemonTO response = pokemonBO.update(pokemon);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon não encontrado");
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> delete(@PathVariable Long codigo) throws SQLException {
        if (pokemonBO.delete(codigo)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon não encontrado");
    }

    // Erros de banco viram 500 com mensagem genérica (o detalhe fica só no log do servidor)
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException e) {
        System.err.println("Erro de banco: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao acessar o banco de dados");
    }
}
