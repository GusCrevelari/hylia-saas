package br.com.fiap.moodtrack.domain.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    T save(T aggregate);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    boolean existsById(ID id);
}
