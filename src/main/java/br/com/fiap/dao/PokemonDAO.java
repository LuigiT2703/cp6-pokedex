package br.com.fiap.dao;

import br.com.fiap.to.PokemonTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PokemonDAO {

    public ArrayList<PokemonTO> findAll() throws SQLException {
        ArrayList<PokemonTO> pokemons = new ArrayList<>();
        String sql = "select CODIGO, NOME, ALTURA, PESO, CATEGORIA, DATA_DE_CAPTURA from DDD_POKEMON order by CODIGO";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pokemons.add(map(rs));
            }
        }
        return pokemons;
    }

    /** Retorna null se não encontrar. */
    public PokemonTO findByCodigo(Long codigo) throws SQLException {
        String sql = "select CODIGO, NOME, ALTURA, PESO, CATEGORIA, DATA_DE_CAPTURA from DDD_POKEMON where CODIGO = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /** Insere e devolve o Pokémon já com o código gerado pelo banco. */
    public PokemonTO save(PokemonTO pokemon) throws SQLException {
        String sql = "insert into DDD_POKEMON(NOME, ALTURA, PESO, CATEGORIA, DATA_DE_CAPTURA) values (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"CODIGO"})) {
            ps.setString(1, pokemon.getNome());
            ps.setDouble(2, pokemon.getAltura());
            ps.setDouble(3, pokemon.getPeso());
            ps.setString(4, pokemon.getCategoria());
            ps.setDate(5, Date.valueOf(pokemon.getDataDeCaptura()));

            if (ps.executeUpdate() == 0) {
                return null;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    pokemon.setCodigo(keys.getLong(1));
                }
            }
            return pokemon;
        }
    }

    /** Retorna null se o código não existir. */
    public PokemonTO update(PokemonTO pokemon) throws SQLException {
        String sql = "update DDD_POKEMON set NOME = ?, ALTURA = ?, PESO = ?, CATEGORIA = ?, DATA_DE_CAPTURA = ? where CODIGO = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pokemon.getNome());
            ps.setDouble(2, pokemon.getAltura());
            ps.setDouble(3, pokemon.getPeso());
            ps.setString(4, pokemon.getCategoria());
            ps.setDate(5, Date.valueOf(pokemon.getDataDeCaptura()));
            ps.setLong(6, pokemon.getCodigo());
            return ps.executeUpdate() > 0 ? pokemon : null;
        }
    }

    public boolean delete(Long codigo) throws SQLException {
        String sql = "delete from DDD_POKEMON where CODIGO = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    private PokemonTO map(ResultSet rs) throws SQLException {
        PokemonTO pokemon = new PokemonTO();
        pokemon.setCodigo(rs.getLong("CODIGO"));
        pokemon.setNome(rs.getString("NOME"));
        pokemon.setAltura(rs.getDouble("ALTURA"));
        pokemon.setPeso(rs.getDouble("PESO"));
        pokemon.setCategoria(rs.getString("CATEGORIA"));
        Date data = rs.getDate("DATA_DE_CAPTURA");
        pokemon.setDataDeCaptura(data != null ? data.toLocalDate() : null);
        return pokemon;
    }
}
