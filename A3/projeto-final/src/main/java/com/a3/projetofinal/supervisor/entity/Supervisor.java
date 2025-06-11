package com.a3.projetofinal.supervisor.entity;


import com.a3.projetofinal.cargo.Cargos;
import com.a3.projetofinal.cliente.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Supervisor extends Cliente implements Serializable {
    @Enumerated(EnumType.STRING)
    private Cargos cargo =Cargos.CARGO_SUPERVISOR;

    @Column(name = "salario")
    private Double salario;

}
