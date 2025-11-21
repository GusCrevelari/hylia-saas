# MoodTracker – Documentação Técnica do Backend

## Sumário
1. Objetivo e Escopo do Projeto ........................................ pág. 1
2. Descrição da Solução Proposta ....................................... pág. 1
3. Funcionalidades Principais ............................................ pág. 2
4. Destaques das Funcionalidades Implementadas ................. pág. 2
5. Tabela de Endpoints (API RESTful) .................................. pág. 3

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

## 5. Tabela de Endpoints – API RESTful

### Usuários (`/users`)
| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| POST | `/users` | Cria um usuário | 201, 400 |
| GET | `/users` | Lista todos os usuários | 200 |
| GET | `/users/{id}` | Busca um usuário por ID | 200, 404 |

---

### Check-ins (`/users/{userId}/checkins`)
| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| POST | `/users/{id}/checkins` | Cria um check-in | 201, 400 |
| GET | `/users/{id}/checkins` | Lista check-ins (opcional: data de–até) | 200 |
| PUT | `/users/checkins/{id}` | Atualiza um check-in | 200, 404 |
| DELETE | `/users/checkins/{id}` | Remove um check-in | 204, 404 |

---

### Análise com IA (`/users/checkins/{id}/analysis`)
| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| POST | `/users/checkins/{id}/analysis` | Gera análise de burnout com IA | 200, 404, 500 |

---

### Risco Consolidado (`/users/{userId}/risk`)
| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| GET | `/users/{id}/risk?days=7` | Calcula badge de risco | 200, 404 |

---

### Dicas (`/tips`)
| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| GET | `/tips/random` | Retorna uma dica aleatória | 200, 404 (sem dicas) |

---

### Feedbacks (`/feedbacks`)
| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| POST | `/feedbacks` | Cria feedback | 201, 400 |
| GET | `/feedbacks/users/{id}` | Lista feedbacks do usuário | 200 |

---

### Configurações (`/config`)
| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| GET | `/config/users/{id}` | Retorna config do usuário | 200, 404 |
| POST | `/config` | Upsert de configuração | 200, 201, 400 |

---

## Conclusão

O backend MoodTracker apresenta uma API REST completa, escalável e totalmente integrada com Inteligência Artificial, sendo capaz de:

- Registrar e analisar estados emocionais,
- Fornecer insights inteligentes,
- Manter registro histórico,
- Funcionar integralmente na nuvem.

Deploy address: https://hylia-moodtracker.onrender.com



