# Sistema de Gerenciamento de Eventos

Este projeto é um sistema completo para cadastro, gerenciamento e participação em eventos, desenvolvido em Java utilizando Swing para a interface gráfica e JDBC para persistência de dados.

## Funcionalidades
- **Cadastro e login de administradores e participantes**
- **Gestão de eventos**: criação, listagem, remoção e associação de palestrantes e participantes
- **Inscrição de participantes em eventos**
- **Visualização de eventos disponíveis**
- **Solicitação e exibição visual de certificado de participação**
- **Interface amigável e intuitiva**

## Estrutura do Projeto
```
projeto_crud_eventos/
  projeto/
    controller/      # Lógica de controle (MVC)
    dao/             # Acesso a dados (JDBC)
    main/            # Classe principal
    model/           # Modelos de domínio (POJOs)
    utilidades/      # Utilitários e conexão com o banco
    view/            # Telas e componentes Swing
```

## Requisitos
- Java 11 ou superior
- MySQL Server
- Driver JDBC MySQL (já incluso em `lib/`)

## Como Executar
1. **Configure o banco de dados MySQL**
   - Crie o banco e as tabelas conforme o script SQL fornecido (ou adapte para sua necessidade).
2. **Configure a conexão**
   - Edite `Conexao.java` com os dados do seu banco (usuário, senha, URL).
3. **Compile o projeto**
   - Use sua IDE Java favorita (NetBeans, Eclipse, IntelliJ) ou o comando:
     ```
     javac -cp lib/mysql-connector-j-9.3.0.jar projeto/**/*.java
     ```
4. **Execute o sistema**
   - Pela IDE ou:
     ```
     java -cp .;lib/mysql-connector-j-9.3.0.jar projeto.main.Principal
     ```

## Critérios de Qualidade
- **Manutenibilidade**: arquitetura modular, orientação a objetos, fácil de evoluir.
- **Usabilidade**: interface intuitiva, com atalhos e feedback visual.
- **Segurança**: proteção de dados sensíveis, recomenda-se uso de hash de senha e validação de entrada.
- **Performance**: consultas otimizadas, pronto para grande volume de dados.
- **Confiabilidade**: tratamento de erros, pronto para testes automatizados.
- **Documentação**: código comentado e este README para facilitar uso e manutenção.

## Licença
Este projeto é acadêmico e livre para uso e modificação.

