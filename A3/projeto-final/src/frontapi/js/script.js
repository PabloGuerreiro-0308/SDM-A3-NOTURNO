document.addEventListener('DOMContentLoaded', () => {


  const API_URL = 'http://localhost:8080';


  const loginForm = document.getElementById('login-form');
  if (loginForm) {
    loginForm.addEventListener('submit', async (event) => {
      event.preventDefault();
      const selectedRole = document.getElementById('role-select').value;
          switch (selectedRole) {
            case 'Funcionario':
              window.location.href = 'funcionario.html';
              break;
            case 'Supervisor':
              window.location.href = 'supervisor.html';
              break;
            case 'Gerente':
              window.location.href = 'gerente.html';
              break;
          }
    });
  }


  const taskListContainer = document.getElementById('task-list-container');
  if (taskListContainer) {
    const loggedInUserId = localStorage.getItem('loggedInUserId');
    const noTasksMessage = document.getElementById('no-tasks-message');

    if (!loggedInUserId) {
      alert('Você não está logado. Redirecionando para a página de login.');
      window.location.href = 'index.html';
      return;
    }

    const fetchAndRenderTasks = async () => {
      try {
        const response = await fetch(`${API_URL}/funcionarios/${loggedInUserId}/tarefas`);

        if (response.status === 404) {
          taskListContainer.innerHTML = '';
          noTasksMessage.classList.remove('hidden');
          return;
        }
        if (!response.ok) throw new Error('Erro ao buscar tarefas.');

        const tasks = await response.json();
        taskListContainer.innerHTML = '';

        const pendingTasks = tasks.filter(task => !task.concluida);

        if (pendingTasks.length === 0) {
          noTasksMessage.classList.remove('hidden');
        } else {
          noTasksMessage.classList.add('hidden');
          pendingTasks.forEach(task => {
            const card = document.createElement('div');
            card.className = 'task-card';
            card.setAttribute('data-task-id', task.id);
            card.innerHTML = `
                            <h3>${task.descricao || 'Título não definido'}</h3>
                            <p><strong>Status:</strong> ${task.concluida ? 'Concluída' : 'Pendente'}</p>
                            <p><strong>ID Tarefa:</strong> ${task.id}</p>
                            <button class="btn btn-complete">Concluir tarefa</button>
                        `;
            taskListContainer.appendChild(card);
          });
        }
      } catch (error) {
        console.error('Erro ao carregar tarefas:', error);
        taskListContainer.innerHTML = '<p>Erro ao carregar as tarefas. Tente novamente mais tarde.</p>';
      }
    };

    taskListContainer.addEventListener('click', async (event) => {
      if (event.target.classList.contains('btn-complete')) {
        const card = event.target.closest('.task-card');
        const tarefaId = card.getAttribute('data-task-id');

        try {
          const response = await fetch(`${API_URL}/funcionarios/tarefas/concluir/${tarefaId}`, {
            method: 'POST'
          });
          if (!response.ok) throw new Error('Falha ao concluir a tarefa.');

          alert('Tarefa concluída com sucesso!');
          card.remove();

          if (taskListContainer.childElementCount === 0) {
            noTasksMessage.classList.remove('hidden');
          }
        } catch (error) {
          console.error('Erro ao concluir tarefa:', error);
          alert('Não foi possível concluir a tarefa.');
        }
      }
    });

    fetchAndRenderTasks();
  }


  const createTaskForm = document.getElementById('create-task-form');
  if (createTaskForm) {
    createTaskForm.addEventListener('submit', async (event) => {
      event.preventDefault();

      const descricao = document.getElementById('descricao').value;
      const funcionarioId = document.getElementById('funcionarioId').value;

      const taskData = {
        descricao: descricao,
        funcionarioId: parseInt(funcionarioId)
      };

      try {
        const response = await fetch(`${API_URL}/supervisores/tarefas`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(taskData)
        });

        if (response.ok) {
          alert('Tarefa cadastrada com sucesso!');
          createTaskForm.reset();
        } else {
          const errorBody = await response.text();
          console.error('Erro do servidor:', errorBody);
          alert(`Erro ao cadastrar tarefa. Verifique se o ID do funcionário é válido.`);
        }

      } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Não foi possível conectar ao servidor. Verifique se o backend está rodando.');
      }
    });
  }


  const searchTasksForm = document.getElementById('search-tasks-form');
  if (searchTasksForm) {
    const resultsContainer = document.getElementById('task-results-container');
    const resultsMessage = document.getElementById('search-results-message');

    searchTasksForm.addEventListener('submit', async (event) => {
      event.preventDefault();

      const funcionarioId = document.getElementById('search-funcionarioId').value;
      if (!funcionarioId) {
        alert('Por favor, digite um ID de funcionário.');
        return;
      }

      resultsContainer.innerHTML = '';
      resultsMessage.textContent = 'Buscando...';
      resultsMessage.classList.remove('hidden');

      try {
        const response = await fetch(`${API_URL}/supervisores/tarefas/${funcionarioId}`);

        if (response.status === 404 || !response.ok) {
          resultsMessage.textContent = 'Nenhuma tarefa encontrada para este funcionário ou o ID é inválido.';
          return;
        }

        const tasks = await response.json();

        if (tasks.length === 0) {
          resultsMessage.textContent = 'Este funcionário não possui tarefas cadastradas.';
          return;
        }

        resultsMessage.classList.add('hidden');

        tasks.forEach(task => {
          const card = document.createElement('div');
          card.className = 'task-card';

          const funcionarioNome = task.funcionario ? task.funcionario.nome : 'N/A';
          const status = task.concluida ? 'Concluída' : 'Pendente';

          card.innerHTML = `
                        <p><strong>Tarefa:</strong> ${task.descricao}</p>
                        <p><strong>Funcionário:</strong> ${funcionarioNome}</p>
                        <p><strong>Status:</strong> ${status}</p>
                    `;
          resultsContainer.appendChild(card);
        });

      } catch (error) {
        console.error('Erro ao buscar tarefas:', error);
        resultsMessage.textContent = 'Erro ao conectar ao servidor. Tente novamente.';
      }
    });
  }


  const reportForm = document.getElementById('report-form');
  if (reportForm) {
    const reportTypeSelect = document.getElementById('report-type');
    const resultsContainer = document.getElementById('report-results-container');

    const renderTaskTable = (tasks) => {
      if (tasks.length === 0) {
        resultsContainer.innerHTML = '<p>Nenhum dado encontrado para este relatório.</p>';
        return;
      }
      let tableHTML = '<table class="report-table"><thead><tr><th>ID Tarefa</th><th>Descrição</th><th>Funcionário</th><th>Status</th></tr></thead><tbody>';
      tasks.forEach(task => {
        tableHTML += `
                    <tr>
                        <td>${task.id}</td>
                        <td>${task.descricao}</td>
                        <td>${task.funcionario ? task.funcionario.nome : 'N/A'}</td>
                        <td>${task.concluida ? 'Concluída' : 'Pendente'}</td>
                    </tr>
                `;
      });
      tableHTML += '</tbody></table>';
      resultsContainer.innerHTML = tableHTML;
    };

    const renderFuncionarioTable = (funcionarios) => {
      if (funcionarios.length === 0) {
        resultsContainer.innerHTML = '<p>Nenhum dado encontrado para este relatório.</p>';
        return;
      }
      let tableHTML = '<table class="report-table"><thead><tr><th>ID Funcionário</th><th>Nome</th><th>CPF</th><th>Cargo</th></tr></thead><tbody>';
      funcionarios.forEach(func => {
        tableHTML += `
                    <tr>
                        <td>${func.id}</td>
                        <td>${func.nome}</td>
                        <td>${func.cpf}</td>
                        <td>${func.cargo}</td>
                    </tr>
                `;
      });
      tableHTML += '</tbody></table>';
      resultsContainer.innerHTML = tableHTML;
    };

    reportForm.addEventListener('submit', async (event) => {
      event.preventDefault();
      const reportType = reportTypeSelect.value;
      let endpoint = '';
      let renderFunction;

      switch (reportType) {
        case 'todas-tarefas':
          endpoint = `${API_URL}/gerentes/relatorios/todos`;
          renderFunction = renderTaskTable;
          break;
        case 'tarefas-pendentes':
          endpoint = `${API_URL}/gerentes/relatorios/pendentes`;
          renderFunction = renderTaskTable;
          break;
        case 'funcionarios-livres':
          endpoint = `${API_URL}/gerentes/relatorios/funcionarios-livres`;
          renderFunction = renderFuncionarioTable;
          break;
        default:
          alert('Tipo de relatório inválido.');
          return;
      }

      resultsContainer.innerHTML = '<p>Gerando relatório...</p>';

      try {
        const response = await fetch(endpoint);
        if (!response.ok) {
          throw new Error('Falha ao buscar dados do relatório.');
        }
        const data = await response.json();
        renderFunction(data);
      } catch (error) {
        console.error('Erro ao gerar relatório:', error);
        resultsContainer.innerHTML = '<p>Ocorreu um erro ao gerar o relatório. Tente novamente.</p>';
      }
    });
  }


  const checkTasksForm = document.getElementById('check-tasks-form');
  if (checkTasksForm) {
    const resultsContainer = document.getElementById('check-tasks-results-container');
    const employeeIdInput = document.getElementById('check-employee-id');

    const fetchAndShowTasks = async (employeeId) => {
      resultsContainer.innerHTML = '<p>Buscando tarefas...</p>';

      try {
        const response = await fetch(`${API_URL}/funcionarios/${employeeId}/tarefas`);
        if (response.status === 404) {
          resultsContainer.innerHTML = '<p>Nenhuma tarefa encontrada para este ID ou o ID é inválido.</p>';
          return;
        }
        if (!response.ok) throw new Error('Erro ao buscar tarefas.');

        const tasks = await response.json();
        const pendingTasks = tasks.filter(task => !task.concluida);

        resultsContainer.innerHTML = '';

        if (pendingTasks.length === 0) {
          resultsContainer.innerHTML = '<p>Você não tem tarefas pendentes. 🎉</p>';
        } else {
          pendingTasks.forEach(task => {
            const card = document.createElement('div');
            card.className = 'task-card';
            card.setAttribute('data-task-id', task.id);
            card.innerHTML = `
                            <h3>${task.descricao || 'Descrição não disponível'}</h3>
                            <p><strong>Status:</strong> Pendente</p>
                            <p><strong>ID Tarefa:</strong> ${task.id}</p>
                            <button class="btn btn-complete">Concluir tarefa</button>
                        `;
            resultsContainer.appendChild(card);
          });
        }
      } catch (error) {
        console.error('Erro ao buscar tarefas:', error);
        resultsContainer.innerHTML = '<p>Ocorreu um erro ao conectar ao servidor.</p>';
      }
    };

    checkTasksForm.addEventListener('submit', (event) => {
      event.preventDefault();
      const employeeId = employeeIdInput.value;
      if (employeeId) {
        fetchAndShowTasks(employeeId);
      } else {
        alert('Por favor, digite seu ID.');
      }
    });

    resultsContainer.addEventListener('click', async (event) => {
      if (event.target.classList.contains('btn-complete')) {
        const card = event.target.closest('.task-card');
        const tarefaId = card.getAttribute('data-task-id');

        try {
          const response = await fetch(`${API_URL}/funcionarios/tarefas/concluir/${tarefaId}`, {
            method: 'POST'
          });
          if (!response.ok) throw new Error('Falha ao concluir a tarefa.');

          alert('Tarefa concluída com sucesso!');
          card.remove();

          if (resultsContainer.childElementCount === 0) {
            resultsContainer.innerHTML = '<p>Você não tem mais tarefas pendentes. 🎉</p>';
          }
        } catch (error) {
          console.error('Erro ao concluir tarefa:', error);
          alert('Não foi possível concluir a tarefa.');
        }
      }
    });
  }

});