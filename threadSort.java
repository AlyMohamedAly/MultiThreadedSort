package MultiThreadedSort;

import java.util.Arrays;
import java.util.Random;

public class threadSort{

    public static void main (String[] args){
    }

    public static int[] MergeThis (int[] ar1, int[] ar2, int sz){
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

        public SortingThread (int[] arr, int start, int end){
            ar = Arrays.copyOfRange(arr, start, end);
        }

        public SortingThread (){ //for empty instialization
        }

        @Override
        public void run (){
            if (ar.length < 2)
                return;
            Arrays.sort(ar);
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
        switch (state){
        case 2:
            try{
                TH1.join();
                TH2.join();
            }catch (InterruptedException ex){
            }
            return MergeThis(TH1.ar, TH2.ar, arr.length);
        case 4:
            while (true)
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
                            if (!TH3.isAlive() && !TH4.isAlive()){
                                MH2 = new MergingThread(TH3.ar, TH4.ar, arr.length - (arr.length / 2));
                                MH2.run();
                                flag2 = false;
                            }
                        }
                        if (!flag1 && !flag2){
                            try{
                                MH1.join();
                                MH2.join();
                            }catch (InterruptedException ex){
                            }
                            return MergeThis(MH1.ans, MH2.ans, arr.length);
                        }
                    }
                }
        case 8:
            while (true){
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
                                            try{
                                                MH5.join();
                                                MH6.join();
                                            }catch (InterruptedException ex){
                                            }
                                            return MergeThis(MH6.ans, MH5.ans, arr.length);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new RuntimeException(); // shouldn't reach This
    }
}
