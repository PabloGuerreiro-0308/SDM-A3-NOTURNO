package com.a3.projetofinal.gerente.controller;

import com.a3.projetofinal.funcionario.entity.Funcionario;
import com.a3.projetofinal.gerente.service.GerenteService;
import com.a3.projetofinal.tarefa.entity.Tarefa;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/gerentes")
public class GerenteController {
    private final GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    // 🔹 Relatório: Todas as tarefas cadastradas
    @GetMapping("/relatorios/todos")
    public ResponseEntity<List<Tarefa>> listarTodas() {
        List<Tarefa> tarefas = gerenteService.listarTodasTarefas();
        return tarefas.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of())
                : ResponseEntity.ok(tarefas);
    }

    // 🔹 Relatório: Tarefas pendentes
    @GetMapping("/relatorios/pendentes")
    public ResponseEntity<List<Tarefa>> listarPendentes() {
        List<Tarefa> tarefasPendentes = gerenteService.listarTarefasPendentes();
        return tarefasPendentes.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of())
                : ResponseEntity.ok(tarefasPendentes);
    }

    // 🔹 Relatório: Funcionários sem tarefas pendentes
    @GetMapping("/relatorios/funcionarios-livres")
    public ResponseEntity<List<Funcionario>> listarFuncionariosSemTarefa() {
        List<Funcionario> funcionarios = gerenteService.listarFuncionariosSemTarefa();
        return funcionarios.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of())
                : ResponseEntity.ok(funcionarios);
    }
}
