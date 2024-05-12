package sbu.cs;

import java.util.ArrayList;
import java.util.List;
public class MatrixMultiplication {
    public static class BlockMultiplier implements Runnable {
        List<List<Integer>> subMatrix_A;
        List<List<Integer>> matrix_B;
        public static List<List<Integer>> resultMatrix_1 = new ArrayList<>();
        public static List<List<Integer>> resultMatrix_2 = new ArrayList<>();
        public static List<List<Integer>> resultMatrix_3 = new ArrayList<>();
        public static List<List<Integer>> resultMatrix_4 = new ArrayList<>();
        int part = 0;

        public BlockMultiplier(List<List<Integer>> subMatrix_A, List<List<Integer>> matrix_B, int part) {
            this.subMatrix_A = subMatrix_A;
            this.matrix_B = matrix_B;
            this.part = part;
        }

        @Override
        public void run() {
            for (int i = 0; i < subMatrix_A.size(); i++) {
                ArrayList<Integer> row = new ArrayList<>();
                for (int j = 0; j < matrix_B.getFirst().size(); j++) {
                    int sum = 0;
                    for (int k = 0; k < matrix_B.size(); k++) {
                        sum += subMatrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                    }
                    row.add(sum);
                }
                if (part == 1) resultMatrix_1.add(row);
                else if (part == 2) resultMatrix_2.add(row);
                else if (part == 3) resultMatrix_3.add(row);
                else if (part == 4) resultMatrix_4.add(row);
            }
        }
    }

    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B) throws InterruptedException {
        List<List<Integer>> subMatrix_a1 = new ArrayList<>();
        List<List<Integer>> subMatrix_a2 = new ArrayList<>();
        List<List<Integer>> subMatrix_a3 = new ArrayList<>();
        List<List<Integer>> subMatrix_a4 = new ArrayList<>();

        for (int i = 0; i < matrix_A.size() / 4; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < matrix_A.getFirst().size(); j++) {
                row.add(matrix_A.get(i).get(j));
            }
            subMatrix_a1.add(row);
        }
        for (int i = matrix_A.size() / 4; i < 2 * matrix_A.size() / 4; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < matrix_A.getFirst().size(); j++) {
                row.add(matrix_A.get(i).get(j));
            }
            subMatrix_a2.add(row);
        }
        for (int i = 2 * matrix_A.size() / 4; i < 3 * matrix_A.size() / 4; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < matrix_A.getFirst().size(); j++) {
                row.add(matrix_A.get(i).get(j));
            }
            subMatrix_a3.add(row);
        }
        for (int i = 3 * matrix_A.size() / 4; i < 4 * matrix_A.size() / 4; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < matrix_A.getFirst().size(); j++) {
                row.add(matrix_A.get(i).get(j));
            }
            subMatrix_a4.add(row);
        }

        BlockMultiplier blockMultiplier_1 = new BlockMultiplier(subMatrix_a1, matrix_B, 1);
        BlockMultiplier blockMultiplier_2 = new BlockMultiplier(subMatrix_a2, matrix_B, 2);
        BlockMultiplier blockMultiplier_3 = new BlockMultiplier(subMatrix_a3, matrix_B, 3);
        BlockMultiplier blockMultiplier_4 = new BlockMultiplier(subMatrix_a4, matrix_B, 4);

        Thread thread_1 = new Thread(blockMultiplier_1);
        Thread thread_2 = new Thread(blockMultiplier_2);
        Thread thread_3 = new Thread(blockMultiplier_3);
        Thread thread_4 = new Thread(blockMultiplier_4);

        thread_1.start();
        thread_2.start();
        thread_3.start();
        thread_4.start();

        thread_1.join();
        thread_2.join();
        thread_3.join();
        thread_4.join();

        List<List<Integer>> resultMatrix = new ArrayList<>();
        resultMatrix.addAll(BlockMultiplier.resultMatrix_1);
        resultMatrix.addAll(BlockMultiplier.resultMatrix_2);
        resultMatrix.addAll(BlockMultiplier.resultMatrix_3);
        resultMatrix.addAll(BlockMultiplier.resultMatrix_4);

        // for(List<Integer> row: BlockMultiplier.resultMatrix_1) System.out.println(row);
        // for(List<Integer> row: BlockMultiplier.resultMatrix_2) System.out.println(row);
        // for(List<Integer> row: BlockMultiplier.resultMatrix_3) System.out.println(row);
        // for(List<Integer> row: BlockMultiplier.resultMatrix_4) System.out.println(row);

        // for(List<Integer> row: resultMatrix) System.out.println(row);

        return resultMatrix;
    }

    public static void main(String[] args) throws InterruptedException {
        List<List<Integer>> a = new ArrayList<>();
        ArrayList<Integer> a1 = new ArrayList<>();
        a1.add(1);
        a1.add(2);
        ArrayList<Integer> a2 = new ArrayList<>();
        a2.add(1);
        a2.add(2);
        ArrayList<Integer> a3 = new ArrayList<>();
        a3.add(1);
        a3.add(2);
        ArrayList<Integer> a4 = new ArrayList<>();
        a4.add(1);
        a4.add(2);
        a.add(a1);
        a.add(a2);
        a.add(a3);
        a.add(a4);

        List<List<Integer>> b = new ArrayList<>();
        ArrayList<Integer> b1 = new ArrayList<>();
        b1.add(1);
        b1.add(2);
        b1.add(3);
        b1.add(4);
        ArrayList<Integer> b2 = new ArrayList<>();
        b2.add(1);
        b2.add(2);
        b2.add(3);
        b2.add(4);
        b.add(b1);
        b.add(b2);
        ParallelizeMatMul(a, b);


    }
}

