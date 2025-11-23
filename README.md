# MoodTracker – Documentação Técnica do Backend

## Sumário
1. Objetivo e Escopo do Projeto
2. Descrição da Solução Proposta
3. Funcionalidades Principais
4. Destaques das Funcionalidades Implementadas
5. Tabela de Endpoints (API RESTful)

---

## 1. Objetivo e Escopo do Projeto

O projeto **MoodTracker** tem como objetivo monitorar o estado emocional de usuários, identificar riscos de burnout e fornecer análises inteligentes que auxiliem na manutenção da saúde mental.

O escopo deste backend inclui:

- Registro e gerenciamento de usuários.
- Registro de check-ins diários com humor, energia e carga de trabalho.
- Análise automática com IA (OpenAI) para estimar risco de burnout.
- Geração de dicas, risco consolidado e feedbacks.
- Exposição de uma API RESTful completa para integração com frontend web/mobile.

---

## 2. Descrição da Solução Proposta

A solução consiste em um backend desenvolvido em **Java + Quarkus**, utilizando arquitetura limpa simplificada, banco de dados Oracle e integração com a **API da OpenAI** para análise automática dos dados de check-in.

Funcionalidades implementadas:

- Autocadastro de usuários.
- CRUD de check-ins.
- Avaliação inteligente do risco via Inteligência Artificial.
- Geração de um resumo interpretativo sobre o risco.
- Sistema de feedbacks de usuário.
- Configurações individuais (tema, fuso horário, notificações).
- Tabela de dicas e sugestão aleatória.
- Exposição de todos os recursos via API RESTful hospedada em Render.

**Status da Implementação:**  
✔ Todas as funcionalidades planejadas foram implementadas.  
✔ Integração com IA funcionando tanto localmente quanto no Render.  
✔ API está 100% funcional para ser usada pelo frontend.

---

## 3. Breve Descrição das Funcionalidades Principais

- **Cadastro de Usuário:** criação de perfis com nome, email e senha.
- **Check-in Diário:** o usuário registra seu estado emocional (humor, energia e carga de trabalho).
- **Análise com IA:** para cada check-in, é possível solicitar uma análise que retorna:
    - Score de risco (0.0 a 1.0)
    - Resumo explicativo (texto natural)
- **Risco Consolidado:** obtém o risco médio dos últimos X dias.
- **Dicas:** retorna uma dica aleatória para bem-estar.
- **Feedback:** o usuário pode enviar avaliações e comentários sobre a plataforma.
- **Configurações:** salva preferências como tema, notificações e horário limite.

---

## 4. Destaques das Funcionalidades Implementadas

- **Integração real com OpenAI GPT (modelo gpt-4.1-mini)** rodando no Render.
- **Análise de burnout automática com score e resumo interpretativo.**
- **Arquitetura limpa:** separação entre domain, application, infrastructure e web.
- **Deploy em nuvem (Render) com ambientes configuráveis.**
- **Banco Oracle FIAP integrado e funcional.**
- **API totalmente documentada e testada via Postman.**

---

## Tabela de Endpoints – API RESTful (Atualizada)

### Usuários (`/users`)
| Método | URI              | Descrição                | Status            |
|--------|------------------|--------------------------|-------------------|
| POST   | `/users`         | Cria um usuário          | 201, 400          |
| GET    | `/users`         | Lista todos os usuários  | 200               |
| GET    | `/users/{id}`    | Busca usuário por ID     | 200, 404          |

---

### Check-ins por Usuário (`/users/{userId}/checkins`)
| Método | URI                            | Descrição                                           | Status      |
|--------|--------------------------------|-----------------------------------------------------|-------------|
| POST   | `/users/{userId}/checkins`     | Cria check-in para um usuário                      | 201, 400    |
| GET    | `/users/{userId}/checkins`     | Lista check-ins do usuário (filtros `from`/`to`)   | 200, 400    |

Parâmetros opcionais em `GET /users/{userId}/checkins`:
- `from=yyyy-MM-dd`
- `to=yyyy-MM-dd`

---

### Operações diretas em Check-ins (`/checkins`)
| Método | URI               | Descrição                    | Status      |
|--------|-------------------|------------------------------|-------------|
| PUT    | `/checkins/{id}`  | Atualiza um check-in         | 200, 400, 404 |
| DELETE | `/checkins/{id}`  | Remove um check-in           | 204, 404    |

---

### Análise com IA (`/users/checkins/{id}/analysis`)
| Método | URI                                | Ingestão IA / OpenAI                   | Status             |
|--------|------------------------------------|----------------------------------------|--------------------|
| POST   | `/users/checkins/{id}/analysis`    | Gera análise de risco para o check-in  | 200, 404, 500      |

---

### Risco Consolidado (`/users/{userId}/risk`)
| Método | URI                        | Descrição                                        | Status      |
|--------|----------------------------|--------------------------------------------------|-------------|
| GET    | `/users/{userId}/risk`     | Retorna badge + série de risco (últimos `days`) | 200, 404    |

Query param:
- `days` (int, default `7`)

---

### Dicas (`/tips`)
| Método | URI            | Descrição                  | Status  |
|--------|----------------|----------------------------|---------|
| GET    | `/tips/random` | Retorna dica aleatória     | 200, 404 (sem dicas) |

---

### Feedbacks (`/feedbacks`)
| Método | URI                           | Descrição                            | Status      |
|--------|-------------------------------|--------------------------------------|-------------|
| POST   | `/feedbacks`                  | Cria feedback do usuário             | 201, 400    |
| GET    | `/feedbacks/users/{userId}`   | Lista feedbacks de um usuário        | 200         |

---

### Configurações (`/config`)
| Método | URI                    | Descrição                                  | Status                 |
|--------|------------------------|--------------------------------------------|------------------------|
| GET    | `/config/users/{id}`   | Retorna configuração do usuário            | 200, 404               |
| POST   | `/config`              | Cria/atualiza configuração do usuário (upsert) | 200/201, 400      |

---
## Conclusão

O backend MoodTracker apresenta uma API REST completa, escalável e totalmente integrada com Inteligência Artificial, sendo capaz de:

- Registrar e analisar estados emocionais,
- Fornecer insights inteligentes,
- Manter registro histórico,
- Funcionar integralmente na nuvem.

## Deploy address

https://hylia-moodtracker.onrender.com

## Video Pitch

https://www.youtube.com/watch?v=EjGmsHIYzlc

## Video Demo

https://www.youtube.com/watch?v=hA0fVkPOPBE

## Git Repo

https://github.com/GusCrevelari/hylia-saas



