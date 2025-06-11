package com.a3.projetofinal.funcionario.controller;

import com.a3.projetofinal.funcionario.entity.Funcionario;
import com.a3.projetofinal.funcionario.service.FuncionarioService;
import com.a3.projetofinal.tarefa.entity.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFuncionario(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(id);

        if (funcionario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
        }
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/{id}/tarefas")
    public ResponseEntity<?> listarTarefas(@PathVariable Long id) {
        List<Tarefa> tarefas = funcionarioService.listarTarefasDoFuncionario(id);

        if (tarefas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma tarefa encontrada para este funcionário.");
        }
        return ResponseEntity.ok(tarefas);
    }

    @PostMapping("/tarefas/concluir/{tarefaId}")
    public ResponseEntity<?> concluirTarefa(@PathVariable Long tarefaId) {
        Tarefa tarefa = funcionarioService.concluirTarefa(tarefaId);

        if (tarefa == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao concluir tarefa.");
        }
        return ResponseEntity.ok(tarefa);
    }
}
