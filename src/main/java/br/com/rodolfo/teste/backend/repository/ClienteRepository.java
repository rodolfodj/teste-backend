package br.com.rodolfo.teste.backend.repository;

import br.com.rodolfo.teste.backend.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByEmail(String email);

    Cliente findByIdAndStatus(Long id, int status);
}
