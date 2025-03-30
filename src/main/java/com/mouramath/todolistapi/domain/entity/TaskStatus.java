package com.mouramath.todolistapi.domain.entity;


public enum TaskStatus {

    BACKLOG,
    IN_PROGRESS,
    AWAITING_REVIEW,
    COMPLETED;

    public boolean isCompleted() {
        return this == COMPLETED;
    }



}

/**
 * Enum que representa os possíveis estados de uma tarefa no sistema.
 * Uso enumeração para garantir valores consistentes e possibilitar futuras extensões.
 *
 * A combinação de enum com boolean completed permite:
 * 1. Representação rica dos estados de workflow (mais que apenas "feito/não feito")
 * 2. Consultas eficientes quando precisamos apenas saber se está completo ou não
 * 3. Flexibilidade para adicionar novos estados sem mudar a estrutura principal
 */

