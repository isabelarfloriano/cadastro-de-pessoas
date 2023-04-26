package com.java.backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.java.backend.dao.PessoaDao;
import com.java.backend.model.Pessoa;

@RestController
@RequestMapping("/pessoas")
public class CadastroDePessoasController {

  private final PessoaDao pessoaDao;

  public CadastroDePessoasController(PessoaDao pessoaDao) {
      this.pessoaDao = pessoaDao;
  }

  @GetMapping
  public List<Pessoa> listarPessoasCadastradas() {
      return pessoaDao.listarPessoas();
  }

  @PostMapping
  public void cadastrarPessoa(@RequestBody Pessoa pessoa) {
      pessoaDao.cadastrarPessoa(pessoa);
  }

  @DeleteMapping("/{id}")
  public void deletarPessoa(@PathVariable("id") int id) {
      pessoaDao.deletarPessoa(id);
  }
}
