import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class QuickSort<T extends Comparable<T>> implements IOrdenador<T> {

    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private T[] dadosOrdenados;
    private Comparator<T> comparador;

    public QuickSort() {
        comparacoes = 0;
        movimentacoes = 0;
    }
    
    @Override
    public T[] ordenar(T[] dados) {
        return ordenar(dados, T::compareTo);
    }

    @Override
    public T[] ordenar(T[] dados, Comparator<T> comparador) {

        this.comparador = comparador;
        int tamanho = dados.length;
        dadosOrdenados = Arrays.copyOf(dados, tamanho);
        inicio = LocalDateTime.now();
        quicksort(0, dados.length - 1);
        termino = LocalDateTime.now();
        return dadosOrdenados;
        
    }

    private void quicksort(int esq, int dir) {

        int part;
        comparacoes++;

        if ( esq < dir ) {

            part = particao(esq, dir);
            quicksort(esq, part - 1);
            quicksort(part + 1, dir);

        }

    }

    private int particao(int inicio, int fim) {

        T pivot = dadosOrdenados[fim];
        int part = inicio - 1;

        for ( int i = inicio; i < fim; i ++ ) {

            comparacoes++;

            if (this.comparador.compare(dadosOrdenados[i], pivot) < 0) {

                part++;
                swap(part, i);

            }

        }

        part++;
        swap(part, fim);
        return part;

    }

    private void swap(int i, int j) {

        movimentacoes += 3;

        T temp = dadosOrdenados[i];
        dadosOrdenados[i] = dadosOrdenados[j];
        dadosOrdenados[j] = temp;

    }

    public long getComparacoes() {
        return comparacoes;
    }

    public long getMovimentacoes() {
        return movimentacoes;
    }

    public double getTempoOrdenacao() {
        return Duration.between(inicio, termino).toMillis();
    }

}
