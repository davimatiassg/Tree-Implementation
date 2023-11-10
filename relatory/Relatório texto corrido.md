<script
  src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"
  type="text/javascript">
</script>

# Implementação da Árvore

A árvore binária de busca foi implementada utilizando apenas uma classe (BinaryTree). A classe possui um argumento de tipo genérico T, onde T é uma classe que herda da classe Comparable<T>. Isso é importante por que é através dessa classe que poderemos realizar comparações entre os elementos da árvore (através do método T1.compareTo(T2), que retorna -1 caso T1 < T2, 0 caso T1 = T2 e 1 caso T1 > T2). Para a execução do código apresentado neste trabalho, usaremos a classe _Integer_ no lugar do argumento T.

Cada nó da árvore, representado por um objeto da classe _BinaryTree_ possui 7 atributos, todos de aceso restrito ao pacote _tree_ (que apenas contém a classe BinaryTree, até o momento de escrita deste documento). Estes são:

T **value**: O valor armazenado nó da árvore.

int **height**: Um inteiro maior que 1 que representa a altura do nó, em comparação com o seu "galho" mais distante.

int **leftNodes** e **rightNodes**: Inteiros positivos que respectivamente representam o número de nós nas subárvores esquerda e direita deste nó.

BinaryTree<T> **root**: O nó raiz (ou a árvore pai) desta instância da BinaryTree. Caso não seja especificado um valor para a raiz de uma BinaryTree, essa referência é assinalada para a própria instância (ou seja, a instância é a própria raiz).

BinaryTree<T> **leftTree** e **rightTree**: Referências para as árvores esquerda e direita desta instância da BinaryTree. Essas referências permanecem com os valores _null_ caso não hajam subárvores para esse nó.

Atestamos que a implementação da árvore binária não é responsável por lidar com erros de uso, tais como tentativas de busca com argumentos nulos ou inválidos.


# Análise dos Algoritmos

Agora, descreveremos o processe de execução de cada um dos algoritmos implementados na classe _BinaryTree_. Para tal, considere uma árvore binária com _n_ nós.

## 1. Algoritmos da Implementação Básica

Apresentaremos aqui alguns dos algoritmos básicos que devem estar presentes na estrutura de uma árvore, mesmo que não estivessem sendo explicitamente requisitados na especificação.

### 1.1. Busca na árvore: `search(T value)`
---

O algoritmo de busca de uma árvore binária é provavelmente uma das partes mais vantajosas desse tipo de implementação. O método `search(T value)`, chamado através da referência de um objeto _BinaryTree_, é responsável por realizar a busca por determinado valor em alguma das suas subárvores. O algoritmo procede de maneira recursiva, como visto a seguir:

```java
public BinaryTree<T> search(T value)
{
	int comparison = value.compareTo(this.value);
	BinaryTree<T> nextTree = comparison < 0 ? leftTree : rightTree;
	if(comparison == 0) return this;
	if(nextTree == null) return null;
	return nextTree.search(value);
}
```

A execução do algoritmo segue um fluxo simples. Compara-se o valor pesquisado com o valor do nó atual para decidir a direção em que a recursão do algoritmo deve acontecer e, em seguida, verifica-se se alguma das condições de parada foi atingida, dando sequência à recursão no caso contrário.

Para a análise da complexidade assintótica, consideremos uma árvore binária de tamanho n. Note que a complexidade desse algoritmo depende da configuração da árvore. O pior caso dessa recursão se dá quando a busca acontece numa árvore cujos nós possuem exatamente um nó filho. Para tal configuração, o algorítmo é _O(n)_, uma vez que todos os nós precisam ser comparados. No caso preferível da árvore ser completa, a complexidade T(n) de cada chamada recursiva pode ser obtida por $T(n) = T(\frac{n}{2}) + 5$. Pelo segundo caso do teorema mestre, portanto, temos $O(T(n)) = O(lg(n))$.

### 1.2. Inserção na árvore: `add(value T)`
---

Abrindo mão do uso do método `search` para buscar pelo nó externo onde o elemento desejado pode ser inserido na árvore, o método `add(T value)` possui algumas peculiaridades mais complexas. Como visível abaixo, o algoritmo também segue um processe recursivo, que percorre a árvore apenas uma vez e, com base no retorno do método (um booleano que indica se foi possível inserir o elemento desejado), realiza a atualização da altura do nó e da quantidade de nós em cada direção.

```java
public Boolean add(T value)
{
	int comparison = value.compareTo(this.value);
	if(comparison == 0) return false;

	BinaryTree<T> nextTree = comparison < 0 ? leftTree : rightTree;
	if(nextTree == null) 
	{
		nextTree = new BinaryTree<T>(value, this);
		if(comparison > 0)
		{
			setRight(nextTree);
			rightNodes ++;
		}
		else{
			setLeft(nextTree);
			leftNodes ++;
		}
		updateHeight();
		return true;
	}
	Boolean inserted = nextTree.add(value);
	if(inserted)
	{
		incrementNodeCount(comparison < 0);
		if(getHeight() - nextTree.getHeight() <= 1) updateHeight();
	}
	return inserted;
}
```

A condição de parada para com inserção do elemento dispõe-se a partir da linha 6 do código acima. Ela ocorre quando a subárvore de um nó na direção em que a inserção deve ser feita é nula e, após feita a inserção no local correto, junto com o incremento da quantidade de nós na direção em que a inserção foi feita, o método `updateHeight()` atualiza a altura do nó, caso necessário. As chamadas recursivas , são 

