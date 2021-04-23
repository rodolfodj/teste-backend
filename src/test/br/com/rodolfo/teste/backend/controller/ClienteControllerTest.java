package br.com.rodolfo.teste.backend.controller;

import br.com.rodolfo.teste.backend.business.ClienteService;
import br.com.rodolfo.teste.backend.exception.BusinessException;
import br.com.rodolfo.teste.backend.mapper.ClienteMapper;
import br.com.rodolfo.teste.backend.repository.ClienteRepository;
import com.rodolfo.teste.backend.api.domain.ClientApiResponseRepresentation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ClienteController.class})
@ExtendWith(SpringExtension.class)
class ClienteControllerTest {
    @Autowired
    private ClienteController clienteController;

    @MockBean
    private ClienteMapper clienteMapper;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ClienteService clienteService;

    @Test
    void testGetClientById() {
        ResponseEntity actualClientById = this.clienteController.getClientById(123L);
        assertNull(actualClientById.getBody());
        assertEquals("<404 NOT_FOUND Not Found,[]>", actualClientById.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualClientById.getStatusCode());
    }

    @Test
    void testExcluir() throws BusinessException {
        doNothing().when(this.clienteService).excluir((Long) any());
        ResponseEntity<ClientApiResponseRepresentation> actualExcluirResult = this.clienteController.excluir(123L);
        assertEquals("<200 OK OK,class ClientApiResponseRepresentation {\n" + "    code: 200\n" + "    type: null\n"
                + "    message: Cliente deletado com sucesso!\n" + "},[]>", actualExcluirResult.toString());
        assertEquals(HttpStatus.OK, actualExcluirResult.getStatusCode());
        assertTrue(actualExcluirResult.hasBody());
        ClientApiResponseRepresentation body = actualExcluirResult.getBody();
        assertEquals(200, body.getCode().intValue());
        assertEquals("Cliente deletado com sucesso!", body.getMessage());
        assertNull(body.getType());
        verify(this.clienteService).excluir((Long) any());
    }

    @Test
    void testExcluir2() throws BusinessException {
        doThrow(new BusinessException("Msg")).when(this.clienteService).excluir((Long) any());
        ResponseEntity<ClientApiResponseRepresentation> actualExcluirResult = this.clienteController.excluir(123L);
        assertEquals("<400 BAD_REQUEST Bad Request,class ClientApiResponseRepresentation {\n" + "    code: 400\n"
                + "    type: null\n" + "    message: Msg\n" + "},[]>", actualExcluirResult.toString());
        assertEquals(HttpStatus.BAD_REQUEST, actualExcluirResult.getStatusCode());
        assertTrue(actualExcluirResult.hasBody());
        ClientApiResponseRepresentation body = actualExcluirResult.getBody();
        assertEquals(400, body.getCode().intValue());
        assertEquals("Msg", body.getMessage());
        assertNull(body.getType());
        verify(this.clienteService).excluir((Long) any());
    }

    @Test
    void testExcluir3() throws BusinessException {
        BusinessException businessException = mock(BusinessException.class);
        when(businessException.getMessage()).thenReturn("foo");
        doThrow(businessException).when(this.clienteService).excluir((Long) any());
        ResponseEntity<ClientApiResponseRepresentation> actualExcluirResult = this.clienteController.excluir(123L);
        assertEquals("<400 BAD_REQUEST Bad Request,class ClientApiResponseRepresentation {\n" + "    code: 400\n"
                + "    type: null\n" + "    message: foo\n" + "},[]>", actualExcluirResult.toString());
        assertEquals(HttpStatus.BAD_REQUEST, actualExcluirResult.getStatusCode());
        assertTrue(actualExcluirResult.hasBody());
        ClientApiResponseRepresentation body = actualExcluirResult.getBody();
        assertEquals(400, body.getCode().intValue());
        assertEquals("foo", body.getMessage());
        assertNull(body.getType());
//        verify(doThrow(businessException)).getMessage();
        verify(this.clienteService).excluir((Long) any());
    }

    @Test
    void testConfirmar() {
        doNothing().when(this.clienteService).confirmar((Long) any());
        ResponseEntity actualConfirmarResult = this.clienteController.confirmar(123L);
        assertEquals("<200 OK OK,class ClientApiResponseRepresentation {\n" + "    code: 200\n" + "    type: null\n"
                + "    message: Confirmação realizada com sucesso!\n" + "},[]>", actualConfirmarResult.toString());
        assertTrue(actualConfirmarResult.hasBody());
        assertEquals(HttpStatus.OK, actualConfirmarResult.getStatusCode());
        assertEquals("Confirmação realizada com sucesso!",
                ((ClientApiResponseRepresentation) actualConfirmarResult.getBody()).getMessage());
        assertEquals(200, ((ClientApiResponseRepresentation) actualConfirmarResult.getBody()).getCode().intValue());
        assertNull(((ClientApiResponseRepresentation) actualConfirmarResult.getBody()).getType());
        verify(this.clienteService).confirmar((Long) any());
    }
}

