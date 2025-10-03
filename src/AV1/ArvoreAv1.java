package AV1;

import java.util.Scanner;

public class ArvoreAv1 {

    static class No {
        int valor;
        No esq, dir;
        int altura; // Campo adicional para AVL

        No(int v) {
            valor = v;
            altura = 1; // Altura inicial de um novo nó é 1
        }
    }

    private No raiz;


    private int altura(No no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    // Função para obter o maior de dois inteiros
    private int max(int a, int b) {
        return Math.max(a, b);
    }

    // Função para obter o fator de balanceamento de um nó
    private int fatorBalanceamento(No no) {
        if (no == null) {
            return 0;
        }
        return altura(no.esq) - altura(no.dir);
    }

    // Rotação simples à direita
    private No rotacaoDireita(No y) {
        No x = y.esq;
        No T2 = x.dir;

        // Realiza a rotação
        x.dir = y;
        y.esq = T2;

        // Atualiza as alturas
        y.altura = max(altura(y.esq), altura(y.dir)) + 1;
        x.altura = max(altura(x.esq), altura(x.dir)) + 1;

        // Retorna a nova raiz
        return x;
    }

    // Rotação simples à esquerda
    private No rotacaoEsquerda(No x) {
        No y = x.dir;
        No T2 = y.esq;

        // Realiza a rotação
        y.esq = x;
        x.dir = T2;

        // Atualiza as alturas
        x.altura = max(altura(x.esq), altura(x.dir)) + 1;
        y.altura = max(altura(y.esq), altura(y.dir)) + 1;

        // Retorna a nova raiz
        return y;
    }

    // Inserção na árvore AVL (agora com lógica de balanceamento)
    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No inserirRec(No no, int valor) {
        // 1. Inserção padrão de uma Árvore Binária de Busca
        if (no == null) {
            return new No(valor);
        }
        if (valor < no.valor) {
            no.esq = inserirRec(no.esq, valor);
        } else if (valor > no.valor) {
            no.dir = inserirRec(no.dir, valor);
        } else {
            // Valores duplicados não são permitidos
            return no;
        }

        // 2. Atualiza a altura do nó ancestral
        no.altura = 1 + max(altura(no.esq), altura(no.dir));

        // 3. Obtém o fator de balanceamento para verificar se o nó ficou desbalanceado
        int balance = fatorBalanceamento(no);

        // 4. Se o nó ficou desbalanceado, existem 4 casos

        // Caso Esquerda-Esquerda
        if (balance > 1 && valor < no.esq.valor) {
            return rotacaoDireita(no);
        }

        // Caso Direita-Direita
        if (balance < -1 && valor > no.dir.valor) {
            return rotacaoEsquerda(no);
        }

        // Caso Esquerda-Direita
        if (balance > 1 && valor > no.esq.valor) {
            no.esq = rotacaoEsquerda(no.esq);
            return rotacaoDireita(no);
        }

        // Caso Direita-Esquerda
        if (balance < -1 && valor < no.dir.valor) {
            no.dir = rotacaoDireita(no.dir);
            return rotacaoEsquerda(no);
        }

        // Retorna o nó (ponteiro) inalterado
        return no;
    }
    public int tamanho() {
        return tamanhoRec(this.raiz);
    }


    private int tamanhoRec(No no) {
        // Caso base: se a árvore/subárvore é vazia, o tamanho é 0.
        if (no == null) {
            return 0;
        }
        // Retorna 1 (nó atual) + tamanho da subárvore esquerda + tamanho da subárvore direita.
        return 1 + tamanhoRec(no.esq) + tamanhoRec(no.dir);
    }


    private static class NoDaFila {
        No noDaArvore;
        NoDaFila proximo;

        NoDaFila(No noDaArvore) {
            this.noDaArvore = noDaArvore;
            this.proximo = null;
        }
    }


    private static class FilaManual {
        private NoDaFila inicio; // O início da fila (de onde removemos)
        private NoDaFila fim;    // O fim da fila (onde adicionamos)

        public boolean isEmpty() {
            return inicio == null;
        }


        public void add(No noDaArvore) {
            NoDaFila novoNo = new NoDaFila(noDaArvore);
            if (isEmpty()) {
                inicio = novoNo;
                fim = novoNo;
            } else {
                fim.proximo = novoNo;
                fim = novoNo;
            }
        }

        public No poll() {
            if (isEmpty()) {
                return null;
            }
            No noParaRetornar = inicio.noDaArvore;
            inicio = inicio.proximo;
            // Se a fila ficou vazia após a remoção, o fim também deve ser nulo.
            if (inicio == null) {
                fim = null;
            }
            return noParaRetornar;
        }
    }

    public No getNoByIndex(int idx) {
        if (raiz == null || idx < 0) {
            return null;
        }

        FilaManual fila = new FilaManual();
        fila.add(raiz);
        int k = 0; // Contador de índice atual

        while (!fila.isEmpty()) {
            No noAtual = fila.poll();

            if (k == idx) {
                return noAtual;
            }
            k++;


            if (noAtual.esq != null) {
                fila.add(noAtual.esq);
            }
            if (noAtual.dir != null) {
                fila.add(noAtual.dir);
            }
        }

        return null; // Índice fora do alcance da árvore
    }
    public void insertionSort(){
        int n = tamanho();
        imprimirPorNivel();
        for (int i = 1; i<n; i++){
            No noAtual = getNoByIndex(i);
            int key = noAtual.valor;
            int j = i-1;
            while(j>=0){
                No noJ = getNoByIndex(j);
                if(noJ.valor <= key){
                    break;
                }
                No noJ1 = getNoByIndex(j+1);
                noJ1.valor = noJ.valor;
                j--;
            }
            No pos = getNoByIndex(j+1);
            pos.valor = key;
            imprimirPorNivel();
        }
    }
    public void selectionSort(){
        int tamanho = tamanho();

        for (int i = 0; i < tamanho; i++) {
            int min = i;
            int minValue = getNoByIndex(min).valor;

            for (int j = i+1; j < tamanho; j++){
                int jValue = getNoByIndex(j).valor;
                if (jValue < minValue) {
                    min = j;
                    minValue = jValue;
                }
            }

            // faz a troca
            int temp = getNoByIndex(i).valor;
            getNoByIndex(i).valor = getNoByIndex(min).valor;
            getNoByIndex(min).valor = temp;
        }
    }


    public void preOrdem(No no) { if (no != null) {
        System.out.print(no.valor + " ");
        preOrdem(no.esq);
        preOrdem(no.dir);
        }
    }
    public void preOrdemInsertion(No no){
        if(no!=null){
            int tamanho = tamanho();
            for (int i = 1; i<tamanho; i++){

            }
        }
    }
    public void emOrdem(No no) { if (no != null) { emOrdem(no.esq); System.out.print(no.valor + " "); emOrdem(no.dir); } }
    public void posOrdem(No no) { if (no != null) { posOrdem(no.esq); posOrdem(no.dir); System.out.print(no.valor + " "); } }

    public void imprimirVisualmente() {
        if (raiz == null) { System.out.println("A árvore está vazia."); return; }
        imprimirVisualmenteRec(raiz, new StringBuilder(), true, true);
    }

    private void imprimirVisualmenteRec(No no, StringBuilder prefixo, boolean isCauda, boolean isRaiz) {
        if (no == null) return;
        System.out.println(prefixo.toString() + (isRaiz ? "" : (isCauda ? "└── " : "├── ")) + no.valor);
        StringBuilder prefixoFilho = new StringBuilder(prefixo);
        if (!isRaiz) { prefixoFilho.append(isCauda ? "    " : "│   "); }
        if (no.esq != null) { imprimirVisualmenteRec(no.esq, prefixoFilho, no.dir == null, false); }
        if (no.dir != null) { imprimirVisualmenteRec(no.dir, prefixoFilho, true, false); }
    }
    public void imprimirPorNivel() {
        if (raiz == null) {
            System.out.println("A árvore está vazia.");
            return;
        }

        FilaManual fila = new FilaManual();
        fila.add(raiz);

        while (!fila.isEmpty()) {
            No noAtual = fila.poll();
            System.out.print(noAtual.valor + " ");

            if (noAtual.esq != null) {
                fila.add(noAtual.esq);
            }
            if (noAtual.dir != null) {
                fila.add(noAtual.dir);
            }
        }
        System.out.println(); // Pula uma linha no final
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArvoreAv1 arvore = new ArvoreAv1();

        System.out.println("Digite os elementos do vetor separados por espaço (ex: 8 5 3 1):");
        String[] entrada = sc.nextLine().split(" ");
        for (String num : entrada) {
            try {
                arvore.inserir(Integer.parseInt(num));
            } catch (NumberFormatException e) {
                System.out.println("Ignorando entrada inválida: " + num);
            }
        }

        System.out.println("\nEscolha a forma de percorrer a árvore:");
        System.out.println("1 - Pré-ordem");
        System.out.println("2 - Em ordem");
        System.out.println("3 - Pós-ordem");
        int opcao = sc.nextInt();

        System.out.println("\n--- RESULTADO ---");

        switch (opcao) {
            case 1: System.out.print("Sequência em Pré-ordem: "); arvore.preOrdem(arvore.raiz); break;
            case 2: System.out.print("Sequência Em ordem: "); arvore.emOrdem(arvore.raiz); break;
            case 3: System.out.print("Sequência em Pós-ordem: "); arvore.posOrdem(arvore.raiz); break;
            default: System.out.println("Opção inválida!"); sc.close(); return;
        }

        System.out.println("\n\nVisualização da Estrutura da Árvore (AVL Balanceada):");
        arvore.imprimirVisualmente();

        arvore.insertionSort();
        arvore.imprimirVisualmente();
        System.out.println("Árvore ordenada");
        arvore.imprimirPorNivel();
        sc.close();
    }
}