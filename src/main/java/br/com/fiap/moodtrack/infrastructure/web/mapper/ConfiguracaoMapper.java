package br.com.fiap.moodtrack.infrastructure.web.mapper;

import br.com.fiap.moodtrack.domain.model.Configuracao;
import br.com.fiap.moodtrack.domain.model.Usuario;
import br.com.fiap.moodtrack.infrastructure.web.dto.ConfiguracaoInputDto;
import br.com.fiap.moodtrack.infrastructure.web.dto.ConfiguracaoOutputDto;

public final class ConfiguracaoMapper {
    private ConfiguracaoMapper() {}

    public static Configuracao toDomain(Usuario usuario, ConfiguracaoInputDto in) {
        var c = new Configuracao();
        c.setUsuario(usuario);
        c.setTema(in.getTema());
        c.setNotificacaoAtiva(
                in.getNotificacaoAtiva() == null ? null :
                        (in.getNotificacaoAtiva() ? 1 : 0)
        );
        c.setHorarioLimite(in.getHorarioLimite());
        c.setFusoHorario(in.getFusoHorario());
        return c;
    }

    public static ConfiguracaoOutputDto toOutput(Configuracao c) {
        var out = new ConfiguracaoOutputDto();
        out.setId(c.getId());
        out.setUsuarioId(c.getUsuario() != null ? c.getUsuario().getId() : null);
        out.setTema(c.getTema());
        out.setNotificacaoAtiva(c.getNotificacaoAtiva() != null && c.getNotificacaoAtiva() == 1);

        out.setHorarioLimite(c.getHorarioLimite());
        out.setFusoHorario(c.getFusoHorario());
        return out;
    }
}
