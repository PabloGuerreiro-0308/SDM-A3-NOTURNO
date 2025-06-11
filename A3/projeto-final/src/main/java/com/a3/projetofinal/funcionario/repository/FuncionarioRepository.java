package com.a3.projetofinal.funcionario.repository;

import com.a3.projetofinal.funcionario.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario,Long> {
    List<Funcionario> findByTarefaIsEmpty();
    Funcionario findByCpf(String cpf);
}
