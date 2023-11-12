
<script
  src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"
  type="text/javascript">
</script>

# Implementação da Árvore

A árvore binária de busca foi implementada utilizando apenas uma classe (BinaryTree). A classe possui um argumento de tipo genérico T, onde T é uma classe que herda da classe Comparable<T>. Isso é importante por que é através dessa classe que poderemos realizar comparações entre os elementos da árvore (através do método `T1.compareTo(T2)`, que retorna -1 caso `T1 < T2`, 0 caso `T1 = T2` e 1 caso `T1 > T2`). Para a execução do código apresentado neste trabalho, usaremos a classe _Integer_ no lugar do argumento T.

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

### 2.1. Encontrar o n-ésimo elemento: `findElementByIndex(int n)`
---

O algoritmo para encontrar o enésimo elemento que implementamos utiliza duas funções. O método wrapper `findElementByIndex(int n)` é a chamada pública e serve para inicializar uma referência ao índice pretendido, enquanto o método privada **findElementByIndexRecursive(int n)** realiza a maior parte do trabalho. Como visto no código abaixo:

```java
public T findElementByIndex(int n){
	int n_[] = {n};
	return findElementByIndexRecursive(n_);
}
```

```java
private T findElementByIndexRecursive(int n[])
{
	T v = null;
	if (leftExists()) v = getLeft().findElementByIndexRecursive(n);
	if (n[0] == 0) return v;

	n[0] --;
	if (n[0] == 0) return value;

	if (rightExists()) v = getRight().findElementByIndexRecursive(n);
	if (n[0] == 0) return v;

	return null;
}
```

O fluxo de execução do algorimto é bastante direto: percorrendo a árvore em ordem simétrica, o algoritmo checa de a referência ao objeto inicializado no método *wrapper* contém o valor 0. Caso contrário, o algoritmo continua a percorrer a árvore.

Note que, para todos os casos, a complexidade desse algoritmo é dominada pela complexidade do percurso em órdem simétrica, uma vez que todos os nós da árvore precisam ser percorridos até que o $n-ésimo$ elemento seja encontrado. Logo, a complexidade do algoritmo é $O(min(n, x)) = O(n)$, onde $n$ é o índice buscado e $x$ é número de elementos na árvore.

### 2.2. Encontrar o índice n de certo elemento: `findIndexByElement(T value)`

A similaridade do nome deste método com o método anterior não é vã. De fato, ambos os algoritmos operam de maneira muito similar, percorrendo recursivamente a árvore em ordem simétrica e atualizando uma referência que indica o índice do valor, fato que pode ser constatado logo a seguir:

```java
public int findIndexByElement(T value){
	int n_[] = {0};
	return findIndexByElementRecursive(value, n_);
}
```

```java
private int findIndexByElementRecursive(T value, int n[]){
	int i = 0;
	if (leftExists()) i = getLeft().findIndexByElementRecursive(value, n);
	if (n[0] == -1) return i;

	n[0] ++;
	if (this.value.compareTo(value) == 0)
	{
		int tmp = n[0];
		n[0] = -1;
		return tmp;
	}

	if (rightExists()) i = getRight().findIndexByElementRecursive(value, n);
	if (n[0] == -1) return i;

	return i;
	}
```

Como esperado, a complexidade assintótica desse método também é dominada pelo percurso em órdem simétrica. Assim, temos que o algoritmo também é $O(n)$, pelos mesmos motivos que o algoritmo do item 2.1.


### 2.3. Obter a mediana da Árvore: `calculateMedian()`

---

O método para calcular a mediana é bastante simples, ainda mais que os anteriores. Dado que os nós possuem o número de subárvores em cada direção (esquerda e direita) armazenados em si, bastou que obtivéssemos calculássemos o índice da mediana (que é a metade do número de nós da árvore) e utilizássemos o supracitado método `findElementByIndex` para encontrar o elemento na posição esperada. Segue abaixo o código implementado:

