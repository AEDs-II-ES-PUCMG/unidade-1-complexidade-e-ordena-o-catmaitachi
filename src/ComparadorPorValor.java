import java.util.Comparator;

public class ComparadorPorValor implements Comparator<Produto>{

    @Override
    public int compare(Produto o1, Produto o2) {

        /*
        
        O enunciado diz exatamente "comparador por porcentagem de desconto"...
        Mas isso não faz sentido, pois o percentual de desconto não varia, o mais próximo e correto seria essa comparação por valor de venda, que inclui o desconto.
        
        */

        return Double.compare(o1.valorDeVenda(), o2.valorDeVenda());
    
    }
    
}
