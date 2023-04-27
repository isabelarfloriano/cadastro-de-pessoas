package com.java.backend;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;
import com.java.backend.controller.CadastroDePessoasController;
import com.java.backend.dao.PessoaDao;
import com.java.backend.exception.PessoaNotFoundException;
import com.java.backend.model.Pessoa;

public class CadastroDePessoasApplicationTests {

  private CadastroDePessoasController cadastroDePessoasController;
  private PessoaDao pessoaDao;

  @Mock
  private Gson gson;

  @BeforeEach
  public void setUp() {
      MockitoAnnotations.openMocks(this);
      pessoaDao = Mockito.mock(PessoaDao.class);
      cadastroDePessoasController = new CadastroDePessoasController(pessoaDao, gson);
  }

  @Test
  public void testListarPessoasCadastradas() throws PessoaNotFoundException {
      List<Pessoa> pessoas = new ArrayList<>();
      Pessoa pessoa = new Pessoa();
      pessoa.setNome("Fulano");
      pessoa.setTelefone("30");
      pessoas.add(pessoa);
      String json = "{\"nome\":\"Fulano\",\"idade\":\"30\"}";

      when(pessoaDao.listarPessoas()).thenReturn(pessoas);
      when(gson.toJson(pessoas)).thenReturn(json);

      ResponseEntity<String> responseEntity = cadastroDePessoasController.listarPessoasCadastradas();

      Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      Assertions.assertEquals(json, responseEntity.getBody());
  }

  @Test
  public void testListarPessoasCadastradasSemPessoas() throws PessoaNotFoundException {
      List<Pessoa> pessoas = new ArrayList<>();

      when(pessoaDao.listarPessoas()).thenReturn(pessoas);

      ResponseEntity<String> responseEntity = cadastroDePessoasController.listarPessoasCadastradas();

      Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
  }
  
  @Test
  public void testCadastrarPessoa() {
      Pessoa pessoa = new Pessoa();
      pessoa.setNome("Fulano");
      pessoa.setTelefone("30");

      ResponseEntity<Pessoa> responseEntity = cadastroDePessoasController.cadastrarPessoa(pessoa);

      Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }
  
  @Test
  public void testDeletarPessoa() throws PessoaNotFoundException {
      int pessoaId = 1;

      ResponseEntity<Void> responseEntity = cadastroDePessoasController.deletarPessoa(pessoaId);

      Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
  }

  @Test
  public void testDeletarPessoaInexistente() throws PessoaNotFoundException {
      int pessoaId = 999;

      doThrow(PessoaNotFoundException.class).when(pessoaDao).deletarPessoa(pessoaId);

      ResponseEntity<Void> responseEntity = cadastroDePessoasController.deletarPessoa(pessoaId);

      Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }
}