```java
public T calculateMedian(){
	int v = 1 + getChildAmount();	

	v =  (v % 2 == 0) ? v = v/2 - 1 : v = v/2;

	return findElementByIndex(v + 1);
}
``` 
Perceba que o método `getChildAmount()` nada mais faz do que retornar a soma do número de nós à direita e à esquerda do nó atual. Assim, a complexidade desse algoritmo é inteiramente dominada pela complexidade do método `findElementByIndex`, ou seja, é $O(n)$ tal como ele.


### 2.4. Obter a média da Árvore: `calculateAverage(T root)`

---

O algoritmo para calcular a média da árvore segundo a assinatura requisitada na especificação do projeto é dividido em 3 métodos. Para fins de elucidação da funcionalidade da tríade, mais vale que apresentemos os códigos antes de qualquer explicação mais rebuscada. Assim sendo, observemo-los:

```java
public double calculateAverage(T root) throws InvalidTargetObjectTypeException {
	BinaryTree rt = search(root);
	if (rt == null) return -1;
	return rt.calculateAverage();
}
```

Este primeiro código contém a assinatura pretendida para a função. Recebendo como argumento apenas o valor de um suposto elemento da árvore, esse algoritmo busca a subárvore que contém esse valor na sua raiz e, em seguida, chama o método `calculateAverage()` através do objeto da subárvore. Esse método será responsável por calcular a média da subárvore. Vale destacar que a declaração do lançamento da exceção refere-se à possibilidade da árvore armazenar elementos não númericos, como strings e outras árvores. Nesse caso, o método sempre retorna a exceção descrita.

A complexidade do método acima, no seu pior caso (similar aos piores casos já descritos - onde cada nó da árvore possui um ou menos nós filhos) pode ser descrita da seguinte forma: seja $n$ o número de nós na árvore e seja $m$ o número de subnós da árvore que contém o valor passado como argumento da função. Disso, a complexidade $T(n)$ desse algoritmo é descrita pela equação $T(n) = U(n-m) + 2 + V(m)$, onde $U$ descreve a complexidade do método `search(T value)` no seu pior caso (ver item 1.1.), e $V$ descreve a complexidade do método calculateAverage, disposto logo abaixo:

```java
public double calculateAverage() throws InvalidTargetObjectTypeException{
	if(!(this.getValue() instanceof Number)){
		String s = "Can not calculate the average of non-numeric values and Class '" + this.getValue().getClass().getSimpleName() + "' cannot be asserted to a numerical type.";
		throw new InvalidTargetObjectTypeException(s);
	}

	Double averageParcels[] = {((Number) this.getValue()).doubleValue()};
	this.sumSubtrees(averageParcels);		
	return averageParcels[0]/(1+getChildAmount());
}
```

O método `calculateAverage()` não recebe argumentos e calcula a média do objeto `BinaryTree` que o chamou. O método pode ser caracterizado como um *wrapper* para o terceiro e último método deste quesito, uma vez que sua função é inicializar uma referência à um objeto que armazenará o somatório de todos os nós a partir do chamador e, após a realização das somas, dividir o total pela quantidade de nós que foram somados. O somatório é realizado recursivamente pelo método `sumSubtrees`, que recebe uma referência para o objeto que armazena o valor do somatório. Esse método pode ser visto abaixo.

A complexidade desse método é definida pelo número de chamadas que a recursão `sumSubtrees` realizar. Assim sendo, descrevemos a complexidade $V(m)$ desse método como $V(m) = W(m) + 8$, onde $W$ é a complexidade do método `sumSubtrees`, exposto a seguir:

```java
private void sumSubtrees(Double averageParcels[]){
	if (leftExists())
	{
		averageParcels[0] += ((Number)getLeft().getValue()).doubleValue();
		getLeft().sumSubtrees(averageParcels);
	}
	if (rightExists())
	{
		averageParcels[0] += ((Number) getRight().getValue()).doubleValue();
		getRight().sumSubtrees(averageParcels);
	}
}
```