A complexidade desse algoritmo também depende da configuração da árvore. Na medida em que o percurso que o algoritmo realiza é o mesmo do algoritmo de busca, vale dizer que a complexidade do método `add()` é *dominada* pela complexidade do algoritmo de busca. Localmente, a complexidade da chamada recursiva $T(n)$ mais frequente para uma árvore completa de $n$ nós onde foi possível inserir o elemento desejado pode ser definida por $T(n) = T(\frac{n}{2}) + 17$ e, pelo teorema mestre, possui complexidade assintótica $O(T(n)) = O(lg n)$, considerando as demais operações não-recursivas como em $O(1)$.

### 1.3. Remoção na árvore: `remove(value T)`
---

O método para remoção de um elemento da árvore é um pouco mais extenso do que os outros dois. Para ele, foram desenvolvidas duas funções: o método `remove(T value)`, responsável por encontrar o elemento a ser removido da árvore e atualizar as variáveis relevantes caso a deleção tenha sucesse e; o método `removeChildTree(BinaryTree<T> nextTree, Boolean deleteLeft)`, responsável por realmente apagar as referências ao elemento deletado e realizar as trocas dos elementos que forem relevantes para a árvore.

o método remove possui complexidade similar à do método `search(T value)` e da `add(T value)`, uma vez que segue o mesmo padrão de percurso na árvore e o realiza uma única vez. O código pode ser visto abaixo:

```java
public Boolean remove(T value) throws Throwable{	
	if(value == null) return false;
	int comparison = value.compareTo(this.value);
	if(comparison == 0){ 
		getRoot().removeChildTree(this, value.compareTo(getRoot().value) < 0);
		return true;
	}
	BinaryTree<T> nextTree = comparison < 0 ? this.leftTree : this.rightTree;
	Boolean success = nextTree.remove(value);
	if(this.getHeight() - nextTree.getHeight() > 1) updateHeight();
	return success;

}
```

Vale dizer que diferentemente das funções anteriormente vistas, o método `remove` apresenta um indicador de exceção, ocasionado pela presença do método `finalize()` na chamada do método `removeChildTree`, na linha 5. Sobre esse método, podemos visualizar seu código logo abaixo:

```java
public void removeChildTree(BinaryTree<T> nextTree, Boolean deleteLeft) throws Throwable {
	switch(nextTree.getEmptySubTreeAmount())
	{
		case 2:
			if (deleteLeft)	setLeft(null);
			else			setRight(null);
			nextTree.finalize();
			updateHeight();
			return;
		case 1:
			BinaryTree<T> nextNextTree = (nextTree.leftExists() ? nextTree.getLeft() : nextTree.getRight());
			nextNextTree.setRoot(this);
			if(deleteLeft)	setLeft(nextNextTree);
			else			setRight(nextNextTree);
			nextTree.finalize();
			updateHeight();
			return;
		case 0:
			BinaryTree<T> eligible = findEligible(nextTree);
			swapTree(eligible, nextTree);
			nextTree.getRoot().removeChildTree(nextTree, nextTree.getValue().compareTo(nextTree.getRoot().getValue()) < 0);
			return;
	}
}
```

Como dito, o método realiza a deleção de um nó da árvore dado seu endereço. Vale ressaltar que o nó deletado precisa ser uma subárvore do objeto que chama o método. A exclusão é feita em $O(1)$ no melhor caso, ao passo que não é necessário percorrer a árvore inteira nenhuma vez adicional. Para realizar a deleção corretamente, o processo possui 3 casos, baseados na quantidade de nós filhos da árvore. Os dois primeiros casos realizam a deleção de forma simples, com a complexidade descrita acima. 

Para o terceiro caso, onde o nó-alvo não possui nenhuma subárvore vazia, existe um método auxiliar `findEligible(BinaryTree<T> victim)`, responsável por obter o nó da árvore que assumirá o lugar do nó deletado. Esse nó garantidamente possui um ou nenhum nó filho. Para remover o nó desejado, basta trocá-lo com o elemento obtido pela `findEligible` e, em seguida, remover o nó-alvo na sua nova posição através de uma chamada recursiva do método `removeChildTree`, que inevitavelmente termina em um dos casos anteriores.

O código para a busca pelo sucessor é simples e pode ser visto logo abaixo:

```java
private BinaryTree<T> findEligible(BinaryTree<T> victim) {
	BinaryTree<T> eligible = null;
	if(victim.leftExists())
	{ 
		eligible = victim.getLeft();
		while(eligible.getRight() != null)
		{
			eligible = eligible.getRight();
		}
		return eligible;
	}

	if(victim.rightExists())
	{ 
		eligible = victim.getRight();
		while(eligible.getLeft() != null)
		{
			eligible = eligible.getLeft();
		}
		return eligible;
	}
	return null;
}
```

Isto posto, por ser um algoritmo que itera sobre os filhos do nó-alvo até obter o seu sucessor, um por vez, a complexidade $T(n)$ do algoritmo no seu pior caso pode ser definida por $T(n) = 4\cdot(n-3) + 4$. Logo, pelo método da substituição temos $O(T(n)) = O(n)$.

## 2. Algoritmos requisitados

Nessa secção, apresentaremos os algoritmos cuja implementação não foi explicitamente requisitada na especificação do projeto. 

### 2.1. Encontrar o n-ésimo elemento: *findElementByIndex*
---

O algoritmo para encontrar o enésimo elemento que implementamos utiliza duas funções. o método wrapper **findElementByIndex(int n)** é a pública e serve para inicializar uma referência ao índice pretendido, enquanto o método privada **findElementByIndexRecursive(int n)** realiza a maior parte do trabalho. Como visto no código abaixo:

Linhas 266 -> 285


Em suma, o algoritmo inicializa uma referência ao índice n e em seguida, inicializa uma variável do tipo T