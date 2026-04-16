import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class HeapSort<T extends Comparable<T>> implements IOrdenador<T> {
    
    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private T[] dadosOrdenados;
    private Comparator<T> comparador;
    private T[] temp;

    public HeapSort() {
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
        temp = Arrays.copyOf(dadosOrdenados, tamanho + 1);
        inicio = LocalDateTime.now();
        heapsort();
        termino = LocalDateTime.now();
        temp = null;
        return dadosOrdenados;

    }

    private void heapsort() {

        for ( int i = 0; i < dadosOrdenados.length; i++ ) { 
            
            movimentacoes++;
            temp[i+1] = dadosOrdenados[i];

        }

        for ( int tamHeap = (temp.length - 1)/2; tamHeap >= 1; tamHeap -- ) {

            restaurar(tamHeap, temp.length - 1);

        }

        int tamHead = temp.length - 1;
        swap(1, tamHead--);

        while ( tamHead > 1 ) {

            restaurar( 1, tamHead );
            swap( 1, tamHead-- );

        }

        for ( int i = 0; i < dadosOrdenados.length; i ++ ) {

            dadosOrdenados[i] = temp[i+1];

        }

    }

    private void restaurar( int i, int tamHeap ) {

        int maior = i;
        int filho = getMaiorFilho(i, tamHeap);
        comparacoes +=2;

        if ( this.comparador.compare(temp[i], temp[filho]) < 0 ) maior = filho;

        if ( maior != i ) {

            swap(i, maior);

            comparacoes++;
            if ( maior <= tamHeap/2 ) restaurar(maior, tamHeap);

        }


    }

    private int getMaiorFilho( int i, int tamHeap ) {

        int filho;
        comparacoes++;

        if ( 
            
            2*i == tamHeap || 
            this.comparador.compare(temp[2*i], temp[2*i+1]) > 0
        
        ) filho = 2*i;
        else filho = 2*i + 1;

        return filho;

    }

    private void swap( int i, int j ) {

        movimentacoes += 3;

        T aux = temp[i];
        temp[i] = temp[j];
        temp[j] = aux; 

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
