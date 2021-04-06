package br.com.rodolfo.teste.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Data
@Validated
@AllArgsConstructor
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
    private int status;

    public void setStatus(Status status) {
        this.status = status.ordinal();
    }

    public Status getStatus() {
        return Status.values()[status];
    }

    public boolean isConfirmado() {
        return !getStatus().equals(Status.PENDENTE);
    }

    public boolean isAtivo() {
        return getStatus().equals(Status.ATIVO);
    }
}
