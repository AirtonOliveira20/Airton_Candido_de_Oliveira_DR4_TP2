# Refatoração da classe Pipeline

Este projeto apresenta a refatoração da classe Pipeline, com o objetivo de melhorar a organização do código, facilitar a leitura e reduzir o tamanho dos métodos.

A classe refatorada foi chamada de `PipelineRefactor`.

Antes da refatoração, a classe apresentava muitas decisões em um único fluxo, um "ninho de ifs", causando problemas na manutenção e na boa compreensão do sistema.

A refatoração buscou:

- separar responsabilidades em métodos menores
- melhorar a legibilidade
- reduzir a duplicação de lógica
- tornar o fluxo principal mais claro
