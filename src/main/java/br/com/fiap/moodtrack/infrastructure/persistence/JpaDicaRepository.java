package br.com.fiap.moodtrack.infrastructure.persistence;

import br.com.fiap.moodtrack.domain.model.Dica;
import br.com.fiap.moodtrack.domain.repository.DicaRepository;
import br.com.fiap.moodtrack.infrastructure.persistence.qualifier.JpaRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@JpaRepo
@ApplicationScoped
public class JpaDicaRepository implements DicaRepository {
    @Inject EntityManager em;

    @Transactional
    @Override
    public Dica save(Dica aggregate) {
        if (aggregate.getId() == null) {
            em.persist(aggregate);
            return aggregate;
        }
        return em.merge(aggregate);
    }

    @Override
    public Optional<Dica> findById(Long id) {
        return Optional.ofNullable(em.find(Dica.class, id));
    }

    @Override
    public List<Dica> findAll() {
        return em.createQuery("select d from Dica d", Dica.class).getResultList();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public boolean existsById(Long id) {
        Long c = em.createQuery("select count(d.id) from Dica d where d.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return c != 0;
    }

    @Override
    public Optional<Dica> findRandom() {
        Long count = em.createQuery("select count(d.id) from Dica d", Long.class).getSingleResult();
        if (count == 0) return Optional.empty();
        int offset = ThreadLocalRandom.current().nextInt(count.intValue());
        List<Dica> list = em.createQuery("select d from Dica d", Dica.class)
                .setFirstResult(offset).setMaxResults(1).getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}
