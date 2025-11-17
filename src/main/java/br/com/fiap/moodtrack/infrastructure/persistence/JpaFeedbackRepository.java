package br.com.fiap.moodtrack.infrastructure.persistence;

import br.com.fiap.moodtrack.domain.model.Feedback;
import br.com.fiap.moodtrack.domain.repository.FeedbackRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class JpaFeedbackRepository implements FeedbackRepository {

    @Inject
    EntityManager em;

    @Transactional
    @Override
    public Feedback save(Feedback aggregate) {
        if (aggregate.getId() == null) {
            em.persist(aggregate);
            return aggregate;
        }
        return em.merge(aggregate);
    }

    @Override
    public Optional<Feedback> findById(Long id) {
        return Optional.ofNullable(em.find(Feedback.class, id));
    }

    @Override
    public List<Feedback> findAll() {
        return em.createQuery("select f from Feedback f", Feedback.class).getResultList();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public boolean existsById(Long id) {
        Long c = em.createQuery("select count(f.id) from Feedback f where f.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return c != 0;
    }

    @Override
    public List<Feedback> findByUsuario(Long usuarioId) {
        return em.createQuery("select f from Feedback f where f.usuario.id = :u order by f.dataFeedback desc", Feedback.class)
                .setParameter("u", usuarioId).getResultList();
    }

    @Override
    public List<Feedback> findByUsuarioBetween(Long usuarioId, LocalDateTime from, LocalDateTime to) {
        return em.createQuery("select f from Feedback f where f.usuario.id = :u and f.dataFeedback between :f and :t order by f.dataFeedback asc", Feedback.class)
                .setParameter("u", usuarioId).setParameter("f", from).setParameter("t", to).getResultList();
    }
}
