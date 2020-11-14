package br.com.rodolfo.teste.backend.business;

import br.com.rodolfo.teste.backend.domain.Cliente;
import br.com.rodolfo.teste.backend.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Slf4j
public class ClienteService {


    @Autowired
    private ClienteRepository repository;

    public Cliente novo(@Valid Cliente obj) {
        log.info("novo: {}", obj);
        return repository.save(obj);
    }

    public Optional<Cliente> recuperarClienteById(Long clienteId) {
        log.info("recuperarClienteById: [clienteId]");
        return repository.findById(clienteId);
    }

    public void excluir(Long clienteId) {
        log.info("excluir: [clienteId]");

        repository.deleteById(clienteId);

    }
}
