package br.com.rodolfo.teste.backend.controller;

import br.com.rodolfo.teste.backend.business.ClienteService;
import br.com.rodolfo.teste.backend.domain.Cliente;
import br.com.rodolfo.teste.backend.exception.BusinessException;
import br.com.rodolfo.teste.backend.mapper.ClienteMapper;
import br.com.rodolfo.teste.backend.repository.ClienteRepository;
import com.rodolfo.teste.backend.api.ClienteApi;
import com.rodolfo.teste.backend.api.domain.ClientApiResponseRepresentation;
import com.rodolfo.teste.backend.api.domain.ClienteRepresentation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping(value = "/v1")
public class ClienteController implements ClienteApi {

    private final ClienteService service;
    private final ClienteRepository repository;
    private final ClienteMapper mapper;


    @Override
    public ResponseEntity cadastrar(@Valid ClienteRepresentation body) {
        log.info("cadastrar");
        Cliente novo = null;
        try {
            novo = this.service.novo(mapper.mapperToDomain(body));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(new ClientApiResponseRepresentation()
                    .code(400)
                    .message(e.getMessage()));
        }
        return ResponseEntity.created(URI.create("http://localhost:8080/v1/cliente/" + novo.getId())).build();
    }

    @Override
    public ResponseEntity getClientById(Long clienteId) {
        log.info("getClientById: [{}]", clienteId);

        try {
            final Optional<Cliente> optionalCliente = service.obterPorId(clienteId);

            if (optionalCliente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(mapper.mapperToRepresentation(optionalCliente.get()));

        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(new ClientApiResponseRepresentation()
                    .code(422)
                    .message(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ClientApiResponseRepresentation().message(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ClientApiResponseRepresentation> excluir(Long clienteId) {
        log.info("excluir: [{}]", clienteId);

        try {
            service.excluir(clienteId);

            return ResponseEntity.ok(new ClientApiResponseRepresentation()
                    .code(200)
                    .message("Cliente deletado com sucesso!"));

        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(new ClientApiResponseRepresentation()
                    .code(400)
                    .message(e.getMessage()));
        }

    }

    @Override
    public ResponseEntity confirmar(Long clienteId) {

        service.confirmar(clienteId);

        return ResponseEntity.ok(new ClientApiResponseRepresentation()
                .code(200)
                .message("Confirmação realizada com sucesso!"));
    }

    @Override
    public ResponseEntity<ClientApiResponseRepresentation> atualizar(Long clienteId, @Valid ClienteRepresentation body) {

        service.atualizar(mapper.mapperToDomain(body));

        return ResponseEntity.ok(new ClientApiResponseRepresentation()
                .code(200)
                .message("Cliente atualizado com sucesso!"));
    }
}
