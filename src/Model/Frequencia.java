package Model;

public class Frequencia {
    private String data;
    private String matricula;
    private String turma;
    private boolean presente;

    public Frequencia(String data, String matricula, String turma, boolean presente) {
        this.data = data;
        this.matricula = matricula;
        this.turma = turma;
        this.presente = presente;
    }

    public String getData() { return data; }
    public String getMatricula() { return matricula; }
    public String getTurma() { return turma; }
    public boolean isPresente() { return presente; }
}