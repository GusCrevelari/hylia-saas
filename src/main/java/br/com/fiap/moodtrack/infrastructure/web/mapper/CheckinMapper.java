package br.com.fiap.moodtrack.infrastructure.web.mapper;

import br.com.fiap.moodtrack.domain.model.Checkin;
import br.com.fiap.moodtrack.domain.model.Dica;
import br.com.fiap.moodtrack.domain.model.RiskLevel;
import br.com.fiap.moodtrack.domain.model.Usuario;
import br.com.fiap.moodtrack.infrastructure.web.dto.CheckinInputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.CheckinOutputDto;

import java.time.LocalDateTime;

public final class CheckinMapper {

    private CheckinMapper() {}

    public static Checkin toDomain(Usuario usuario, Dica dica, CheckinInputDto in) {
        LocalDateTime when = in.getDataCheckin() != null ? in.getDataCheckin() : LocalDateTime.now();
        Checkin c = new Checkin();
        c.setUsuario(usuario);
        c.setDataCheckin(when);
        c.setHumor(in.getHumor());
        c.setEnergia(in.getEnergia());
        c.setCargaTrabalho(in.getCargaTrabalho());
        c.setObservacao(in.getObservacao());
        c.setDica(dica);
        return c;
    }

    public static CheckinOutputDto toOutput(Checkin c) {
        CheckinOutputDto out = new CheckinOutputDto();
        out.setId(c.getId());
        out.setUsuarioId(c.getUsuario() != null ? c.getUsuario().getId() : null);
        out.setDataCheckin(c.getDataCheckin());
        out.setHumor(c.getHumor());
        out.setEnergia(c.getEnergia());
        out.setCargaTrabalho(c.getCargaTrabalho());
        out.setObservacao(c.getObservacao());
        RiskLevel lvl = c.getNivelRisco();
        out.setNivelRisco(lvl != null ? lvl.name() : null);
        out.setDicaId(c.getDica() != null ? c.getDica().getId() : null);
        out.setDicaTitulo(c.getDica() != null ? c.getDica().getTitulo() : null);
        return out;
    }
}
