package br.com.fiap.moodtrack.infrastructure.persistence;

import br.com.fiap.moodtrack.domain.model.Usuario;
import br.com.fiap.moodtrack.domain.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class JpaUsuarioRepository implements UsuarioRepository {

    @Inject
    EntityManager em;

    @Transactional
    @Override
    public Usuario save(Usuario aggregate) {
        if (aggregate.getId() == null) {
            em.persist(aggregate);
            return aggregate;
        }
        return em.merge(aggregate);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.ofNullable(em.find(Usuario.class, id));
    }

    @Override
    public List<Usuario> findAll() {
        return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public boolean existsById(Long id) {
        Long c = em.createQuery("select count(u.id) from Usuario u where u.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return c != 0;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        List<Usuario> list = em.createQuery("select u from Usuario u where u.email = :e", Usuario.class)
                .setParameter("e", email).setMaxResults(1).getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}
