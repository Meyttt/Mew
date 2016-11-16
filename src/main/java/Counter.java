import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Admin on 15.11.2016.
 */
public class Counter {
	public static Integer getE(int d, int k){
		int c= d>k ?d :k;
		int a=d;
		int b=k;
		int matrixE[][] = {{1,0},{0,1}};
		while( a%b!=0){
			int r=a%b;
			int q=a/b;
			a=b;
			b=r;
//			if(q>0){
//				q=q-c;
//			}
			matrixE = matrixMultiplication(matrixE,new int[][]{{0,1},{1,-1*q}});
			showMatrix(matrixE);

		}
		System.out.println(matrixE[0][1]);
		System.out.println(matrixE[1][1]);
		if (matrixE[0][1]<0){
			return c-matrixE[0][1];
		}
		return (matrixE[0][1]);

	}
	private static int[][] matrixMultiplication(int[][] originalMatrix, int[][] secondlMatrix){
		int result[][] = new int[2][2];
		for(int i=0; i<originalMatrix.length;i++){
			int[] row = originalMatrix[i];
			for(int j=0;j<row.length;j++){
				result[i][j]= row[0]*secondlMatrix[0][j]+row[1]*secondlMatrix[1][j];
			}
		}
		return  result;
	}
	public static void showMatrix(int[][] matrix){
		for(int i=0; i<matrix.length;i++){
			int[] row = matrix[i];
			for(int j=0; j<row.length;j++){
				System.out.print(row[j]+"===");
			}
			System.out.println();
		}
	}

	public static void main1(String[] args) {
		int d=37;
		int k=41;
		int e = getE(d,k);
		System.out.println(e);
		System.out.println((e*d)%k);

	}

	public static void main(String[] args) {
		int d = 113;
		PrimeNumbers primeNumbers = new PrimeNumbers(1000);
		ArrayList<Integer> integers = primeNumbers.getPrimeNumbers();
		Random rand = new Random();
		int p =integers.get(rand.nextInt(integers.size()));
		int q =integers.get(rand.nextInt(integers.size()));
		int n = p*q;
		int k=(p-1)*(q-1);
		int e = getE(d,k);
		System.out.println(n);
		System.out.println(k);
		System.out.println(e);

	}
}

