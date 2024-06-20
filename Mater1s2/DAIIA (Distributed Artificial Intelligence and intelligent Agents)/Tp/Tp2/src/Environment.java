import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Environment {
    public boolean[][] matrix;

    private final Lock lock = new ReentrantLock();

    public Environment() {
        matrix = new boolean[10][10];
        initializePollutedCells();
        printMatrix();
    }

    private void initializePollutedCells() {
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);
            matrix[x][y] = true;
        }
    }

    public boolean[][] getMatrix() {
        return matrix;
    }

    public void printMatrix() {
        lock.lock();  
        try {
            System.out.println("Current State of the Matrix:");
            for (boolean[] row : matrix) {
                for (boolean cell : row) {
                    System.out.print(cell ? "1 " : "0 ");
                }
                System.out.println();
            }
            System.out.println();
        } finally {
            lock.unlock();  
        }
    }
    public boolean isPolluted(int x, int y) {
       return matrix[x][y] ;
    }
    public void polluteCell(int x, int y) {
        matrix[x][y] = true;
    }

    public void cleanCell(int x, int y) {
        matrix[x][y] = false;
    }
}
