package com.mouramath.todolistapi.domain.entity;


public enum TaskStatus {

    BACKLOG,
    IN_PROGRESS,
    AWAITING_REVIEW,
    COMPLETED;

    public static boolean isCompleted(TaskStatus status) {
        return status == COMPLETED;
    }



}

/*
 * Representa os possíveis estados de uma tarefa no sistema.
 * Uso enum para fornecer uma tipagem forte e prevenir valores inválidos,
 * enquanto mantenho a possibilidade de adicionar novos estados no futuro.
 */

/**
 * Converte o valor do enum para o valor do boolean completed.
 * Útil para mapeamentos e consultas onde precisamos do valor booleano.
 *
 * @param status O status a ser verificado
 * @return true se o status for COMPLETED, false caso contrário
 */