# 🥋 DumbFit - Diário de Artes Marciais

O **DumbFit** é um aplicativo Android nativo projetado para praticantes de artes marciais (como Jiu-Jitsu, Judô, Muay Thai, Karate, etc.) registrarem suas rotinas de treinos e acompanharem a evolução de suas técnicas de combate.

O app utiliza uma interface moderna com abas para separar o histórico de sessões práticas do catálogo de golpes aprendidos.

## 🚀 Funcionalidades

- **Aba de Treinos:**
    - Registro de sessões com tipo de treino (Sparring, Kata, Randori, etc.).
    - Duração do treino em minutos.
    - Seleção visual de data através do `DatePicker` nativo do Material 3.
    - Campo de anotações livres para observações de desempenho.
- **Aba de Técnicas:**
    - Catálogo de golpes e posições estruturado por nome e Arte Marcial.
    - Espaço detalhado para descrição de etapas de execução, pegadas e ajustes técnicos.
    - Ordenação automática na listagem por Arte Marcial e Nome da Técnica.

## 🛠️ Tecnologias e Arquitetura

O projeto foi desenvolvido seguindo as recomendações oficiais do ecossistema Android moderno:

- **Linguagem:** [Kotlin](https://kotlinlang.org/)
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Interface 100% declarativa e reativa)
- **Design System:** Material Design 3 (com suporte nativo a Tema Escuro)
- **Banco de Dados Local:** [Room Database](https://developer.android.com/training/data-storage/room) (SQLite persistente)
- **Processamento de Anotações:** Google KSP (Kotlin Symbol Processing)
- **Arquitetura:** MVVM (Model-View-ViewModel) com fluxo de dados reativo via `StateFlow` e `Coroutines`
- **Navegação:** Jetpack Navigation Compose (Gerenciamento de rotas em tela cheia para formulários)

## 📸 Telas do Aplicativo

*(Dica: Após subir o projeto no GitHub, você pode tirar prints do app rodando, salvá-los em uma pasta chamada `screenshots` e substituir os links abaixo pelas imagens reais!)*

| Histórico de Treinos | Formulário com Calendário |
|:---:|:---:|
| <img src="screenshots/lista_treinos.png" width="250"> | <img src="screenshots/form_treinos.png" width="250"> |

## 🏗️ Como compilar o projeto

Para rodar o projeto localmente, você precisará do **Android Studio Jellyfish (ou superior)**.

1. Clone este repositório:
   ```bash
   git clone [https://github.com/lukasbariani/DumbFit.git](https://github.com/lukasbariani/DumbFit.git)