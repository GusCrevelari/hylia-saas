package br.com.fiap.moodtrack.application.usecase;

import br.com.fiap.moodtrack.domain.model.Checkin;

public interface IaRiskAnalyzer {
    record Result(double score, String resumo) {}

    Result analyze(Checkin checkin);
}