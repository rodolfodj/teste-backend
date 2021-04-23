package br.com.rodolfo.teste.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id", "email"}))
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String telefone;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Endereco endereco;

    public boolean isConfirmado() {
        return !getStatus().equals(Status.PENDENTE);
    }

    public boolean isAtivo() {
        return getStatus().equals(Status.ATIVO);
    }
}
