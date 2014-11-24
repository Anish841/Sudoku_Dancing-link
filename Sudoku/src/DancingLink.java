import java.util.Stack;
class DancingCell{							//Store necessary information about each cell in dancing link
	DancingCell L,R,U,D;
	int i,j;
	int count;
	ActualMatrixCell amc=null;
	DancingCell(DancingCell leftmost,DancingCell cellHeader,int _i,int _j){
		if(leftmost==null){
			L=this;
			R=this;
		}else{
			L   = leftmost.L;
		    R   = leftmost;
		    L.R = this;
		    R.L = this;
		}
		if(cellHeader==null){
			U=this;
			D=this;
		}else{
			 U = cellHeader.U;
			 D   = cellHeader;
			 U.D = this;
			 D.U = this;
		}
		i=_i;
		j=_j;count=0;
	}
	  void hideCellvertical() {
			U.D = D;
			D.U = U;
	  }
		    
     void unhideCellVertical() {
			D.U = this;
			U.D = this;
     }

     void hideCellHorizontal() {
			R.L = L;
			L.R = R;
     }

     void unhideCellHorizontal() {
			L.R = this;
			R.L = this;
     }
}
class ActualMatrixCell{		
	int ar,ac,av;
	public ActualMatrixCell(int ar,int ac,int av) {
		this.ar=ar;
		this.ac=ac;
		this.av=av;
	}
}
class ExactCover{
	DancingCell h,ch[];							
	int n,noCol,n2;					
	int rowCount=0;
	Stack<ActualMatrixCell> possibleSolution=null;
	public ExactCover(int _n) {
		h=new DancingCell(null,null,-1,-1);
		n=_n;
		n2=n*n;
		noCol=4*(n2*n2);
		ch=new DancingCell[noCol];
		for(int i=0;i<noCol;i++){
			ch[i]=new DancingCell(h,null, -1, i);
		}
		possibleSolution=new Stack<ActualMatrixCell>();
	}
	public void makeExactCover(int n,SudokuCell matrice[][]){
		ActualMatrixCell amc=null;
		for(int r=0;r<(n2);r++){
			for(int c=0;c<(n2);c++){
				if(matrice[r][c].cellValue==0){
					for(int k=0;k<(n2);k++){
						if(matrice[r][c].arr[k]==0){
							amc=new ActualMatrixCell(r,c,k+1);
							addCell(r, c, k+1, rowCount,amc);
							rowCount++;
						}
					}
				}else{
					int value=matrice[r][c].cellValue;
					amc=new ActualMatrixCell(r,c,value);
					addCell(r, c, value, rowCount,amc);
					rowCount++;
				}
			}
		}
	}
	public void addCell(int r,int c,int value,int rowCount,ActualMatrixCell amc){
		int cellV=getcellHeaderNo(r, c);
		int rowV=getRowHeaderNo(r)+value;
		int colV=getColHeaderNo(c)+value;
		int boxV=getboxHeaderNo(r, c)+value;
		ch[cellV].count++;
		ch[rowV].count++;
		ch[colV].count++;
		ch[boxV].count++;
		DancingCell cellReference=null;
		DancingCell newCell=null;
		cellReference=new DancingCell(cellReference,ch[cellV], rowCount, cellV);
		cellReference.amc=amc;
		newCell=new DancingCell(cellReference,ch[rowV], rowCount, rowV);
		newCell.amc=amc;
		newCell=new DancingCell(cellReference,ch[colV], rowCount, colV);
		newCell.amc=amc;
		newCell=new DancingCell(cellReference,ch[boxV], rowCount, boxV);	
		newCell.amc=amc;
	}
	public int getRowHeaderNo(int r){
		return (((n2*n2)-1)+(r)*(n2));
	}
	public int getColHeaderNo(int c){
		return ((2*(n2*n2)-1)+(c*n2));
	}
	public int getcellHeaderNo(int r,int c){
		return (r*(n2)+c);
	}
	public int getboxHeaderNo(int r,int c){
		int q=((r/n)*n+(c/n))*(n2);
		return ((3*(n2*n2)-1)+q);
	}
	public boolean findSolution(){
		if(h.R==h)
			return true;
		int min=Integer.MAX_VALUE;
		DancingCell minHeader=null;
		for(DancingCell j=h.R;j!=h;j=j.R){				
			if(j.count<min){
				minHeader=j;
				min=j.count;
			}
		}
		findCover(minHeader.j);
		for (DancingCell r=minHeader.D; r!=minHeader; r=r.D) {
            possibleSolution.push(r.amc);  
            for (DancingCell l=r.R; l!=r; l=l.R)
                findCover(l.j);
            if (findSolution())
                return true;
            for (DancingCell l=r.L; l!=r; l=l.L)  // backtrack
                uncoverAll(l.j);
           possibleSolution.pop();
        }
        uncoverAll(minHeader.j);
        return false;
		
	}
	 void findCover(int j) {                    
		 DancingCell c=ch[j];
			c.hideCellHorizontal();
			for (DancingCell k=c.D; k!=c; k=k.D){
			    for (DancingCell l=k.R; l!=k; l=l.R) {
					l.hideCellvertical();
					ch[l.j].count--;               
			    }
		    }
	 }
	 void uncoverAll(int j) {
		 DancingCell c=ch[j];
			for (DancingCell i=c.U; i!=c; i=i.U)
			    for (DancingCell l=i.L; l!=i; l=l.L) { 
				ch[l.j].count++;               
				l.unhideCellVertical();
			    }
			c.unhideCellHorizontal();
     }
}
public class DancingLink{
	public void executeDancingLink(int n,SudokuCell matrice[][]){
		ExactCover e=new ExactCover(n);
		e.makeExactCover(n, matrice);
		if(e.findSolution()){
			for(ActualMatrixCell i:e.possibleSolution){
				matrice[i.ar][i.ac].cellValue=i.av;
			}
		}
	}
}