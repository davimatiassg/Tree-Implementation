# Trabalho de EDB2
Turma: *T01*
## Identificação Pessoal
*Carlos Eduardo Valle Rosa Filho*
	- *carlosvallerosafilho@gmail.com*
*Davi Matias Soares Genuino*
	- *davimatiassg@gmail.com*


## Instruções de compilação

Antes de realizar a execução do projeto, certifique-se de modificar, conforme desejado, os arquivos `<binaryTree>` e  `commands`, na pasta `./input/`. Eles contém respectivamente a especificação da árvore e dos comando a serem executados. Esses arquivos serão passados como argumentos para a execução do programa.

### Com Eclipse:
1. Abra o projeto no eclipse normalmente;
2. No explorador do projeto, vá até o caminho `tree->view` e abra a classe `TreeView.java`;
3. Nas opções ao lado do botão **Run**, procure por **Run Configurations**;
4. Na aba *Arguments*, procure pelo campo *Program arguments* e insira o texto `binaryTree.txt Commands.txt`, caso o campo esteja em branco;
5. No campo *Working directory*, clique no botão *Workspace* e selecione a pasta `input`, na pasta raiz do projeto;
6. Clique em Apply e, em seguida, em Run.


### Pela linha de comando
1. Na pasta raiz do projeto, execute o comando abaixo para compilar o projeto :
<code>javac -d ./bin -sourcepath . ./src/tree/*.java ./src/reader/*.java ./src/view/*.java</code>
2. Vá até a pasta `bin`
3. Execute o projeto com:
<code>java ./bin/view/TreeView `<caminho-para-arquivo-de-comandos>` `<caminho-para-arquivo-da-arvore>` </code>.
	- O padrão é <code>java view.TreeView ./../input/binaryTree.txt ./../input/commands.txt`.</code>
