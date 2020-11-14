package br.com.rodolfo.teste.backend.controller;

import br.com.rodolfo.teste.backend.business.ClienteService;
import br.com.rodolfo.teste.backend.domain.Cliente;
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
    public ResponseEntity<ClienteRepresentation> cadastrar(@Valid ClienteRepresentation body) {
        log.info("cadastrar");
        Cliente novo = this.service.novo(mapper.mapper(body));
        return ResponseEntity.created(URI.create("http://localhost:8080/v1/cliente/" + novo.getId())).build();
    }

    @Override
    public ResponseEntity<ClienteRepresentation> getClientById(Long clienteId) {
        log.info("getClientById: {}", clienteId);

        try {
            final Optional<Cliente> optionalCliente = service.recuperarClienteById(clienteId);
            return ResponseEntity.ok(mapper.mapperToRepresentation(optionalCliente.orElseThrow()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<ClientApiResponseRepresentation> excluir(Long clienteId) {
        log.info("excluir: [clienteId]");

        service.excluir(clienteId);

        return ResponseEntity.ok(new ClientApiResponseRepresentation()
                .code(200)
                .message("Cliente deletado com sucesso!"));
    }

    //    @Override
//    public ResponseEntity<Void> confirmar(Long clienteId) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<ClientApiResponse> excluir(Long clienteId) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<Cliente> getClientById(Long clienteId) {
//        return null;
//    }
}