A visibilidade privada desse método é objetiva garantir que ele não será utilizado fora do método `calculateAverage()`, onde não há a verificação de que os objetos armazenados na árvore sequer são "somáveis". Com isso em mente, o método percorre a árvore recursivamente em pré-ordem e adiciona os valores dos nós à referência `averageParcels[]`.

Note que, uma vez que percorre todos os $m$ nós da árvore uma única vez, esse algoritmo tem complexidade linear, ou seja, $O(W(m)) = O(m)$. Para analisar a complexidade total da chamada do primeiro método apresentado nesse item (que também é a execução mais dispendiosa dentre os três), tomemos as três equações que apresentamos e poderemos perceber que a complexidade do primeiro método deste quesito, o `calculateAverage(T root)`, também é linear:

Como $O(W(m)) = O(m)$, seja $c \in \mathbb{R}_{>0}$ tal que, eventualmente, $W(m) \leq cm$. Logo, note que:
$$\begin{align*}
W(m) \leq cm &\implies W(m) + 8 \leq cm + 8\\
&\implies W(m) + 8 \leq 8c_1m \tag{Para todo $m \geq1 $}\\
&\implies V(m) \leq 8cm\\
&\implies V(m) = O(m)\\
\end{align*}$$
Analogamente, como $V(m) = O(m)$, temos $V(m) +2 = O(m)$. Disso, como pelo item 1.1. temos que $U$ é linear, note que:
$$\begin{align*}
T(n) = U(n-m) + 2 + V(m) &\implies T(n) = O(n-m) + O(m)\\
&\implies T(n) = O(n -m + m)\\
&\implies T(n) = O(n)\\
\end{align*}$$
Assim, obtemos que a complexidade $T(n)$ do método`calculateAverage(T root)`, realmente é linear.


### 2.5 Verificar se a árvore é cheia: `isFull()`
---

Para verificar se a árvore é cheia, isto é, se todas as suas folhas estão no último nível, implementamos um código de funcionamento similar aos que já apresentamos acima: o método `isFull()` inicializa uma referência a um objeto - desta vez um booleano - que será alterada conforme a progressão de um algoritmo recursivo. A implementação do `isFull()` é mostrada abaixo:

```java
public boolean isFull() {//Metal Alchemist
	boolean b[] = {true};
	completenessCheck(b, 0);
	return b[0];
}
```

Como esperado, o algoritmo recursivo em questão é o método `completenessCheck`. O processo de execução desse método é bastante interessante, principalmente considerando que ele será utilizado tanto neste quesito quanto no próximo. O código da implementação pode ser visto em seguida:

```java
private void completenessCheck(boolean b[], int subTreeAmountGap)
{
	if (!b[0]) return;
	int t = 0;
	if(leftExists()){ t += getLeft().getHeight(); }
	if(rightExists()){ t -= getRight().getHeight(); }
	if(Math.abs(t) > heighGap) {
		b[0] = false;
		return;
	}

	if(leftExists()){ getLeft().completenessCheck(b, heighGap);}
	if(rightExists()){ getRight().completenessCheck(b, heighGap);}
}
```

O processo descrito pelo algoritmo consiste em verificar a quantidade de subárvores em cada um dos nós, recursivamente. A ideia central do algoritmo é, para cada nó da árvore verificar que a diferença entre as alturas dos nós filhos é 0. Em caso afirmativo, como é de se esperar o algoritmo deverá percorrer a árvore inteira até constatar esse fato, no último nível (pior caso). No caso contrário, o algoritmo interrompe a recurssão ao atribuir o valor `false` à referência passada, ativando a condição de parada na primeira linha do código.

