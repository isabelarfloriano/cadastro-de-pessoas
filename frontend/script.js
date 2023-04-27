const form = document.getElementById('form');
const tabela = document.getElementById('tabela');

let pessoas = [];

async function listarPessoas() {
  const response = await fetch('http://localhost:8080/pessoas', {
    method: 'GET',
  });
  if (response.ok) {
    const pessoasJson = await response.json();
    pessoas = pessoasJson;
    atualizarTabela(pessoas);
  } else {
    console.error('Erro ao buscar pessoas: ', response.status);
  }
}

async function adicionarPessoa(pessoa) {
  await fetch('http://localhost:8080/pessoas', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(pessoa)
  })
  .then(async response => {
    if (response.ok) {
      const pessoaJson = await response.json();
      pessoas.push(pessoaJson);
      atualizarTabela(pessoas);
    }
  })
  .catch(error => console.error(error));
}

form.addEventListener('submit', async (event) => {
  event.preventDefault();
  const nome = document.getElementById('nome').value;
  const email = document.getElementById('email').value;
  const telefone = document.getElementById('telefone').value;
  const profissao = document.getElementById('profissao').value;
  const pessoa = { nome, email, telefone, profissao };
  await adicionarPessoa(pessoa);
  form.reset();
});

function atualizarTabela(pessoas) {
  const tabela = document.getElementById("tabela");
  tabela.innerHTML = "";

  pessoas.map((pessoa) => {
    const row = tabela.insertRow();

    const idCell = row.insertCell();
    idCell.classList.add("id");
    idCell.innerHTML = pessoa.id;

    const nomeCell = row.insertCell();
    nomeCell.classList.add("nome");
    nomeCell.innerHTML = pessoa.nome;

    const emailCell = row.insertCell();
    emailCell.classList.add("email");
    emailCell.innerHTML = pessoa.email;

    const telefoneCell = row.insertCell();
    telefoneCell.classList.add("telefone");
    telefoneCell.innerHTML = pessoa.telefone;

    const profissaoCell = row.insertCell();
    profissaoCell.classList.add("profissao");
    profissaoCell.innerHTML = pessoa.profissao;

    const acaoCell = row.insertCell();
    acaoCell.classList.add("acao");
    const removerButton = document.createElement("button");
    removerButton.classList.add("remover");
    removerButton.dataset.id = pessoa.id;
    removerButton.innerHTML = "Remover";
    acaoCell.appendChild(removerButton);
  });
}

async function removerPessoa(id) {
  const response = await fetch(`http://localhost:8080/pessoas/${id}`, {
    method: 'DELETE'
  });
  
  if (response.status === 204) {
    await listarPessoas(pessoas);
  } else if (response.status === 404) {
    throw new Error('Pessoa não encontrada.');
  } else {
    throw new Error('Erro ao deletar pessoa.');
  }
}

tabela.addEventListener('click', async (event) => {
  if (event.target.classList.contains('remover')) {
    const id = event.target.dataset.id;
    await removerPessoa(id);
  }
});


window.onload = () => { 
  listarPessoas(pessoas);
 };
