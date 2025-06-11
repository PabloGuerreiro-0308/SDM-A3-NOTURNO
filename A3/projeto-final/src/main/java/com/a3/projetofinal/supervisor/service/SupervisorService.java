package com.a3.projetofinal.supervisor.service;


import com.a3.projetofinal.funcionario.entity.Funcionario;
import com.a3.projetofinal.funcionario.repository.FuncionarioRepository;
import com.a3.projetofinal.supervisor.entity.Supervisor;
import com.a3.projetofinal.supervisor.repository.SupervisorRepository;
import com.a3.projetofinal.tarefa.entity.Tarefa;
import com.a3.projetofinal.tarefa.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupervisorService {
    private TarefaRepository tarefaRepository;
    private FuncionarioRepository funcionarioRepository;
    private SupervisorRepository supervisorRepository;

    @Autowired
    public SupervisorService(TarefaRepository tarefaRepository, FuncionarioRepository funcionarioRepository, SupervisorRepository supervisorRepository) {
        this.tarefaRepository = tarefaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.supervisorRepository = supervisorRepository;
    }

    public Tarefa criarTarefa(String descricao,long idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(()-> new RuntimeException("Funcionário não encontrado"));
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao(descricao);
        tarefa.setFuncionario(funcionario);
        tarefa.setConcluida(false);
        tarefaRepository.save(tarefa);
        return tarefaRepository.save(tarefa);
    }


    public List<Tarefa> listarTarefa(Long idFuncionario) {
        return tarefaRepository.findByFuncionarioId(idFuncionario);
    }

}
