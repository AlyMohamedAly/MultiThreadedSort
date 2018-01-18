package OS;

import java.util.Arrays;
import java.util.Random;

public class threadSort{

    public static void main (String[] args){
        int[] arr = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};             //Worst case
        System.out.println("Test_1: " + Arrays.toString(arr));
        //arr = sortThis(arr);
        System.out.println("Result_1: " + Arrays.toString(sortThis(arr)));

        System.out.println("------------------------------------------------------------");

        int[] arr2 = {1, 2, 3, 4, 5, 11, 5, 9, 8, 7, 6, 5, 5, 3, 22, 1};             //Sorted first quarter
        System.out.println("Test_2: " + Arrays.toString(arr2));
        arr2 = sortThis(arr2);
        System.out.println("Result_2: " + Arrays.toString(arr2));

        System.out.println("------------------------------------------------------------");

        int[] arr3 = {1, 2, 3};                                                     //Already sorted
        System.out.println("Test_3: " + Arrays.toString(arr3));
        arr3 = sortThis(arr3);
        System.out.println("Result_3: " + Arrays.toString(arr3));

        System.out.println("------------------------------------------------------------");

        int[] arr4 = {4, 2, 3, 1};                                                   //Small array
        System.out.println("Test_4: " + Arrays.toString(arr4));
        arr4 = sortThis(arr4);
        System.out.println("Result_4: " + Arrays.toString(arr4));

        System.out.println("------------------------------------------------------------");

        int[] arr5 = {1};                                                           //One int array
        System.out.println("Test_5: " + Arrays.toString(arr5));
        arr5 = sortThis(arr5);
        System.out.println("Result_5: " + Arrays.toString(arr5));

        System.out.println("------------------------------------------------------------");

        int[] arr6 = {};                                                            //Empty array
        System.out.println("Test_6: " + Arrays.toString(arr6));
        arr6 = sortThis(arr6);
        System.out.println("Result_6: " + Arrays.toString(arr6));

        System.out.println("------------------------------------------------------------");

        int[] arr7 = {66, 66, 66, 66, 66, 66, 66, 66};                              //Repeated number array
        System.out.println("Test_7: " + Arrays.toString(arr7));
        arr7 = sortThis(arr7);
        System.out.println("Result_7: " + Arrays.toString(arr7));
        System.out.println("------------------------------------------------------------");

        Random r = new Random();
        int[] arr8 = new int[24];                                                  //Random array
        for (int i = 0; i < 24; i++){
            arr8[i] = r.nextInt(i * 3 + 4);
        }
        System.out.println("Test_8: " + Arrays.toString(arr8));
        arr8 = sortThis(arr8);
        System.out.println("Result_8: " + Arrays.toString(arr8));
        System.out.println("------------------------------------------------------------");

        int[] arr9 = new int[24];                                                  //Random mostly negative array
        for (int i = 0; i < 24; i++){
            arr9[i] = r.nextInt(i * 3 + 4) - 60;
        }
        System.out.println("Test_9: " + Arrays.toString(arr9));
        arr9 = sortThis(arr9);
        System.out.println("Result_9: " + Arrays.toString(arr9));
        System.out.println("------------------------------------------------------------");

