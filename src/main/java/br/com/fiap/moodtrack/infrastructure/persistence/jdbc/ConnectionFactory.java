package br.com.fiap.moodtrack.infrastructure.persistence.jdbc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class ConnectionFactory {
    @Inject DataSource dataSource;
    public Connection getConnection() throws SQLException { return dataSource.getConnection(); }
}
