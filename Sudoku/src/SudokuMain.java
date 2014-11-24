import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class SudokuMain {
	public static void main(String[] args) throws NumberFormatException, IOException {
		int n;
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		n=Integer.parseInt(bf.readLine());
		int n2=n*n;
		SudokuCell matrice[][]=new SudokuCell[n2][n2];
		SudokuOperation so=new SudokuOperation(n,n2);
		so.intializeSudoku(matrice,bf);					//intialize sudoku
		long millis = System.currentTimeMillis();
		so.findPossileElements(matrice);				//find possible elements
		so.fillByRule1(matrice);						//Fill elements by Rule 1
		while(so.fillByRule2(matrice)==1){				//Fill elements by rule2 and call rule1
			so.fillByRule1(matrice);					//recursively call rule 1
		}
		//Applied rule1 and rule2 first then apply dancing link to solve
		if(!so.checkAllFill(matrice)){					//check whether all elements are filled by rule1/rule2
			DancingLink d=new DancingLink();			//Call dancing link procedure and classes
			d.executeDancingLink(n, matrice);
		}

		long millis1 = System.currentTimeMillis();
		so.print_sudoku(matrice);						//print the final matrices
		System.out.println("Time Taken:->"+(double)(millis1-millis)/1000+" seconds");
		
		/* use if want to check whether output is correct or not*/
//		if(so.verifySudokuUtility(matrice)){
//			System.out.println("Yes Its correct");
//		}else{
//			System.out.println("Some issue");
//		}
	}
}
