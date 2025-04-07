package Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import Model.Aluno;
import Model.Turma;
import Model.Frequencia;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
    private Gson gson = new Gson();
    private final String alunoFile = "alunos.json";
    private final String turmaFile = "turmas.json";
    private final String frequenciaFile = "frequencias.json";

    public void salvarAluno(Aluno aluno) {
        List<Aluno> alunos = carregarAlunos();
        alunos.removeIf(a -> a.getMatricula().equals(aluno.getMatricula()));
        alunos.add(aluno);
        salvarArquivo(alunoFile, alunos);
    }

    public void salvarTurma(Turma turma) {
        List<Turma> turmas = carregarTurmas();
        turmas.removeIf(t -> t.getCodigo().equals(turma.getCodigo()));
        turmas.add(turma);
        salvarArquivo(turmaFile, turmas);
    }

    public void salvarFrequencia(Frequencia frequencia) {
        List<Frequencia> frequencias = carregarFrequencias();
        frequencias.add(frequencia);
        salvarArquivo(frequenciaFile, frequencias);
    }

    public List<Aluno> carregarAlunos() {
        return carregarArquivo(alunoFile, new TypeToken<List<Aluno>>(){}.getType());
    }

    public List<Turma> carregarTurmas() {
        return carregarArquivo(turmaFile, new TypeToken<List<Turma>>(){}.getType());
    }

    public List<Frequencia> carregarFrequencias() {
        return carregarArquivo(frequenciaFile, new TypeToken<List<Frequencia>>(){}.getType());
    }

    private <T> List<T> carregarArquivo(String caminho, Type type) {
        try (Reader reader = new FileReader(caminho)) {
            List<T> result = gson.fromJson(reader, type);
            return result != null ? result : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarArquivo(String caminho, Object obj) {
        try (Writer writer = new FileWriter(caminho)) {
            gson.toJson(obj, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}