        int[] arr10 = new int[80];                                                  //Big random array
        for (int i = 0; i < 80; i++){
            arr10[i] = r.nextInt(i * 3 + 4) - 60;
        }
        System.out.println("Test_10: " + Arrays.toString(arr10));
        arr10 = sortThis(arr10);
        System.out.println("Result_10: " + Arrays.toString(arr10));
        System.out.println("------------------------------------------------------------");
    }

    public static int[] mergeThis (int[] ar1, int[] ar2, int sz){
        int[] ans = new int[sz];
        int i, j, k;
        for (i = 0, j = 0, k = 0; i < ar1.length && j < ar2.length;){
            if (ar1[i] < ar2[j]){
                ans[k] = ar1[i];
                i++;
                k++;
            }else{
                ans[k] = ar2[j];
                j++;
                k++;
            }
        }
        for (; i < ar1.length; i++){
            ans[k] = ar1[i];
            k++;
        }
        for (; j < ar2.length; j++){
            ans[k] = ar2[j];
            k++;
        }
        return ans;
    }

    public static class SortingThread extends Thread{

        private int[] ar;
        private int start;
        private int end;

        public SortingThread (int[] arr, int start, int end){
            ar = Arrays.copyOfRange(arr, start, end);
            this.start = start;
            this.end = end;
        }

        public SortingThread (){ //for empty instialization
        }

        @Override
        public void run (){
            if (ar.length < 2)
                return;
            for (int i = 0; i < ar.length; i++){
                for (int j = 0; j < ar.length - 1; j++){
                    if (ar[j] > ar[j + 1]){
                        int temp = ar[j];
                        ar[j] = ar[j + 1];
                        ar[j + 1] = temp;
                    }
                }
            }
        }
    }

    public static class MergingThread extends Thread{

        private int[] ar1;
        private int[] ar2;
        private int[] ans;

        public MergingThread (){ //for empty instialization

        }

        public MergingThread (int[] arr1, int[] arr2, int size){
            ar1 = arr1;
            ar2 = arr2;
            ans = new int[size];
        }

        @Override
        public void run (){
            int i, j, k;
            for (i = 0, j = 0, k = 0; i < ar1.length && j < ar2.length;){
                if (ar1[i] < ar2[j]){
                    ans[k] = ar1[i];
                    i++;
                    k++;
                }else{
                    ans[k] = ar2[j];
                    j++;
                    k++;
                }
            }
            for (; i < ar1.length; i++){
                ans[k] = ar1[i];
                k++;
            }
            for (; j < ar2.length; j++){
                ans[k] = ar2[j];
                k++;
            }
        }
    }

    public static int[] sortThis (int[] arr){
        if (arr.length < 2)
            return arr;

        int state = 2;

        if ((arr.length / 4) >= 1){
            state = 4;
        }
        if ((arr.length / 8) >= 1){
            state = 8;
        }

        SortingThread TH1 = new SortingThread();  //empty instialization
        SortingThread TH2 = new SortingThread();  //empty instialization
        SortingThread TH3 = new SortingThread();  //empty instialization
        SortingThread TH4 = new SortingThread();  //empty instialization
        SortingThread TH5 = new SortingThread();  //empty instialization
        SortingThread TH6 = new SortingThread();  //empty instialization
        SortingThread TH7 = new SortingThread();  //empty instialization
        SortingThread TH8 = new SortingThread();  //empty instialization

        switch (state){
        case 2:
            TH1 = new SortingThread(arr, 0, arr.length / 2);
            TH2 = new SortingThread(arr, arr.length / 2, arr.length);

            TH1.run();
            TH2.run();
            break;
        case 4:
            TH1 = new SortingThread(arr, 0, arr.length / 4);
            TH2 = new SortingThread(arr, arr.length / 4, arr.length / 2);
            TH3 = new SortingThread(arr, arr.length / 2, (arr.length * 3) / 4);
            TH4 = new SortingThread(arr, (arr.length * 3) / 4, arr.length);

            TH1.run();
            TH2.run();
            TH3.run();
            TH4.run();
            break;
        case 8:
            TH1 = new SortingThread(arr, 0, arr.length / 8);
            TH2 = new SortingThread(arr, arr.length / 8, arr.length / 4);
            TH3 = new SortingThread(arr, arr.length / 4, (arr.length * 3) / 8);
            TH4 = new SortingThread(arr, (arr.length * 3) / 8, arr.length / 2);
            TH5 = new SortingThread(arr, arr.length / 2, (arr.length * 5) / 8);
            TH6 = new SortingThread(arr, (arr.length * 5) / 8, (arr.length * 3) / 4);
            TH7 = new SortingThread(arr, (arr.length * 3) / 4, (arr.length * 7) / 8);
            TH8 = new SortingThread(arr, (arr.length * 7) / 8, arr.length);

            TH1.run();
            TH2.run();
            TH3.run();
            TH4.run();
            TH5.run();
            TH6.run();
            TH7.run();
            TH8.run();
            break;
        }
        MergingThread MH1 = new MergingThread();  //empty instialization
        MergingThread MH2 = new MergingThread();  //empty instialization
        MergingThread MH3 = new MergingThread();  //empty instialization
        MergingThread MH4 = new MergingThread();  //empty instialization
        MergingThread MH5 = new MergingThread();  //empty instialization
        MergingThread MH6 = new MergingThread();  //empty instialization
        while (true){
            switch (state){
            case 2:
                if (!TH1.isAlive() && !TH2.isAlive()){
                    return mergeThis(TH1.ar, TH2.ar, arr.length);
                }
                break;
            case 4:
                if ((!TH1.isAlive() && !TH2.isAlive()) || (!TH3.isAlive() && !TH4.isAlive())){
                    boolean flag1 = true;
                    boolean flag2 = true;
                    while (true){
                        if (flag1){
                            if (!TH1.isAlive() && !TH2.isAlive()){
                                MH1 = new MergingThread(TH1.ar, TH2.ar, arr.length / 2);
                                MH1.run();
                                flag1 = false;
                            }
                        }
                        if (flag2){
                            if (!TH1.isAlive() && !TH2.isAlive()){
                                MH2 = new MergingThread(TH3.ar, TH4.ar, arr.length - (arr.length / 2));
                                MH2.run();
                                flag2 = false;
                            }
                        }
                        if (!flag1 && !flag2){
                            if (!MH1.isAlive() && !MH2.isAlive()){
                                return mergeThis(MH1.ans, MH2.ans, arr.length);
                            }
                        }
                    }
                }
                break;
            case 8:
                if ((!TH1.isAlive() && !TH2.isAlive()) || (!TH3.isAlive() && !TH4.isAlive())
                        || (!TH5.isAlive() && !TH6.isAlive()) || (!TH7.isAlive() && !TH8.isAlive())){
                    boolean flag1 = true;
                    boolean flag2 = true;
                    boolean flag3 = true;
                    boolean flag4 = true;

                    while (true){
                        if (flag1){
                            if (!TH1.isAlive() && !TH2.isAlive()){
                                MH1 = new MergingThread(TH1.ar, TH2.ar, arr.length / 4);
                                MH1.run();
                                flag1 = false;
                            }
                        }
                        if (flag2){
                            if (!TH3.isAlive() && !TH4.isAlive()){
                                MH2 = new MergingThread(TH3.ar, TH4.ar, (arr.length / 2) - arr.length / 4);
                                MH2.run();
                                flag2 = false;
                            }
                        }
                        if (flag3){
                            if (!TH5.isAlive() && !TH6.isAlive()){
                                MH3 = new MergingThread(TH5.ar, TH6.ar, (arr.length / 2) - arr.length / 4);
                                MH3.run();
                                flag3 = false;
                            }
                        }
                        if (flag4){
                            if (!TH7.isAlive() && !TH8.isAlive()){
                                MH4 = new MergingThread(TH7.ar, TH8.ar, (arr.length / 2) - arr.length / 4);
                                MH4.run();
                                flag4 = false;
                            }
                        }
                        if (!flag1 && !flag2 && !flag3 && !flag4){
                            while (true){
                                if (!MH1.isAlive() && !MH2.isAlive() || !MH3.isAlive() && !MH4.isAlive()){
                                    boolean flag5 = true;
                                    boolean flag6 = true;
                                    while (true){
                                        if (flag5){
                                            if (!MH1.isAlive() && !MH2.isAlive()){
                                                MH5 = new MergingThread(MH1.ans, MH2.ans, arr.length / 2);
                                                MH5.run();
                                                flag5 = false;
                                            }
                                        }
                                        if (flag6){
                                            if (!MH3.isAlive() && !MH4.isAlive()){
                                                MH6 = new MergingThread(MH3.ans, MH4.ans, arr.length - (arr.length / 2));
                                                MH6.run();
                                                flag6 = false;
                                            }
                                        }
                                        if (!flag5 && !flag6){
                                            while (true){
                                                if (!MH5.isAlive() && !MH6.isAlive()){
                                                    return mergeThis(MH6.ans, MH5.ans, arr.length);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
    }

}
