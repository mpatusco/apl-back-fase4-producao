# language: pt
Funcionalidade: Producao
  Cenario: inserir pedido na producao
    Quando inserir pedido na producao
    Entao o pedido e inserido com sucesso
    E deve ser apresentado

  Cenario: concluir pedido na producao
    Dado que um pedido foi inserido na producao
    Quando concluir um pedido
    Entao o pedido e concluido na producao

  Cenario: remover um pedido da producao
    Dado que um pedido foi inserido na producao
    Quando remover um pedido
    Entao o pedido deve ser removido da producao

  Cenario: obter pedido na producao
    Dado que um pedido foi inserido na producao
    Quando obter um pedido na producao
    Entao o pedido deve ser obtido
    E deve ser apresentado

  Cenario: buscar pedidos na producao
    Quando buscar pedidos na producao
    Entao deve buscar os pedidos
    E os pedidos devem ser apresentados