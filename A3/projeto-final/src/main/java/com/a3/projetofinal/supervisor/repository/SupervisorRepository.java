package com.a3.projetofinal.supervisor.repository;

import com.a3.projetofinal.supervisor.entity.Supervisor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
   // Supervisor findByCpf(String cpf);
}
