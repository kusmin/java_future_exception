package com.async;

import java.util.concurrent.CompletableFuture;

public class ExemploExceptionally {

    public static void main(String[] args) {

        // Cria uma tarefa assíncrona que pode falhar
        CompletableFuture<Integer> calculoAssincrono = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executando tarefa crítica...");

            // Simula uma falha 50% das vezes
            if (Math.random() > 0.5) {
                throw new RuntimeException("Erro: cálculo inválido!");
            }

            return 100; // Resultado em caso de sucesso
        });

        // Aplica o tratamento de exceção com exceptionally
        CompletableFuture<Integer> resultadoTratado = calculoAssincrono
                .exceptionally(ex -> {
                    System.err.println("Falha detectada! Motivo: " + ex.getMessage());

                    // Valor padrão para continuar o processamento
                    return 0;
                })
                .thenApply(resultado -> {
                    System.out.println("Processando resultado: " + resultado);
                    return resultado * 2;
                });

        // Consome o resultado final (sucesso ou fallback)
        resultadoTratado.thenAccept(resultadoFinal ->
                System.out.println("Resultado final: " + resultadoFinal)
        );

        // Garante que a aplicação não termine antes da tarefa
        resultadoTratado.join();
    }
}
