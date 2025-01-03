package org.ricardo.examen.application.port.input;

import org.ricardo.examen.domain.model.ClientMetrics;

public interface GetClientsMetricsUseCase {

    ClientMetrics getMetrics();
}
