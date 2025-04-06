package View;

import Controller.SistemaControle;
import java.util.Scanner;

public class Tela {
    private SistemaControle controller = new SistemaControle();
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        int opcao;
        do {
            System.out.println("\n--- Sistema de Controle de Frequência ---");
            System.out.println("1. Cadastrar Turma");
            System.out.println("2. Cadastrar Aluno");
            System.out.println("3. Marcar Presença");
            System.out.println("4. Listar Presenças");
            System.out.println("5. Relatório por Aluno");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarTurma();
                case 2 -> cadastrarAluno();
                case 3 -> marcarPresenca();
                case 4 -> listarPresencas();
                case 5 -> relatorioPorAluno();
            }
        } while (opcao != 0);
    }

    private void cadastrarTurma() {
        System.out.print("Nome da turma: ");
        String nome = scanner.nextLine();
        System.out.print("Código da turma: ");
        String codigo = scanner.nextLine();
        String resultado = controller.cadastrarTurma(nome, codigo);
        System.out.println(resultado);
    }

    private void cadastrarAluno() {
        System.out.print("Nome do aluno: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.println("Turmas disponíveis:");
        controller.listarTurmas().forEach(t -> System.out.println("Código: " + t.getCodigo() + ", Nome: " + t.getNome()));
        System.out.print("Código da turma: ");
        String turma = scanner.nextLine();
        String resultado = controller.cadastrarAluno(nome, matricula, turma);
        System.out.println(resultado);
    }

    private void marcarPresenca() {
        System.out.print("Data (dd/mm/yyyy): ");
        String data = scanner.nextLine();
        System.out.print("Matrícula do aluno: ");
        String matricula = scanner.nextLine();
        System.out.println("Turmas disponíveis:");
        controller.listarTurmas().forEach(t -> System.out.println("Código: " + t.getCodigo() + ", Nome: " + t.getNome()));
        System.out.print("Código da turma: ");
        String turma = scanner.nextLine();
        System.out.print("O aluno está presente? (sim/não): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        boolean presente = resposta.equals("sim");
        controller.marcarPresenca(data, matricula, turma, presente);
        System.out.println("Presença marcada!");
    }

    private void listarPresencas() {
        System.out.print("Filtrar por (turma/data): ");
        String filtro = scanner.nextLine();
        System.out.print("Valor (código da turma ou data): ");
        String valor = scanner.nextLine();
        controller.listarPresencas(filtro, valor);
    }

    private void relatorioPorAluno() {
        System.out.println("Alunos cadastrados:");
        controller.listarAlunos().forEach(aluno ->
                System.out.println("Matrícula: " + aluno.getMatricula() + ", Nome: " + aluno.getNome()));
        System.out.print("Digite a matrícula do aluno: ");
        String matricula = scanner.nextLine();
        controller.relatorioPorAluno(matricula);
    }
}