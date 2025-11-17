package br.com.fiap.moodtrack.infrastructure.persistence;

import br.com.fiap.moodtrack.domain.model.AnaliseIa;
import br.com.fiap.moodtrack.domain.repository.AnaliseIaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class JpaAnaliseIaRepository implements AnaliseIaRepository {

    @Inject
    EntityManager em;

    @Transactional
    @Override
    public AnaliseIa save(AnaliseIa aggregate) {
        if (aggregate.getId() == null) {
            em.persist(aggregate);
            return aggregate;
        }
        return em.merge(aggregate);
    }

    @Override
    public Optional<AnaliseIa> findById(Long id) {
        return Optional.ofNullable(em.find(AnaliseIa.class, id));
    }

    @Override
    public List<AnaliseIa> findAll() {
        return em.createQuery("select a from AnaliseIa a", AnaliseIa.class).getResultList();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public boolean existsById(Long id) {
        Long c = em.createQuery("select count(a.id) from AnaliseIa a where a.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return c != 0;
    }

    @Override
    public List<AnaliseIa> findByCheckin(Long checkinId) {
        return em.createQuery("select a from AnaliseIa a where a.checkin.id = :c order by a.dataAnalise desc", AnaliseIa.class)
                .setParameter("c", checkinId).getResultList();
    }
}
