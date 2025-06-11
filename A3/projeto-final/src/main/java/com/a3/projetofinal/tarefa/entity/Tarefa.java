package com.a3.projetofinal.tarefa.entity;


import com.a3.projetofinal.funcionario.entity.Funcionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Boolean concluida = false;

    @ManyToOne
    @JoinColumn(name = "funcionario_id",nullable = false)
    private Funcionario funcionario;

    public Long getFuncionarioId() {
        return funcionario != null ? funcionario.getId() : null;
    }
}
