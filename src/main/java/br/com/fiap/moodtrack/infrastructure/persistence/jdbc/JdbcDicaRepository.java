package br.com.fiap.moodtrack.infrastructure.persistence.jdbc;

import br.com.fiap.moodtrack.domain.model.Dica;
import br.com.fiap.moodtrack.domain.repository.DicaRepository;
import br.com.fiap.moodtrack.infrastructure.persistence.qualifier.JdbcRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.sql.*;
import java.util.*;

@JdbcRepo
@ApplicationScoped
public class JdbcDicaRepository implements DicaRepository {

    @Inject ConnectionFactory cf;

    @Transactional
    @Override
    public Dica save(Dica d) {
        try (var con = cf.getConnection()) {
            if (d.getId() == null) {
                try (var ps = con.prepareStatement(
                        "INSERT INTO DICAS (TITULO, DESCRICAO, CATEGORIA) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, d.getTitulo());
                    ps.setString(2, d.getDescricao());
                    ps.setString(3, d.getCategoria());
                    ps.executeUpdate();
                    try (var rs = ps.getGeneratedKeys()) {
                        if (rs.next()) d.setId(rs.getLong(1));
                    }
                }
                return d;
            } else {
                try (var ps = con.prepareStatement(
                        "UPDATE DICAS SET TITULO=?, DESCRICAO=?, CATEGORIA=? WHERE ID_DICA=?")) {
                    ps.setString(1, d.getTitulo());
                    ps.setString(2, d.getDescricao());
                    ps.setString(3, d.getCategoria());
                    ps.setLong(4, d.getId());
                    ps.executeUpdate();
                }
                return d;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Dica> findById(Long id) {
        try (var con = cf.getConnection();
             var ps = con.prepareStatement(
                     "SELECT ID_DICA,TITULO,DESCRICAO,CATEGORIA FROM DICAS WHERE ID_DICA=?")) {
            ps.setLong(1, id);
            try (var rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Dica> findAll() {
        try (var con = cf.getConnection();
             var ps = con.prepareStatement(
                     "SELECT ID_DICA,TITULO,DESCRICAO,CATEGORIA FROM DICAS ORDER BY ID_DICA");
             var rs = ps.executeQuery()) {
            var list = new ArrayList<Dica>();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void deleteById(Long id) {
        try (var con = cf.getConnection();
             var ps = con.prepareStatement("DELETE FROM DICAS WHERE ID_DICA=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean existsById(Long id) {
        try (var con = cf.getConnection();
             var ps = con.prepareStatement("SELECT 1 FROM DICAS WHERE ID_DICA=?")) {
            ps.setLong(1, id);
            try (var rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Dica> findRandom() {
        try (var con = cf.getConnection();
             var ps = con.prepareStatement(
                     "SELECT ID_DICA,TITULO,DESCRICAO,CATEGORIA " +
                             "FROM DICAS ORDER BY SYS_GUID() FETCH FIRST 1 ROWS ONLY");
             var rs = ps.executeQuery()) {
            if (!rs.next()) return Optional.empty();
            return Optional.of(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Dica map(ResultSet rs) throws SQLException {
        var d = new Dica();
        d.setId(rs.getLong("ID_DICA"));
        d.setTitulo(rs.getString("TITULO"));
        d.setDescricao(rs.getString("DESCRICAO"));
        d.setCategoria(rs.getString("CATEGORIA"));
        return d;
    }
}
