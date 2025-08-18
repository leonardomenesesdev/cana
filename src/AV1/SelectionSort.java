package AV1;

public class SelectionSort {
    public static void main(String[] args) {
        int [] v = {5, 2, 4, 6, 1, 3};
        for(int i=0; i<v.length; i++){
            System.out.print(v[i]+" ");
        }
        System.out.println();
        int temp = 0;

        for(int i = 0; i < v.length; i++){
            int min = i;
            for(int j = i+1; j<v.length; j++){
                if(v[j] < v[min]){
                    min = j;
                }
            }
            temp = v[min]; //temp recebe o menor valor, que pode estar em uma posicao desordenada
            v[min] = v[i]; //a posicao do valor desornado receberá o valor que era maior que o que estava nele
            v[i] = temp; //a posicao 'correta' (ate o momento) recebe o menor valor, que estava em v[min]
        }
        System.out.println("ordenado:");
        for(int i=0; i<v.length; i++){
            System.out.print(v[i]+" ");
        }
        System.out.println();
    }
}
