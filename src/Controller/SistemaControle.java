package Controller;

import Model.Aluno;
import Model.Frequencia;
import Model.Turma;
import Repository.Arquivo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SistemaControle {
    private Arquivo repo = new Arquivo();

    public String cadastrarTurma(String nome, String codigo) {
        List<Turma> turmas = repo.carregarTurmas();
        if (turmas.stream().anyMatch(t -> t.getCodigo().equals(codigo))) {
            return "Erro: Já existe uma turma com o código " + codigo;
        }
        repo.salvarTurma(new Turma(nome, codigo));
        return "Turma cadastrada com sucesso!";
    }

    public String cadastrarAluno(String nome, String matricula, String turma) {
        List<Turma> turmas = repo.carregarTurmas();
        if (turmas.stream().noneMatch(t -> t.getCodigo().equals(turma))) {
            return "Erro: A turma com código " + turma + " não existe!";
        }
        List<Aluno> alunos = repo.carregarAlunos();
        if (alunos.stream().anyMatch(a -> a.getMatricula().equals(matricula) && a.getTurma().equals(turma))) {
            return "Erro: O aluno com matrícula " + matricula + " já está cadastrado na turma " + turma;
        }
        repo.salvarAluno(new Aluno(nome, matricula, turma));
        return "Aluno cadastrado com sucesso!";
    }

    public void marcarPresenca(String data, String matricula, String turma, boolean presente) {
        repo.salvarFrequencia(new Frequencia(data, matricula, turma, presente));
    }

    public List<Aluno> listarAlunos() {
        return repo.carregarAlunos();
    }

    public List<Frequencia> listarFrequencias() {
        return repo.carregarFrequencias();
    }

    public List<Turma> listarTurmas() {
        return repo.carregarTurmas();
    }


    public void listarPresencas(String filtro, String valor) {
        List<Frequencia> frequencias = listarFrequencias();
        List<Aluno> alunos = repo.carregarAlunos();

        Map<String, List<Frequencia>> agrupado = frequencias.stream()
                .filter(f -> filtro.equals("turma") ? f.getTurma().equals(valor) : f.getData().equals(valor))
                .collect(Collectors.groupingBy(Frequencia::getMatricula));

        agrupado.forEach((matricula, lista) -> {
            String nome = alunos.stream()
                    .filter(a -> a.getMatricula().equals(matricula))
                    .map(Aluno::getNome)
                    .findFirst()
                    .orElse("Desconhecido");
            long presencas = lista.stream().filter(Frequencia::isPresente).count();
            long total = lista.size();
            long faltas = total - presencas;
            double frequencia = total > 0 ? (presencas * 100.0) / total : 0;
            System.out.printf("Aluno: %s (Matrícula: %s), Presenças: %d, Faltas: %d, Frequência: %.2f%%\n",
                    nome, matricula, presencas, faltas, frequencia);
        });
    }


    public void relatorioPorAluno(String matricula) {
        List<Aluno> alunos = repo.carregarAlunos();
        List<Turma> turmas = repo.carregarTurmas();
        String nomeAluno = alunos.stream()
                .filter(a -> a.getMatricula().equals(matricula))
                .map(Aluno::getNome)
                .findFirst()
                .orElse("Desconhecido");

        List<Frequencia> frequencias = listarFrequencias().stream()
                .filter(f -> f.getMatricula().equals(matricula))
                .collect(Collectors.toList());

        if (frequencias.isEmpty()) {
            System.out.println("Nenhuma frequência registrada para o aluno " + nomeAluno);
            return;
        }

        System.out.println("Relatório de " + nomeAluno + " (Matrícula: " + matricula + "):");
        Map<String, List<Frequencia>> porTurma = frequencias.stream()
                .collect(Collectors.groupingBy(Frequencia::getTurma));

        porTurma.forEach((codTurma, lista) -> {
            String nomeTurma = turmas.stream()
                    .filter(t -> t.getCodigo().equals(codTurma))
                    .map(Turma::getNome)
                    .findFirst()
                    .orElse("Turma Desconhecida");
            long presencas = lista.stream().filter(Frequencia::isPresente).count();
            long total = lista.size();
            long faltas = total - presencas;
            double frequencia = total > 0 ? (presencas * 100.0) / total : 0;
            System.out.printf("Matéria: %s (Código: %s), Presenças: %d, Faltas: %d, Frequência: %.2f%%\n",
                    nomeTurma, codTurma, presencas, faltas, frequencia);
        });
    }
}