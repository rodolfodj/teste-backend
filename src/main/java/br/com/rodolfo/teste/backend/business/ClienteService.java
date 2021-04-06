package br.com.rodolfo.teste.backend.business;

import br.com.rodolfo.teste.backend.domain.Cliente;
import br.com.rodolfo.teste.backend.domain.Status;
import br.com.rodolfo.teste.backend.exception.BusinessException;
import br.com.rodolfo.teste.backend.exception.EmailJaCadastradoException;
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


    public Cliente novo(@Valid Cliente obj) throws BusinessException {
        log.info("novo: {}", obj);
        validarEmail(obj.getEmail());
        return repository.save(obj);
    }

    public Optional<Cliente> obterPorId(Long clienteId) throws BusinessException {
        log.info("recuperarClienteById: [clienteId]");
        final Optional<Cliente> cliente = repository.findById(clienteId);

        cliente.ifPresent(cliente1 -> {
            if (cliente1.isConfirmado())
                throw new BusinessException("Cliente não confirmado");
        });

        return cliente;
    }

    public void excluir(Long clienteId) throws BusinessException {
        log.info("excluir: [clienteId]");

        if (validarClienteConfirmado(clienteId))
            throw new BusinessException("Cliente não confirmado");

        repository.deleteById(clienteId);

    }

    public void confirmar(Long clienteId) {
        log.info("confirmacaoCliente: [{}]", clienteId);

        final Optional<Cliente> client = repository.findById(clienteId);

        Cliente c = client.orElseThrow(() -> new BusinessException("Cliente não existe"));

        if (c.isConfirmado()) {
            throw new BusinessException("cliente já confirmado");
        } else {
            c.setStatus(Status.ATIVO);
            repository.save(c);
        }
    }

    public void atualizar(Cliente body) {
        log.info("atualizar: [{}]", body);

        final Optional<Cliente> opt = obterPorId(body.getId());

        Cliente cliente = opt.orElseThrow(() -> new BusinessException("Cliente não existe"));

        if (!cliente.getEmail().equals(body.getEmail())) {
            validarEmail(body.getEmail());
        }

        if (cliente.isConfirmado()) {
            body.setStatus(Status.ATIVO);
            repository.save(body);
        } else {
            throw new BusinessException("Cliente não confirmado");
        }

    }

    private void validarEmail(String email) throws EmailJaCadastradoException {
        log.info("validarEmail: [email]");

        if (repository.findByEmail(email) != null) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }
    }

    private boolean validarClienteConfirmado(Long clienteId) throws BusinessException {
        log.info("validarClienteConfirmado: [clienteId]");

        return repository.findByIdAndStatus(clienteId, Status.PENDENTE.ordinal()) != null;
    }


}
