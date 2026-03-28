import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements IOrdenador<T>  {
    
    private final String nome = "Merge Sort";
    private long comparacoes;
    private long movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0/1_000_000;

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public long getComparacoes() {
        return comparacoes;
    }

    @Override
    public long getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }

    private void iniciar(){
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar(){
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    @Override
    public T[] ordenar(T[] dados) {

        T[] dadosOrdenados;
        int dir = dados.length - 1;
        int esq = 0;

        iniciar();

        dadosOrdenados = mergesort(dados, esq, dir);

        terminar();

        return dadosOrdenados;

    }

    private T[] mergesort(T[] dados, int esq, int dir) {

        comparacoes++;

        if ( esq < dir ) {

            int meio = ( esq + dir ) / 2;

            T[] ladoEsq = mergesort(dados, esq, meio);
            T[] ladoDir = mergesort(dados, meio + 1, dir);
            T[] merged = merge(ladoEsq, ladoDir);

            return merged;

        }

        return Arrays.copyOfRange(dados, esq, esq + 1);

    }

    private T[] merge(T[] esq, T[] dir) {

        T[] merged = Arrays.copyOf(esq, esq.length + dir.length);

        movimentacoes += merged.length;

        int i = 0, j = 0, k = 0;

        while ( i < esq.length && j < dir.length ) {

            comparacoes++;
            movimentacoes++;

            if ( esq[i].compareTo(dir[j]) <= 0 ) merged[k] = esq[i++];
            else merged[k] = dir[j++];

            k++;

        }

        comparacoes++;

        if ( i == esq.length ) {

            for ( ; k < merged.length; k++ ) {

                merged[k] = dir[j++];
                movimentacoes++;
                
            }

        } else {

            for ( ; k < merged.length; k++ ) {

                merged[k] = esq[i++];
                movimentacoes++;

            }

        }

        return merged;

    }

}
