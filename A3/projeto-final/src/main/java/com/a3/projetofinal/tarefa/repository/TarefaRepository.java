package com.a3.projetofinal.tarefa.repository;


import com.a3.projetofinal.funcionario.entity.Funcionario;

import com.a3.projetofinal.tarefa.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa t WHERE t.funcionario.id = :funcionarioId")
    List<Tarefa> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);


    List<Tarefa> findByConcluidaFalse();
}
