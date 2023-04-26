package com.java.backend.dao;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import com.google.gson.Gson;
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
        String sql = "SELECT * FROM pessoas_cadastradas";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Pessoa pessoa = new Gson().fromJson(rs.getString("json"), Pessoa.class);
            pessoa.setId(rs.getInt("id"));
            return pessoa;
        });
    }

    public void cadastrarPessoa(Pessoa pessoa) {
        int id = gerarId();
        pessoa.setId(id);
        String json = new Gson().toJson(pessoa);
        String sql = "INSERT INTO pessoas_cadastradas (id, json) VALUES (?, ?)";
        jdbcTemplate.update(sql, id, json);
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
