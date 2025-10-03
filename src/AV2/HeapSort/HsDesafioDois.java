package AV2.HeapSort;

import java.util.Arrays;
import java.util.Random;

public class HsDesafioDois {
    public void minHeapify(int[] A, int n, int i) {
        // índice do nó pai
        int menor = i;
        // índice do filho esquerdo
        int esq = 2 * i + 1;
        // índice do filho direito
        int dir = 2 * i + 2;
        // verifica se o filho esquerdo é menor que o pai
        if (esq < n && A[esq] < A[menor]) {
            menor = esq;
        }
        // verifica se o filho direito é menor que o pai
        if (dir < n && A[dir] < A[menor]) {
            menor = dir;
        }
        // se encontrou um filho menor, troca e continua ajustando a subárvore
        if (menor != i) {
            int aux = A[i];
            A[i] = A[menor];
            A[menor] = aux;
            minHeapify(A, n, menor);
        }
    }

    public void buildMinHeap(int[] A, int n) {
        // o laço começa no último nó pai (n/2 - 1) até a raiz
        // garante que todos os nós pais sejam processados
        // cada iteração ajusta a subárvore como um Min Heap válido
        for (int i = n / 2 - 1; i >= 0; i--) {
            minHeapify(A, n, i);
        }
    }

    public void heapSortDescending(int[] A) {
        int n = A.length;
        // constrói o Min Heap a partir do vetor original
        buildMinHeap(A, n);
        // cada iteração remove o menor elemento (raiz)
        // e o posiciona no final do vetor
        for (int i = n - 1; i > 0; i--) {
            int temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            // aplica o minHeapify na raiz considerando o tamanho reduzido
            minHeapify(A, i, 0);
        }
    }

    public static void main(String[] args) {

        HsDesafioDois hs = new HsDesafioDois();

        int[] vetor = new int[20];
        Random random = new Random();

        // gera 20 números aleatórios de 1 a 100
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = random.nextInt(100) + 1; // valores no intervalo [1,100]
        }

        System.out.println("Vetor original: " + Arrays.toString(vetor));

        hs.heapSortDescending(vetor);

        System.out.println("Vetor ordenado (decrescente): " + Arrays.toString(vetor));
    }
}
