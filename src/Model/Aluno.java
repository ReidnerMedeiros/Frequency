package Model;

public class Aluno {
    private String nome;
    private String matricula;
    private String turma;

    public Aluno(String nome, String matricula, String turma) {
        this.nome = nome;
        this.matricula = matricula;
        this.turma = turma;
    }

    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getTurma() { return turma; }
}