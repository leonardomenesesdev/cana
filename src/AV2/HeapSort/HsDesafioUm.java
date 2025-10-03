package AV2.HeapSort;

import java.util.Arrays;
import java.util.Random;

public class HsDesafioUm {
    public void maxHeapify(int[] A, int n, int i) {
        //nó pai
        int maior = i;
        //filho esquerdo
        int esq = 2 * i + 1;
        //filho direito
        int dir = 2 * i + 2;
        //verifica se o filho esquerdo está posicionado corretamente ou se pode ser maior que um nó pai
        if (esq < n && A[esq] > A[maior]) {
            maior = esq;
        }
        //verifica se o filho direito está posicionado corretamente ou se pode ser maior que um nó pai
        if (dir < n && A[dir] > A[maior]) {
            maior = dir;
        }
        //faz as trocas necessárias
        if (maior != i) {
            int aux = A[i];
            A[i] = A[maior];
            A[maior] = aux;
            maxHeapify(A, n, maior);
        }

    }

    public void buildMaxHeap(int[] A, int n) {
        //laço inicia em n/2-1, correspondendo ao último nó pai na representação de um vetor. Garante
        //que apenas nós pais sejam processados
        //Iteração decrescente -> Assume que as subárvores de cada nó já são heaps válidos
        for (int i = n / 2 - 1; i >= 0; i--) {
            maxHeapify(A, n, i);
        }
    }

    public void heapSort(int[] A) {
        int n = A.length;
        //Construção do Max Heap com base no vetor original
        buildMaxHeap(A, n);
        //Cada iteração extrai o elemento máximo atual e posiciona-o corretamente no fim do vetor
        for (int i = n - 1; i > 0; i--) {
            int temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            //Aplica o maxHeapify na raiz com tamanho reduzido
            maxHeapify(A, i, 0);
        }
    }

    public static void main(String[] args) {
        HsDesafioUm hs = new HsDesafioUm();


        int[] vetor = new int[20];
        Random random = new Random();

        // Gera 20 números aleatórios de 1 a 100
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = random.nextInt(100) + 1; // [1,100]
        }

        System.out.println("Vetor original: " + Arrays.toString(vetor));

        hs.heapSort(vetor);

        System.out.println("Vetor ordenado: " + Arrays.toString(vetor));
    }
}
