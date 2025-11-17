package br.com.fiap.moodtrack.infrastructure.persistence;

import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.domain.repository.CheckinRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class JpaCheckinRepository implements CheckinRepository {

    @Inject
    EntityManager em;

    @Transactional
    @Override
    public Checkin save(Checkin aggregate) {
        if (aggregate.getId() == null) {
            em.persist(aggregate);
            return aggregate;
        }
        return em.merge(aggregate);
    }

    @Override
    public Optional<Checkin> findById(Long id) {
        return Optional.ofNullable(em.find(Checkin.class, id));
    }

    @Override
    public List<Checkin> findAll() {
        return em.createQuery("select c from Checkin c", Checkin.class).getResultList();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public boolean existsById(Long id) {
        Long c = em.createQuery("select count(c.id) from Checkin c where c.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return c != 0;
    }

    @Override
    public Optional<Checkin> findByUsuarioAndDate(Long usuarioId, LocalDate day) {
        LocalDateTime from = day.atStartOfDay();
        LocalDateTime to = day.atTime(LocalTime.MAX);
        List<Checkin> list = em.createQuery(
                        "select c from Checkin c where c.usuario.id = :u and c.dataCheckin between :f and :t order by c.dataCheckin desc",
                        Checkin.class)
                .setParameter("u", usuarioId)
                .setParameter("f", from)
                .setParameter("t", to)
                .setMaxResults(1)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<Checkin> findByUsuarioBetween(Long usuarioId, LocalDateTime from, LocalDateTime to) {
        return em.createQuery(
                        "select c from Checkin c where c.usuario.id = :u and c.dataCheckin between :f and :t order by c.dataCheckin asc",
                        Checkin.class)
                .setParameter("u", usuarioId)
                .setParameter("f", from)
                .setParameter("t", to)
                .getResultList();
    }

    @Override
    public List<Checkin> findLastN(Long usuarioId, int n) {
        return em.createQuery(
                        "select c from Checkin c where c.usuario.id = :u order by c.dataCheckin desc",
                        Checkin.class)
                .setParameter("u", usuarioId)
                .setMaxResults(n)
                .getResultList();
    }
}
