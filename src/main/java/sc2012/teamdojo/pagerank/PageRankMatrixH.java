package sc2012.teamdojo.pagerank;


// Sub-stochastic matrix - some rows will have all zeros
public class PageRankMatrixH {
	
    private ValueToIndexMapping indexMapping = new ValueToIndexMapping();
    
    double[][] matrix;
    
    private int numberOfProgrammersWithNoLinks = 0;

    
    public PageRankMatrixH(int nProgrammers) {
        matrix = new double[nProgrammers][nProgrammers];
    }
    
    public int getNumberOfProgrammersWithNoLinks() {
        return this.numberOfProgrammersWithNoLinks;
    }
    
    public double[][] getMatrix() {
        return matrix;
    }
    
    /**
     * Just associate programmer with an index. Used for programmers that
     * have no outlinks.
     */
    public void addLink(String programmer) {
        indexMapping.getIndex(programmer);
    }
    
    public void addLink(String fromProgrammer, String toProgrammer, double weight) {
        int i = indexMapping.getIndex(fromProgrammer);
        int j = indexMapping.getIndex(toProgrammer);
        
        try {
            
            matrix[i][j] = weight;
            
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("fromProgrammer:" + fromProgrammer + ", toProgrammer: " + toProgrammer);
        }
    }
    public void addLink(String fromProgrammer, String toProgrammer) {
        addLink(fromProgrammer, toProgrammer, 1);
    }

    public void calculate() {
    	
        for(int i = 0, n = matrix.length; i < n; i++) {
        	
            double rowSum = 0;
            
            for(int j = 0, k = matrix.length; j < k; j++) {
            
            	rowSum += matrix[i][j];
            }
            
            if( rowSum > 0 ) {
            
            	for(int j = 0, k = matrix.length; j < k; j++) {
                
            		if( matrix[i][j] > 0 ) {
                    
            			matrix[i][j] = matrix[i][j] / rowSum;
                    }
                }
            	
            } else {
                
            	numberOfProgrammersWithNoLinks++;
            }
        }
    }
    
    public void print() {

    	StringBuilder txt = new StringBuilder("H Matrix\n\n");
    	
    	for (int i = 0, n = matrix.length; i < n; i++) {
    		txt.append("Index: ").append(i);
    		txt.append("  -->  ");
    		txt.append("Programmer ID: ").append(indexMapping.getValue(i));
    		txt.append("\n");
    	}
    	
    	txt.append("\n").append("\n");
    	
    	for (int i = 0, n = matrix.length; i < n; i++) {

			for (int j = 0, k = matrix.length; j < k; j++) {

				txt.append(" ");
				txt.append(matrix[i][j]);
				
				if (j < k-1) {
					txt.append(", ");
				} else {
					txt.append("\n");
				}
			}
		}
    	
    	System.out.println(txt.toString());
	}
    
    public int getSize() {
    	return matrix.length;
    }

    /** 
     * A <B>dangling node</B> corresponds to a programmer that has no outlinks.
     * These nodes result in a H row that has all its values equal to 0.
     */
	public int[] getDangling() {
		
		int   n = getSize();
		int[] d = new int[n];
		
		boolean foundOne = false;
		
		for (int i=0; i < n; i++) {
			
			for (int j=0; j < n; j++) {
			
				if (matrix[i][j] > 0) {
					foundOne = true;
					break;
				} 
			}
			
			if (foundOne) {
				d[i] = 0;
			} else {
				d[i] = 1;
			}
			
			foundOne = false;
		}
		
		return d;
	}

	/**
	 * @return the indexMapping
	 */
	public ValueToIndexMapping getIndexMapping() {
		return indexMapping;
	}
}
