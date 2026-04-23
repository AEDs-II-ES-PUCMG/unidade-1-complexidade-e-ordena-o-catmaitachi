import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */
public class ComparadorCriterioB implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        
        int aux = o2.getFormaDePagamento() - o1.getFormaDePagamento();

        if ( aux == 0 ) {

            boolean mesmoValor = o1.valorFinal() == o2.valorFinal();

            if ( mesmoValor ) return o1.getIdPedido() - o2.getIdPedido();

            return Double.compare(o1.valorFinal(), o2.valorFinal());

        }

        return aux;

    }
}
