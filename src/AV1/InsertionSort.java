package AV1;

public class InsertionSort {
    public static void main(String[] args) {
        int [] v = {5, 2, 4, 6, 1, 3};
        for(int i=0; i<v.length; i++){
            System.out.print(v[i]+" ");
        }
        for(int i=1; i < v.length; i++){
            int j = i-1;
            int key = v[i];
            while(j>=0 && v[j]>key){
                v[j+1] = v[j];
                j--;
            }
            v[j+1] = key;
        }
        System.out.println();
        System.out.println("ordenado:");
        for(int i=0; i<v.length; i++){
            System.out.print(v[i]+" ");
        }
    }
}
