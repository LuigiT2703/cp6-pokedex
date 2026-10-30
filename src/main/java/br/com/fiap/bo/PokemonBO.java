package br.com.fiap.bo;

import br.com.fiap.dao.PokemonDAO;
import br.com.fiap.to.PokemonTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class PokemonBO {

    private final PokemonDAO pokemonDAO = new PokemonDAO();

    public ArrayList<PokemonTO> findAll() throws SQLException {
        return pokemonDAO.findAll();
    }

    public PokemonTO findByCodigo(Long codigo) throws SQLException {
        return pokemonDAO.findByCodigo(codigo);
    }

    public PokemonTO save(PokemonTO pokemon) throws SQLException {
        return pokemonDAO.save(pokemon);
    }

    public PokemonTO update(PokemonTO pokemon) throws SQLException {
        return pokemonDAO.update(pokemon);
    }

    public boolean delete(Long codigo) throws SQLException {
        return pokemonDAO.delete(codigo);
    }
}
