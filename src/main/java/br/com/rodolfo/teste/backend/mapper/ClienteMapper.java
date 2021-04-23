package br.com.rodolfo.teste.backend.mapper;

import br.com.rodolfo.teste.backend.domain.Cliente;
import com.rodolfo.teste.backend.api.domain.ClienteRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(source = "cliente.endereco", target = "endereco")
    Cliente mapperToDomain(ClienteRepresentation cliente);

    ClienteRepresentation mapperToRepresentation(Cliente cliente);
}
