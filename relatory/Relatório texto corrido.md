# Implementação da Árvore

A árvore binária de busca foi implementada utilizando apenas uma classe (BinaryTree). A classe possui um argumento de tipo genérico T, onde T é uma classe que herda da classe Comparable<T>. Isso é importante por que é através dessa classe que poderemos realizar comparações entre os elementos da árvore (através do método T1.compareTo(T2), que retorna -1 caso T1 < T2, 0 caso T1 = T2 e 1 caso T1 > T2). Para a execução do código apresentado neste trabalho, usaremos a classe _Integer_ no lugar do argumento T.

Cada nó da árvore, representado por um objeto da classe _BinaryTree_ possui 7 atributos, todos de aceso restrito ao pacote _tree_ (que apenas contém a classe BinaryTree, até o momento de escrita deste documento). Estes são:

T **value**: O valor armazenado nó da árvore.

int **height**: Um inteiro maior que 1 que representa a altura do nó, em comparação com o seu "galho" mais distante.

int **leftNodes** e **rightNodes**: Inteiros positivos que respectivamente representam o número de nós nas subárvores esquerda e direita deste nó.

BinaryTree<T> **root**: O nó raiz (ou a árvore pai) desta instância da BinaryTree. Caso não seja especificado um valor para a raiz de uma BinaryTree, essa referência é assinalada para a própria instância (ou seja, a instância é a própria raiz).

BinaryTree<T> **leftTree** e **rightTree**: Referências para as árvores esquerda e direita desta instância da BinaryTree. Essas referências permanecem com os valores _null_ caso não hajam subárvores para esse nó.


# Análise dos Algoritmos

Agora, descreveremos o processo de execução de cada um dos algoritmos implementados na classe _BinaryTree_. Para tal, considere uma árvore binária com _x_ nós.

## Implementação básica

Apresentaremos aqui alguns dos algoritmos básicos que devem estar presentes em qualquer implementação de estruturas de dados.

1. Busca na árvore

O algoritmo de busca de uma árvore binária é provavelmente uma das partes mais vantajosas desse tipo de implementação. O método ```search(T value)```, chamado através da referência de um objeto _BinaryTree_, é responsável por realizar a busca por determinado valor em alguma das suas subárvores. O algoritmo procede de maneira recursiva, como visto a seguir:


258 -> 265

A execução do algoritmo segue um fluxo simples. Compara-se o valor pesquisado com o valor do nó atual. Caso a comparação resulte em 0 (indicando que os valores são iguais), retornamos o valor atual. Caso contrário, com base na comparação, criamos uma referência à uma das subárvores do nó atual na direção (direita ou esquerda) em que poderemos encontrar o nó com o valor buscado. Se a referência for uma subárvore vazia, o algoritmo retorna o valor _null_, indicando que o nó pesquisado não está na árvore. O algoritmo então repete recursivamente a partir da referência à árvore na direção selecionada.

Considere uma árvore de _n_ níveis. Analisando a complexidade local do algoritmo, rapidamente podemos ver que a complexidade T(m), para _1 < m < n_, de cada chamada recursiva do método _search_ pode ser calculada da seguinte forma, levando em consideração que a complexidade do algoritmo de comparação é O(1) e 
\begin{align*}
	T(m) = T(m-1) + 5
\begin{end*}

A complexidade local do algoritmo naturalmente depende de _x_. O melhor caso ocorre quando buscamos pelo valor na raiz da árvore, onde _O(search) = O(1)_.


## Algoritmos requisitados
1. Encontrar o n-ésimo elemento _ findElementByIndex

O algoritmo para encontrar o enésimo elemento que implementamos utiliza duas funções. A função wrapper **findElementByIndex(int n)** é a pública e serve para inicializar uma referência ao índice pretendido, enquanto a função privada **findElementByIndexRecursive(int n)** realiza a maior parte do trabalho. Como visto no código abaixo:

Linhas 266 -> 285


Em suma, o algoritmo inicializa uma referência ao índice n e em seguida, inicializa uma variável do tipo T