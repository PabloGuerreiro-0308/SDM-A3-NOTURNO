package com.a3.projetofinal.funcionario.service;


import com.a3.projetofinal.funcionario.entity.Funcionario;
import com.a3.projetofinal.funcionario.repository.FuncionarioRepository;
import com.a3.projetofinal.tarefa.entity.Tarefa;
import com.a3.projetofinal.tarefa.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private FuncionarioRepository funcionarioRepository;
    private TarefaRepository tarefaRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, TarefaRepository tarefaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.tarefaRepository = tarefaRepository;
    }

    public Funcionario buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Funcionário não encontrado no sistema"));
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }
    public List<Tarefa> listarTarefasDoFuncionario(Long funcionarioId) {
        return tarefaRepository.findByFuncionarioId(funcionarioId);
    }

    public Tarefa concluirTarefa(Long tarefaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefa.setConcluida(true);
        return tarefaRepository.save(tarefa);

    }
}
