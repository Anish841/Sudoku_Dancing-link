import java.io.BufferedReader;
import java.io.IOException;

class SudokuCell{
	int cellValue;
	int arr[];
	int valueCount;
}
public class SudokuOperation {
	int n2,n;
	public SudokuOperation(int n,int n2){
		this.n=n;
		this.n2=n2;
	}
	public void intializeSudoku(SudokuCell matrice[][],BufferedReader bf) throws IOException{
		int i,j;
		for(i=0;i<n2;i++){
			String str=bf.readLine();
			String str1[]=str.split("\\s+");
			for(j=0;j<n2;j++){
				matrice[i][j]=new SudokuCell();
				matrice[i][j].cellValue=Integer.parseInt(str1[j]);
				if(matrice[i][j].cellValue==0){
					matrice[i][j].arr=new int[n2];
					matrice[i][j].valueCount=n2;
				}
			}
		}
	}
	public void findPossileElements(SudokuCell matrice[][]){
		int i,j,k,l;
		for(i=0;i<n2;i++){
			for(j=0;j<n2;j++){
				if(matrice[i][j].cellValue==0){
					for(k=0;k<n2;k++){					//call row wise possibility
						if(matrice[i][k].cellValue!=0){
							if(matrice[i][j].arr[matrice[i][k].cellValue-1]==0){
								matrice[i][j].arr[matrice[i][k].cellValue-1]=1;
								matrice[i][j].valueCount--;
							}
						}
					
					}
					for(k=0;k<n2;k++){					//call column wise possiblity
						if(matrice[k][j].cellValue!=0){
							if(matrice[i][j].arr[matrice[k][j].cellValue-1]==0){
								matrice[i][j].arr[matrice[k][j].cellValue-1]=1;
								matrice[i][j].valueCount--;
							}
						}
					}
					int h=i/n,g=j/n;
					for(k=(h*n);k<((h*n)+n);k++){					//call box wise possiblity
						for(l=(g*n);l<((g*n)+n);l++){
							if(matrice[k][l].cellValue!=0){
								if(matrice[i][j].arr[matrice[k][l].cellValue-1]==0){
									matrice[i][j].arr[matrice[k][l].cellValue-1]=1;
									matrice[i][j].valueCount--;
								}
							}
						}
					}				
				}
			}
		}
	}
	void fillByRule1(SudokuCell matrice[][]){
		int i,j;
		for(i=0;i<n2;i++){
			for(j=0;j<n2;j++){
				if(matrice[i][j].cellValue==0 && matrice[i][j].valueCount==1){
					fillByRule1_Util(matrice,i,j);				//Fill elements by Rule 1
				}
			}
		}

	}
	void fillByRule1_Util(SudokuCell matrice[][],int i,int j){
		int k,value=0;
		if(matrice[i][j].cellValue==0 && matrice[i][j].valueCount==1){		//Update Single cell value
			for(k=0;k<n2;k++){
					if(matrice[i][j].arr[k]==0){
						matrice[i][j].arr[k]=1;
						matrice[i][j].valueCount--;
						matrice[i][j].cellValue=k+1;
						value=k;
					}
			}
		}	
		
		for(k=0;k<n2;k++){							//Row traverse
			if(matrice[i][k].cellValue==0 && matrice[i][k].arr[value]==0 && matrice[i][k].valueCount>2){
				matrice[i][k].arr[value]=1;
				matrice[i][k].valueCount--;
			}					
			if(matrice[i][k].cellValue==0 && matrice[i][k].valueCount==2 && matrice[i][k].arr[value]==0){
				matrice[i][k].arr[value]=1;
				matrice[i][k].valueCount--;
				fillByRule1_Util(matrice,i,k);				//recursive call
			}
		}
		for(k=0;k<n2;k++){							//Column traverse
			if(matrice[k][j].cellValue==0 && matrice[k][j].arr[value]==0 && matrice[k][j].valueCount>2){
				matrice[k][j].arr[value]=1;
				matrice[k][j].valueCount--;
			}				
			if(matrice[k][j].cellValue==0 && matrice[k][j].valueCount==2 && matrice[k][j].arr[value]==0){
				matrice[k][j].arr[value]=1;
				matrice[k][j].valueCount--;
				fillByRule1_Util(matrice,k,j);				//recursive call
			}
		}
		int h=i/n,g=j/n,l;
		for(k=(h*n);k<((h*n)+n);k++){						//call box traverse
			for(l=(g*n);l<((g*n)+n);l++){
				if(matrice[k][l].cellValue==0 && matrice[k][l].arr[value]==0 && matrice[k][l].valueCount>2){
					matrice[k][l].arr[value]=1;
					matrice[k][l].valueCount--;
				}				
				if(matrice[k][l].cellValue==0 && matrice[k][l].valueCount==2 && matrice[k][l].arr[value]==0){
					matrice[k][l].arr[value]=1;
					matrice[k][l].valueCount--;
					fillByRule1_Util(matrice,k,l);				//recursive call
				}
			}
		}
	}
	int fillByRule2(SudokuCell matrice[][]){
		int i,j,k,flag=0;
		for(i=0;i<n2;i++)
		{
			for(j=0;j<n2;j++)
			{
				if(matrice[i][j].cellValue==0 && matrice[i][j].valueCount>0)
				{
					for(k=0;k<n2;k++)
					{
						if(matrice[i][j].arr[k]==0)
						{
							if(!check_possible_val(matrice,i,j,k)){
								matrice[i][j].cellValue=k+1;
								matrice[i][j].valueCount=0;
								update_possible_values(matrice,i,j,k);
								flag=1;
								break;
							}
						}
					}	
				}	
			}
		}
	return flag;	
	}

boolean check_possible_val(SudokuCell matrice[][],int i,int j,int value)
{
	int k,l;
	boolean r_flag=false,c_flag=false,b_flag=false;
	for(k=0;k<n2;k++)						//Traverse Row to check value
	{
		if((j!=k)&&(matrice[i][k].cellValue==0)&&(matrice[i][k].arr[value]==0))
		{
			r_flag =true;
			break;
		}
	}
							
	for(k=0;k<n2;k++)						//Traverse Column to check Value
	{
		if((i!=k)&&(matrice[k][j].cellValue==0)&&(matrice[k][j].arr[value]==0))
		{
			c_flag=true;
			break;
		}
	}
				
	int h=i/n,g=j/n;
	for(k=(h*n);k<((h*n)+n);k++){					//Traverse box to check Value
		for(l=(g*n);l<((g*n)+n);l++){
			if(!(i==k && j==l) && matrice[k][l].cellValue==0 && matrice[k][l].arr[value]==0){
					b_flag=true;
					break;
			}
		}
	}			
	return(r_flag && c_flag && b_flag);
}

void update_possible_values(SudokuCell matrice[][],int i,int j,int value)
{
	int k,l;

	for(k=0;k<n2;k++)						//Update row values
	{
		if((matrice[i][k].cellValue==0)&&(matrice[i][k].arr[value]==0) && matrice[i][k].valueCount>0)
		{
			matrice[i][k].arr[value]=1;
			matrice[i][k].valueCount--;
		}
	}
							
	for(k=0;k<n2;k++)						//Update Column Values
	{
		if((matrice[k][j].cellValue==0)&&(matrice[k][j].arr[value]==0) && matrice[k][j].valueCount>0)
		{
			matrice[k][j].arr[value]=1;
			matrice[k][j].valueCount--;
		}
	}
		
	int h=i/n,g=j/n;
	for(k=(h*n);k<((h*n)+n);k++){					//Traverse box to check Value
		for(l=(g*n);l<((g*n)+n);l++){
			if(matrice[k][l].cellValue==0 && matrice[k][l].arr[value]==0){
				matrice[k][l].arr[value]=1;
				matrice[k][l].valueCount--;
			}
		}
	}
}
//print the sudoku
void print_sudoku(SudokuCell matrice[][])
{
	int i,j,k;
	for(k=0;k<n2+(n2/n)-1;k++)
		System.out.print("--");
	System.out.println();
	for(i=0;i<n2;i++)
	{
		System.out.print("|");
		for(j=0;j<n2;j++)
		{
			if(matrice[i][j].arr == null){
				System.out.print(matrice[i][j].cellValue+" ");
			}else if(matrice[i][j].cellValue!=0)
				System.out.print(""+matrice[i][j].cellValue+" ");
			else 
				System.out.print(""+matrice[i][j].cellValue+" ");
			if((j+1)%n==0)
				System.out.print("|");
		}
		System.out.println();
		if((i+1)%n==0)
		{
			for(k=0;k<n2+(n2/n)-1;k++)
			System.out.print("--");
			System.out.println();
		} 
	}
}
//Check if all cell are filled or not
public boolean checkAllFill(SudokuCell matrice[][]){
	boolean flag=true;
	for(int i=0;i<n2;i++){
		for(int j=0;j<n2;j++){
			if(matrice[i][j].cellValue==0){
				flag=false;
				break;
			}
		}
		if(!flag)
			break;
	}
	return flag;
}
//Check whether final ans is correct or not
public boolean verifySudokuUtility(SudokuCell matrice[][]){
	boolean flag=true;
	for(int i=0;i<n2;i++){
		for(int j=0;j<n2;j++){
			for(int k=0;k<n2;k++){							//Row traverse
				if(j!=k && matrice[i][j].cellValue==matrice[i][k].cellValue){
					flag=false;
					break;
				}
			}
			if(!flag)
				break;
			for(int k=0;k<n2;k++){							//Column traverse
				if(i!=k && matrice[i][j].cellValue==matrice[k][j].cellValue){
					flag=false;
					break;
				}
			}
			if(!flag)break;
			int h=i/n,g=j/n,l;
			for(int k=(h*n);k<((h*n)+n);k++){						//call box traverse
				for(l=(g*n);l<((g*n)+n);l++){
					if((i!=k || j!=l) && matrice[i][j].cellValue==matrice[k][l].cellValue){
						flag=false;
						break;
					}
				}
				if(!flag)
					break;
			}
		}
		if(!flag)break;
	}
	return flag;
}
}
