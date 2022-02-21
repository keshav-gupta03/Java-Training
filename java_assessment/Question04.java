package java_assessment;

public class Question04 {
    public static void main(String[] args) {
        int matrix[][] = { { 1, 3, 5 },
                { 3, 2, 4 },
                { 5, 4, 1 } };
        if(issymmetric(matrix,3))
            System.out.println("matrix is symmetric");
        else
            System.out.println("matrix is not symmetric");
    }
    static boolean issymmetric(int matrix[][],int n){
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (matrix[i][j] != matrix[j][i])
                    return false;
        return true;
    }
}
