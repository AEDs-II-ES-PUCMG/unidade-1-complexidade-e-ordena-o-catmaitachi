
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * MIT License
 *
 * Copyright(c) 2022-25 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class AppOficina {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;
    static Comparator<Produto> criterio;

    static Produto[] ordenadosPorDesc;
    static Produto[] ordenadosPorIden;

    // #region utilidades
    static Scanner teclado;

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static String lerTexto(String mensagem) {
        System.out.print(mensagem + ": ");
        return teclado.nextLine();
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }
    

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Inserção");
        System.out.println("3 - Seleção");
        System.out.println("4 - Mergesort");
        System.out.println("5 - Heapsort");
        System.out.println("6 - Quicksort");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Por descrição");
        System.out.println("2 - Por código");
        System.out.println("3 - Por valor de venda");
        
        return lerNumero("Digite sua opção", Integer.class);
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado. Produtos não carregados");
            dadosCarregados = null;
        }
        return dadosCarregados;
    }

    static Produto localizarProduto() {

        cabecalho();
        
        System.out.println("Localizando um produto, escolha um meio de busca:");
        
        System.out.println("1 - Por identificador");
        System.out.println("2 - Por descrição");

        int opcao = lerNumero("Escolha uma opção", Integer.class);
        
        int numero = 0;
        String descricao = "";
        Produto[] baseBusca = null;

        switch (opcao) {

            case 1 :
                
                numero = lerNumero("Digite o identificador do produto", Integer.class);
                baseBusca = ordenadosPorIden;
                break;

            case 2 :

                descricao = lerTexto("Digite a descrição do produto");
                baseBusca = ordenadosPorDesc;
                break;

        }

        Produto localizado = null;
        
        int inicio = 0;
        int fim = quantProdutos - 1;

        while ( inicio <= fim ) {

            int meio = inicio + ( fim - inicio ) / 2;

            if ( opcao == 1 ) {

                if ( baseBusca[meio].hashCode() == numero ) {

                    localizado = baseBusca[meio];
                    break;
                
                }

                else if ( baseBusca[meio].hashCode() < numero ) inicio = meio + 1;
                else fim = meio - 1;
                
            } else if ( opcao == 2 ) {

                int comparacao = baseBusca[meio].getDescricao().compareToIgnoreCase(descricao);

                if ( comparacao == 0 ) {

                    localizado = baseBusca[meio];
                    break;

                } 
                
                else if ( comparacao < 0 ) inicio = meio + 1;
                else fim = meio - 1;

            }

        }
        
        return localizado;
    
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";
        
        if(produto!=null){
            mensagem = String.format("Dados do produto:\n%s", produto);            
        }
        
        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        double valor = lerNumero("valor", Double.class);
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
            relatorio.append(produtos[i]+"\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos(){

        cabecalho();
        
        int opcaoSort = exibirMenuOrdenadores();
        int opcaoCrit = exibirMenuComparadores();

        if ( opcaoSort == 0 || opcaoCrit == 0 ) return;

        ordenador = null;

        switch ( opcaoSort ) {

            case 1 -> ordenador = new BubbleSort<>();
            case 2 -> ordenador = new InsertSort<>();
            case 3 -> ordenador = new SelectionSort<>();
            case 4 -> ordenador = new MergeSort<>();
            case 5 -> ordenador = new HeapSort<>();
            case 6 -> ordenador = new QuickSort<>();

        }

        criterio = null;
        
        switch ( opcaoCrit ) {

            case 1 -> criterio = new ComparadorPadrao();
            case 2 -> criterio = new ComparadorPorCodigo();
            case 3 -> criterio = new ComparadorPorValor();

        }

        produtos = ordenador.ordenar(produtos, criterio);

        System.out.println("Tempo gasto para ordenar: " + ordenador.getTempoOrdenacao() + "ms");

    }

    static void embaralharProdutos(){
        Collections.shuffle(Arrays.asList(produtos));
    }

    static void verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados){
        cabecalho();
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)?");
        String resposta = teclado.nextLine().toUpperCase();
        if(resposta.equals("S"))
            dadosOriginais = Arrays.copyOf(copiaDados, copiaDados.length);
    }

    static void listarProdutos(){
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }

    public static void main(String[] args) {

        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos(nomeArquivoDados);
        embaralharProdutos();

        ordenadosPorDesc = new MergeSort<Produto>().ordenar(produtos, new ComparadorPadrao());
        ordenadosPorIden = new MergeSort<Produto>().ordenar(produtos, new ComparadorPorCodigo());

        int opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            pausa();
        }while (opcao != 0);

        teclado.close();

    }                       

}
