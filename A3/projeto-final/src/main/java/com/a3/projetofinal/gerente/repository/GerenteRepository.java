package com.a3.projetofinal.gerente.repository;

import com.a3.projetofinal.gerente.entity.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, Long> {

}
