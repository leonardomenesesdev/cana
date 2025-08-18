import java.util.*;

public class Arvore {
    static class No {
        int valor;
        No esq, dir;
        No(int v) { valor = v; }
    }

    private No raiz;

    /* ===== Inserção nível a nível (sem ordenação) ===== */
    public void inserir(int v) {
        No novo = new No(v);
        if (raiz == null) { raiz = novo; return; }
        Queue<No> q = new LinkedList<>();
        q.add(raiz);
        while (!q.isEmpty()) {
            No n = q.poll();
            if (n.esq == null) { n.esq = novo; return; }
            else q.add(n.esq);
            if (n.dir == null) { n.dir = novo; return; }
            else q.add(n.dir);
        }
    }

    /* ===== Utilitários de BFS para acessar "posição" ===== */

    // Conta nós (BFS)
    private int tamanho() {
        if (raiz == null) return 0;
        int count = 0;
        Queue<No> q = new LinkedList<>();
        q.add(raiz);
        while (!q.isEmpty()) {
            No n = q.poll();
            count++;
            if (n.esq != null) q.add(n.esq);
            if (n.dir != null) q.add(n.dir);
        }
        return count;
    }

    // Retorna o nó pela "posição" em nível (0-based)
    private No getNoPorIndice(int idx) {
        if (raiz == null) return null;
        int k = 0;
        Queue<No> q = new LinkedList<>();
        q.add(raiz);
        while (!q.isEmpty()) {
            No n = q.poll();
            if (k == idx) return n;
            k++;
            if (n.esq != null) q.add(n.esq);
            if (n.dir != null) q.add(n.dir);
        }
        return null; // índice fora do alcance
    }

    /* ===== "Insertion Sort" in-place sobre a ordem de níveis ===== */
    public void ordenarInsertionPorNivel() {
        int n = tamanho();
        for (int i = 1; i < n; i++) {
            No noI = getNoPorIndice(i);
            int chave = noI.valor;

            int j = i - 1;
            // desloca valores maiores para frente
            while (j >= 0) {
                No noJ = getNoPorIndice(j);
                if (noJ.valor <= chave) break; // posição achada
                No noJp1 = getNoPorIndice(j + 1);
                noJp1.valor = noJ.valor; // "shift" do insertion sort
                j--;
            }

            // insere chave
            No pos = getNoPorIndice(j + 1);
            pos.valor = chave;
            imprimirNivel();
        }
    }

    /* ===== Impressão em níveis ===== */
    public void imprimirNivel() {
        if (raiz == null) { System.out.println(); return; }
        Queue<No> q = new LinkedList<>();
        q.add(raiz);
        while (!q.isEmpty()) {
            No n = q.poll();
            System.out.print(n.valor + " ");
            if (n.esq != null) q.add(n.esq);
            if (n.dir != null) q.add(n.dir);
        }
        System.out.println();
    }

    /* ===== Demo ===== */
    public static void main(String[] args) {
        Arvore t = new Arvore();
        Random rand = new Random();
        // Inserções sem ordenação (preenche em largura)
        for (int i = 0; i < 10; i++) {
            int num = rand.nextInt(100); // 0 a 99
            t.inserir(num);
        }

        System.out.print("Antes (nível):   ");
        t.imprimirNivel(); // ex.: 50 30 70 10 90 40 20 60 80

        t.ordenarInsertionPorNivel();

        System.out.print("Depois (nível):  ");
        t.imprimirNivel(); // agora em ordem crescente pela sequência de níveis
    }
}