package com.a3.projetofinal.supervisor.controller;

import com.a3.projetofinal.supervisor.entity.Supervisor;
import com.a3.projetofinal.supervisor.service.SupervisorService;
import com.a3.projetofinal.tarefa.entity.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/supervisores")
public class SupervisorController {

    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @PostMapping("/tarefas")
    public Tarefa criarTarefa(@RequestBody Map<String, Object> dados) {
        String descricao = (String) dados.get("descricao");
        Long funcionarioId = Long.valueOf(dados.get("funcionarioId").toString()); // Convertendo corretamente

        return supervisorService.criarTarefa(descricao, funcionarioId);
    }
    @GetMapping("/tarefas/{funcionarioId}")
    public List<Tarefa> listarTarefasFuncionario(@PathVariable Long funcionarioId) {
        return supervisorService.listarTarefa(funcionarioId);
    }


}
