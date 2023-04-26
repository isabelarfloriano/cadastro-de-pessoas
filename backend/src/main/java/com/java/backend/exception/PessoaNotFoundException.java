package com.java.backend.exception;

@SuppressWarnings("serial")
public class PessoaNotFoundException extends RuntimeException {

  public PessoaNotFoundException(int id) {
      super("Pessoa não encontrada com id " + id);
  }
}
