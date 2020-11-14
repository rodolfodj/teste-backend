package br.com.rodolfo.teste.backend.mapper;

import br.com.rodolfo.teste.backend.domain.Cliente;
import com.rodolfo.teste.backend.api.domain.ClienteRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente mapper(ClienteRepresentation cliente);

    ClienteRepresentation mapperToRepresentation(Cliente cliente);
}
