package br.com.fiap.moodtrack.infrastructure.persistence;

import br.com.fiap.moodtrack.domain.model.Configuracao;
import br.com.fiap.moodtrack.domain.repository.ConfiguracaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class JpaConfiguracaoRepository implements ConfiguracaoRepository {

    @Inject
    EntityManager em;

    @Transactional
    @Override
    public Configuracao save(Configuracao aggregate) {
        if (aggregate.getId() == null) {
            em.persist(aggregate);
            return aggregate;
        }
        return em.merge(aggregate);
    }

    @Override
    public Optional<Configuracao> findById(Long id) {
        return Optional.ofNullable(em.find(Configuracao.class, id));
    }

    @Override
    public List<Configuracao> findAll() {
        return em.createQuery("select c from Configuracao c", Configuracao.class).getResultList();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public boolean existsById(Long id) {
        Long c = em.createQuery("select count(c.id) from Configuracao c where c.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return c != 0;
    }

    @Override
    public Optional<Configuracao> findByUsuarioId(Long usuarioId) {
        List<Configuracao> list = em.createQuery("select c from Configuracao c where c.usuario.id = :u", Configuracao.class)
                .setParameter("u", usuarioId).setMaxResults(1).getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}
