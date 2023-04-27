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
  .then(response => {
    if (response.ok) {
      const pessoaJson = response.json();
      pessoas.push(pessoaJson);
      atualizarTabela(pessoas);
    }
  })
  .catch(error => console.error(error));
}

form.addEventListener('submit', (event) => {
  event.preventDefault();
  const nome = document.getElementById('nome').value;
  const email = document.getElementById('email').value;
  const telefone = document.getElementById('telefone').value;
  const profissao = document.getElementById('profissao').value;
  const pessoa = { nome, email, telefone, profissao };
  adicionarPessoa(pessoa);
  form.reset();
});

function atualizarTabela(pessoas) {
  const tabela = document.getElementById("tabela");
  tabela.innerHTML = "";

  pessoas.forEach((pessoa) => {
    const row = tabela.insertRow();

    const idCell = row.insertCell();
    idCell.innerHTML = pessoa.id;

    const nomeCell = row.insertCell();
    nomeCell.innerHTML = pessoa.nome;

    const emailCell = row.insertCell();
    emailCell.innerHTML = pessoa.email;

    const telefoneCell = row.insertCell();
    telefoneCell.innerHTML = pessoa.telefone;

    const profissaoCell = row.insertCell();
    profissaoCell.innerHTML = pessoa.profissao;

    const acaoCell = row.insertCell();
    const removerButton = document.createElement("button");
    removerButton.innerHTML = "Remover";
    removerButton.addEventListener("click", () => {
      removerPessoa(pessoa.id);
    });
    acaoCell.appendChild(removerButton);
  });
}


function removerPessoa(id) {
  fetch(`http://localhost:8080/pessoas/${id}`, {
    method: 'DELETE'
  })
  .then(response => {
    if (response.status === 204) {
      const row = document.querySelector(`button[data-id="${id}"]`).parentNode.parentNode;
      row.remove();
    } else if (response.status === 404) {
      throw new Error('Pessoa não encontrada.');
    } else {
      throw new Error('Erro ao deletar pessoa.');
    }
  })
  .catch(error => console.error(error));
}


tabela.addEventListener('click', (event) => {
  if (event.target.classList.contains('remover')) {
    const id = event.target.dataset.id;
    removerPessoa(id);
  }
});

window.onload = () => { 
  listarPessoas(pessoas);
 };
