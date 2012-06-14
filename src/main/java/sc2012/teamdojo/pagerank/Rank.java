package sc2012.teamdojo.pagerank;

import java.util.ArrayList;
import java.util.List;

public abstract class Rank {
    
	private double alpha = 0.8;
	
	private double epsilon = 0.001;
	
	double[] pR;
	
	public Rank() {
	}
	
	public abstract PageRankMatrixH getH();
	
	public void build() throws Exception {
	    
        // check the results
		// getH().print();
        
        findPageRank(alpha, epsilon);        
    }

	public void findPageRank(double alpha, double epsilon) {
		
		// A counter for our iterations
		int k = 0; 
		
		// auxiliary variable
		PageRankMatrixH matrixH = getH();
		
		// The H matrix has size nxn and the PageRank vector has size n
		int n = matrixH.getSize();
		
		//auxiliary variable
		double inv_n = (double)1/n;
		
		// This is the actual nxn matrix of double values
		double[][] H = matrixH.getMatrix();
		
		// A dummy variable that holds our error -- 
		// arbitrarily set to an initial value of 1
		double error = 1;
		
		// This holds the values of the PageRank vector
        pR = new double[n];
        
        // This is a copy of the PageRank vector from the previous iteration
        // The only reason that we need this is for evaluating the error	
        double[] tmpPR = new double[n];

        // Set the initial values (ad hoc)
        for (int i=0; i < n; i++) {
        	pR[i] = inv_n;
        }

        
        double[][] dNodes= getDanglingNodeMatrix();
    	
    	double tNodes=(1 - alpha) * inv_n;

    	//Replace the H matrix with the G matrix
    	for (int i=0; i < n; i++) {
    		
        	for (int j=0; j < n; j++) {
        		        		
        		H[i][j] = alpha*H[i][j] + dNodes[i][j] + tNodes;
        	}
    	}

    	// Iterate until convergence.
    	// We have found the PageRank values if our error is smaller than epsilon 
    	while ( error >= epsilon) {
        	
            // Make a copy of the PageRank vector before we update it
        	for (int i=0; i < n; i++) {
            	tmpPR[i] = pR[i];
            }
        	         	
        	double dummy =0;
        	// Now we get the next point in the iteration
        	for (int i=0; i < n; i++) {
        	
        		dummy =0;
        		
        		for (int j=0; j < n; j++) {
            		
            		dummy += pR[j]*H[j][i];
            	}
        		
        		pR[i] = dummy;
        	}

        	// Get the error, so that we can check convergence
        	error = norm(pR,tmpPR);
        	
        	// Display the progress
        	System.out.println("\n Iteration: "+k +",   PageRank convergence error: "+error);
        	for(int i = 0; i < n; i++) {
        	    System.out.println("Index: " + i + " -->  PageRank: " + pR[i]);
        	}
        	//increase the value of the counter by one
        	k++;
        }
        
        // Report the final values

    	List<RelevanceScore> allRankings = new ArrayList<RelevanceScore>();
        for (int i=0; i < n; i++) {
            String url = matrixH.getIndexMapping().getValue(i);
            RelevanceScore r = new RelevanceScore(url, pR[i]);
            allRankings.add(r);
        }
        RelevanceScore.sort(allRankings);
        System.out.println("\n______________  Calculation Results  _______________\n");
        for (RelevanceScore r : allRankings) {
            System.out.printf("Page URL: %-42s  -->  Rank: %.15f\n", r.getId(), r.getScore());
        }
        System.out.println("\n____________________________________________________\n");        
	}
	
	private double norm(double[] a, double[] b) {
		
		double norm = 0;
		
		int n = a.length;
		
		for (int i=0; i < n; i++) {
			norm += Math.abs(a[i]-b[i]);
		}
		return norm;
	}

	
	private double[][] getDanglingNodeMatrix() {
		
		PageRankMatrixH matrixH = getH();
		
		int n = matrixH.getSize();
		
		double inv_n = (double)1/n;
		
		// The dangling node vector
		int[] dangling = matrixH.getDangling();
		
		double[][] dNodes = new double[n][n];
	   	
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				
				if (dangling[i] == 0) {
					dNodes[i][j] = 0;
				} else {
					dNodes[i][j] = alpha * inv_n;
				}
			}
		}

		return dNodes;
	}
	    	
	
	
	
	/**
	 * @return the alpha
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * @return the epsilon
	 */
	public double getEpsilon() {
		return epsilon;
	}

	/**
	 * @param epsilon the epsilon to set
	 */
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	/**
	 * @return the pR
	 */
	public double getPageRank(String url) {
		
		int i = getH().getIndexMapping().getIndex(url);
		
		return pR[i];
	}
}
