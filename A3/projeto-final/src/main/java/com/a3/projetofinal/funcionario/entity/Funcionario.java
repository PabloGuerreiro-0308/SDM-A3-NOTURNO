package com.a3.projetofinal.funcionario.entity;


import com.a3.projetofinal.cargo.Cargos;
import com.a3.projetofinal.cliente.Cliente;
import com.a3.projetofinal.tarefa.entity.Tarefa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "funcionario")
public class Funcionario extends Cliente implements Serializable{

    @Enumerated(EnumType.STRING)
    private Cargos cargo =Cargos.CARGO_FUNCIONARIO;

    @Column(name = "salario")
    private Double salario;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario")
    private List<Tarefa> tarefa;

}
