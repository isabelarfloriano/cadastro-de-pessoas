package com.java.backend.model;

public class Pessoa {
  private int id;
  private String nome;
  private String email;
  private String telefone;
  private String profissao;

  public Pessoa() {}
  
  public Pessoa(String nome, String email, String telefone, String profissao) {
    this.nome = nome;
    this.email = email;
    this.telefone = telefone;
    this.profissao = profissao;
  }

  public Pessoa(int id, String nome, String email, String telefone, String profissao) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.telefone = telefone;
    this.profissao = profissao;
  }

  public int getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getEmail() {
    return email;
  }

  public String getTelefone() {
    return telefone;
  }

  public String getProfissao() {
    return profissao;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public void setProfissao(String profissao) {
    this.profissao = profissao;
  }
    
}
