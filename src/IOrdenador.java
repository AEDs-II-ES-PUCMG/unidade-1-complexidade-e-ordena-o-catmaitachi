public interface IOrdenador<T>{
    public T[] ordenar(T[] dados);
    public String getNome();
    public long getComparacoes();
    public long getMovimentacoes();
    public double getTempoOrdenacao();
}