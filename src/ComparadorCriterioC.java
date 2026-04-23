import java.util.Comparator;

/**
 * Critério C - Índice de Economia (decrescente).
 * O índice de economia é a diferença entre o valor de catálogo atual e o valor efetivamente pago.
 * Desempate 1: Valor Final do Pedido (crescente).
 * Desempate 2: Código Identificador do pedido (crescente).
 */
public class ComparadorCriterioC implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        
        Double media1 = o1.valorFinal() / o1.getQuantosProdutos();
        Double media2 = o2.valorFinal() / o2.getQuantosProdutos();

        int aux = Double.compare(media1, media2);

        if ( aux == 0 ) {

            boolean mesmoValor = o1.valorFinal() == o2.valorFinal();

            if ( mesmoValor ) return o1.getIdPedido() - o2.getIdPedido();

            return Double.compare(o1.valorFinal(), o2.valorFinal());

        }

        return aux;

    }
}
