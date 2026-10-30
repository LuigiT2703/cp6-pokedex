package br.com.fiap.dao;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Carrega o .env uma única vez (variáveis de ambiente têm prioridade; se .env não existir, ignora)
    private static final Dotenv DOTENV = Dotenv.configure().ignoreIfMissing().load();

    private ConnectionFactory() {
    }

    /**
     * Abre uma NOVA conexão a cada chamada. Quem chamar deve fechá-la (try-with-resources).
     * Isso evita que requisições simultâneas fechem a conexão umas das outras.
     */
    public static Connection getConnection() throws SQLException {
        String url = DOTENV.get("DB_URL");
        String user = DOTENV.get("DB_USER");
        String pass = DOTENV.get("DB_PASSWORD");
        if (url == null || user == null || pass == null) {
            throw new SQLException("Variáveis de ambiente do banco (DB_URL, DB_USER, DB_PASSWORD) não configuradas");
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
