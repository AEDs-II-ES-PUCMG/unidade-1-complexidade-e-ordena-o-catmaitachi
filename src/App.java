import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;
    

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;        
    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }

    static String gerarCabecalho() {

        StringBuilder sb = new StringBuilder();

        sb.append("+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        sb.append("| Método             | Tamanho do Vetor   | Comparações        | Movimentações      | Tempo (ms)         |\n");
        sb.append("+--------------------+--------------------+--------------------+--------------------+--------------------+\n");

        return sb.toString();

    }

    static String gerarLinha(String algoritmo, int tamanhoVetor, long numComparacoes, long numMovimentacoes, double tempo) {

        StringBuilder sb = new StringBuilder();
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR")); 

        sb.append(String.format("| %-18s | %18s | %18s | %18s | %18s |\n", algoritmo, nf.format(tamanhoVetor), nf.format(numComparacoes), nf.format(numMovimentacoes), nf.format(tempo)));
        sb.append("+--------------------+--------------------+--------------------+--------------------+--------------------+\n");

        return sb.toString();

    }

    static String visualizarVetor(Integer[] vetor) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < vetor.length; i++) {
            sb.append(vetor[i]);
            if (i < vetor.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.print(gerarCabecalho());

        int tam = 50000;
        Integer[] vetor = gerarVetorObjetos(tam);

        BubbleSort<Integer> bolha = new BubbleSort<>();

        Integer[] vetorOrdenadoBolha = bolha.ordenar(vetor);

        System.out.print(gerarLinha("Bubble Sort", tam, bolha.getComparacoes(), bolha.getMovimentacoes(), bolha.getTempoOrdenacao()));
    
        InsertionSort<Integer> insercao = new InsertionSort<>();
    
        Integer[] vetorOrdenadoInsercao = insercao.ordenar(vetor);
    
        System.out.print(gerarLinha("Insertion Sort", tam, insercao.getComparacoes(), insercao.getMovimentacoes(), insercao.getTempoOrdenacao()));
    
        SelectionSort<Integer> selecao = new SelectionSort<>();
    
        Integer[] vetorOrdenadoSelecao = selecao.ordenar(vetor);
    
        System.out.print(gerarLinha("Selection Sort", tam, selecao.getComparacoes(), selecao.getMovimentacoes(), selecao.getTempoOrdenacao()));

    }
}
