import java.util.Comparator;

public class ComparadorPadrao implements Comparator<Produto>{

    @Override
    public int compare(Produto o1, Produto o2) {
        
        String nome1 = o1.getDescricao().toUpperCase();
        String nome2 = o2.getDescricao().toUpperCase();

        return nome1.compareTo(nome2);

    }
    
}
