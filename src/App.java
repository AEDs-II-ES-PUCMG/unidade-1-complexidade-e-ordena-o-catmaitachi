import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class App {

    // Tamanhos de Teste

    static final int[] tamanhosTesteGrande  = { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio   = {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static int[] tamanhosTestePersonalizado = new int[5];

    // Utilitários

    static int[] tamanhosTeste = new int[5];
    static IOrdenador<Integer> algoritmo;
    static Random aleatorio = new Random();
    static Scanner scanner = new Scanner(System.in);
    static double nanoToMilli = 1.0/1_000_000;

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){

        int[] vetor = new int[tamanho];

        for (int i = 0; i < tamanho; i++) vetor[i] = aleatorio.nextInt(1, tamanho/2);
        
        return vetor;

    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {

        Integer[] vetor = new Integer[tamanho];
        
        for (int i = 0; i < tamanho; i++) vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        
        return vetor;
    
    }

    static void pausa() { 
        
        System.out.println("\nPressione Enter para continuar..."); 
        
        try { System.in.read(); } catch (Exception e) {} 
    
    }

    static String iniciarTabela() {

        StringBuilder sb = new StringBuilder();

        sb.append("\n");
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

    /**
    * Ajuda a visualizar o conteúdo do vetor, isso é mais pra fins de teste...
    * @param vetor Vetor a ser visualizado.
    * @return String com a representação do vetor, no formato: [valor1, valor2, valor3, ...]
    */ 
    static String visualizarVetor(Integer[] vetor) {

        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (int i = 0; i < vetor.length; i++) {

            sb.append(vetor[i]);

            if (i < vetor.length - 1) sb.append(", ");
            
        }

        sb.append("]");

        return sb.toString();

    }

    static String selecAlgoritmo() {

            StringBuilder sb = new StringBuilder();

            sb.append("\nEscolha um algoritmo de ordenação para começar:\n\n");

            sb.append("[1] Bubble Sort\n");
            sb.append("[2] Selection Sort\n");
            sb.append("[3] Insertion Sort\n"); 
            sb.append("[4] Merge Sort\n");
            sb.append("\n: ");

            return sb.toString();

    }

    static String selecTamanhoTeste() {

        StringBuilder sb = new StringBuilder();

        sb.append("\nEscolha um tamanho de teste:\n\n");

        sb.append("[1] Pequeno\n");
        sb.append("[2] Médio\n");
        sb.append("[3] Grande\n");
        sb.append("[4] Personalizado\n\n: ");

        return sb.toString();

    }

    static void escolherTamanhosPersonalizados() {

        System.out.println("\nDigite tamanhos inteiros de vetor para os testes: \n");

        for ( int i = 0 ; i < tamanhosTestePersonalizado.length; i++ ) {

            System.out.printf("%dº: ", (i+1));
            tamanhosTestePersonalizado[i] = scanner.nextInt();

        }

    }

    static String executarTeste(int tamanhoVetor) {

        Integer[] vetor = gerarVetorObjetos(tamanhoVetor);

        algoritmo.ordenar(vetor);

        return gerarLinha(algoritmo.getNome(), tamanhoVetor, algoritmo.getComparacoes(), algoritmo.getMovimentacoes(), algoritmo.getTempoOrdenacao());

    }

    public static void main(String[] args) {
        
        int opAlgoritmo = -1;
        int opTamanhoTeste = -1;

        while ( true ) {

            System.out.println("\n>> Oficina - Algoritmos de Ordenação <<");

            System.out.print(selecAlgoritmo());
            opAlgoritmo = scanner.nextInt();

            switch ( opAlgoritmo ) {

                case 1: algoritmo = new BubbleSort<>(); break;
                case 2: algoritmo = new SelectionSort<>(); break;
                case 3: algoritmo = new InsertionSort<>(); break;
                case 4: algoritmo = new MergeSort<>(); break;
                default: System.out.println("Opção inválida!\n"); pausa(); continue;

            }

            // Se quiser ver se o Algoritmo tá funcionando direitinho, apenas descomente a linha a baixo!
            // System.out.println( "\n" + visualizarVetor( algoritmo.ordenar(gerarVetorObjetos(tamanhosTestePequeno[2])) ));

            System.out.print(selecTamanhoTeste());
            opTamanhoTeste = scanner.nextInt();

            switch ( opTamanhoTeste ) {

                case 1: tamanhosTeste = tamanhosTestePequeno; break;
                case 2: tamanhosTeste = tamanhosTesteMedio; break;
                case 3: tamanhosTeste = tamanhosTesteGrande; break;
                case 4: escolherTamanhosPersonalizados(); tamanhosTeste = tamanhosTestePersonalizado; break;
                default: System.out.println("Opção inválida!\n"); pausa(); continue;

            }

            if ( algoritmo != null && tamanhosTeste != null ) {

                System.out.print(iniciarTabela());

                for ( int tamanho : tamanhosTeste ) System.out.print(executarTeste(tamanho));

            }

            System.out.print("\nDeseja realizar outro teste? (s/N): ");
            String resposta = scanner.next();

            if ( !resposta.equalsIgnoreCase("s") ) {

                System.out.println("\nEncerrando o programa...\n");
                break;

            }

        } 

    }

}
