package com.java.backend.dao;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.java.backend.config.DataBaseConfig;
import com.java.backend.model.Pessoa;

@Component
public class PessoaDao {

  private final JdbcTemplate jdbcTemplate;
  private final String createTableSql;

  @Autowired
  public PessoaDao(DataSource dataSource, DataBaseConfig dataBaseConfig) {
      this.jdbcTemplate = new JdbcTemplate(dataSource);
      this.createTableSql = dataBaseConfig.criaTabelaSql();
      init();
  }

  private void init() {
      jdbcTemplate.execute(createTableSql);
  }

    public List<Pessoa> listarPessoas() {
      String sql = "SELECT id, nome, email, telefone, profissao FROM pessoas_cadastradas";
      return jdbcTemplate.query(sql, (rs, rowNum) -> {
          Pessoa pessoa = new Pessoa();
          pessoa.setId(rs.getInt("id"));
          pessoa.setNome(rs.getString("nome"));
          pessoa.setEmail(rs.getString("email"));
          pessoa.setTelefone(rs.getString("telefone"));
          pessoa.setProfissao(rs.getString("profissao"));
          return pessoa;
      });
    }

    public void cadastrarPessoa(Pessoa pessoa) {
        int id = gerarId();
        pessoa.setId(id);
        String sql = "INSERT INTO pessoas_cadastradas (id, nome, email, telefone, profissao) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(), pessoa.getProfissao());
    }

    public void deletarPessoa(int id) {
        String sql = "DELETE FROM pessoas_cadastradas WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private int gerarId() {
        String sql = "SELECT MAX(id) FROM pessoas_cadastradas";
        Integer maxId = jdbcTemplate.queryForObject(sql, Integer.class);
        if (maxId == null) {
            return 1;
        }
        return maxId + 1;
    }
}
