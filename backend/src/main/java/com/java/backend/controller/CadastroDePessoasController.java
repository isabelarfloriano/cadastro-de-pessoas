package com.java.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.java.backend.dao.PessoaDao;
import com.java.backend.exception.PessoaNotFoundException;
import com.java.backend.model.Pessoa;

@RestController
@RequestMapping("/pessoas")
public class CadastroDePessoasController {

  private final PessoaDao pessoaDao;

  @Autowired
  public CadastroDePessoasController(PessoaDao pessoaDao) {
      this.pessoaDao = pessoaDao;
  }

  @GetMapping
  public ResponseEntity<List<Pessoa>> listarPessoasCadastradas() {
      List<Pessoa> pessoas = pessoaDao.listarPessoas();
      if (pessoas.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(pessoas, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Pessoa> cadastrarPessoa(@RequestBody Pessoa pessoa) {
      pessoaDao.cadastrarPessoa(pessoa);
      return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarPessoa(@PathVariable int id) {
      try {
          pessoaDao.deletarPessoa(id);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (PessoaNotFoundException e) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  }
}