Visto que no seu pior caso o algoritmo percorre a árvore inteira uma única vez, a sua complexidade, definida na equação $T(n) = 2T(\frac{n}{2}) + 13$ é trivialmente linear (pelo 1º caso do teorema mestre).

### 2.6 Verificar se a árvore é completa: `isComplete()`
---

O código para obter se uma árvore é completa é extremamente similar ao empregado na verificação se a árvore é cheia, a saber:

```java
public boolean isComplete() {
	boolean b[] = {true};
	completenessCheck(b, 1);
	return b[0];
}
```

A única alteração nesse código em relação ao anterior, além do nome, é que a chamada do método `completenessCheck` na linha 2 passa o valor 1 (e não 0, como no quesito anterior) para o argumento `heightGap` do método, indicando que pode haver uma diferença de altura de uma unidade entre os nós filhos de determinado nó da árvore, como é comum observarmos em árvores completas não-cheias.

A complexidade deste algoritmo é idêntica à do quesito anterior.


### 2.7 Impressão em préordem: `toStringPreOrder()`
---

Para realizar a impressão em pré-ordem da árvore, utilizamos o algoritmo padrão para esse tipo de acesso, como visto abaixo:

```java
public String toStringPreOrder()
	{
		String s = "";
		s += " " + getValue().toString();
		if (leftExists()) s += getLeft().toStringPreOrder();
		if (rightExists()) s += getRight().toStringPreOrder();
		return s;
	}
```

O algoritmo segue uma estrutura simples, que concatena a um objeto string os valores dos nós percorridos em pré-ordem para então retorna-lo. A complexidade desse algoritmo é linar, visto que é dominada pela complexidade do percurso.


### 2.8 Impressão em formatos diferentes: `toString(int i)`
---

Para a realização da impressão, utilizamos o método `toString(int i)` responsável unicamente por chamar o método correspondente ao tipo de impressão requisitado. A saber:

```java
public String toString(int i){
	String s = "BinaryTree\n" + "Root: " + this.getValue() + "\n";
	if (i == 1) return showTreeDashes();
	if (i == 2) return showTreeChain();
	return s;
	}
```

A impressão em hierarquia é feita pelo método `showTreeDashes()`, apresentado sob a seguinte forma:

```java
public String showTreeDashes(){ 
	return showTreeDashes("", 
	"_____________________________________________________"+
	"_____________________________________________________"+
	"_____________________________________________________");
}

```


```java
private String showTreeDashes(String spaces, String dashes) {
	try{
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		String strV = df.format(value).toString();
	} catch (Exception e) {}
	
	spaces += "     ";	
	dashes = dashes.substring(0, dashes.length() - 5);
	String s = spaces + strV + dashes.substring(0, dashes.length() - strV.length()) + "\n";
	if(leftExists()) { s += leftTree.showTreeDashes(spaces, dashes); }
	if(rightExists()) { s += rightTree.showTreeDashes(spaces, dashes); }
	return s;
}
```

Apesar da configuração um tanto carregada, o método possui um funcionamento bem simples. O algoritmo percorre a árvore em pré-ordem e, para cada chamda recursiva, adiciona série de espaços em branco, seguida do valor do nó no qual a chamada foi feita e uma sequência de traços que reduz seu tamanho de acordo. Existe também espaço para tratamento do tamanho dos valores exibidos, caso sejam valores numéricos.

Para a impressão no formato 2, usando parêntesis, temos o método `showTreeChain()`, muito mais curto, descrito abaixo:

```java
public String showTreeChain(){
	String s = "(" + this.getValue();
	if(leftExists()) s += getLeft().showTreeChain();
	if(rightExists()) s += getRight().showTreeChain();
	return s+")";
}
```

O método `showTreeChain()` apresenta de modo muito mais óbvio o percurso em pre-ordem que é desenvolvido nele. De fato, tanto esse método quanto o anterior (`showTreeDashes`) têm sua complexidade dominada pelo percurso, sendo portanto lineares.