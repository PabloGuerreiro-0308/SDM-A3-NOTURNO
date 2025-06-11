package com.a3.projetofinal.gerente.service;

import com.a3.projetofinal.funcionario.entity.Funcionario;
import com.a3.projetofinal.funcionario.repository.FuncionarioRepository;
import com.a3.projetofinal.tarefa.entity.Tarefa;
import com.a3.projetofinal.tarefa.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GerenteService {
    private final TarefaRepository tarefaRepository;
    private final FuncionarioRepository funcionarioRepository;

    public GerenteService(TarefaRepository tarefaRepository, FuncionarioRepository funcionarioRepository) {
        this.tarefaRepository = tarefaRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    // 🔹 Todas as tarefas cadastradas
    public List<Tarefa> listarTodasTarefas() {
        return tarefaRepository.findAll();
    }

    // 🔹 Tarefas pendentes
    public List<Tarefa> listarTarefasPendentes() {
        return tarefaRepository.findByConcluidaFalse();
    }

    // 🔹 Funcionários sem tarefas pendentes
    public List<Funcionario> listarFuncionariosSemTarefa() {
        List<Tarefa> tarefasPendentes = tarefaRepository.findByConcluidaFalse();
        List<Long> idsComPendencias = tarefasPendentes.stream()
                .map(tarefa -> tarefa.getFuncionario().getId()) // Correção no acesso ao ID
                .toList();

        return funcionarioRepository.findAll().stream()
                .filter(func -> !idsComPendencias.contains(func.getId()))
                .collect(Collectors.toList());
    }
}
