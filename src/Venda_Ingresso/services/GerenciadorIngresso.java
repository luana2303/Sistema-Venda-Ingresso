package Venda_Ingresso.services;

import Venda_Ingresso.entities.Ingresso;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorIngresso {

    private List<Ingresso> ingressos;
    private int prox = 0;

    public GerenciadorIngresso() {
        this.ingressos = new ArrayList<>();
    }

    // ✅ Comprar ingresso
    public void comprarIngresso(Ingresso ingresso) {
        if (ingresso == null) {
            throw new IllegalArgumentException("Ingresso inválido.");
        }

        ingresso.setCodigo(++prox);
        ingressos.add(ingresso);
    }

    // ✅ Getter
    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    // ✅ Setter (ESSENCIAL para desserialização - R14)
    public void setIngressos(List<Ingresso> ingressos) {
        this.ingressos = ingressos;
        atualizarProximoCodigo();
    }

    // ✅ Recalcula o próximo código (IMPORTANTE)
    private void atualizarProximoCodigo() {
        prox = 0;

        for (Ingresso i : ingressos) {
            if (i.getCodigo() > prox) {
                prox = i.getCodigo();
            }
        }
    }
}