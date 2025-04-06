package Model;

public class Turma {
    private String nome;
    private String codigo;

    public Turma(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public String getNome() { return nome; }
    public String getCodigo() { return codigo; }
}