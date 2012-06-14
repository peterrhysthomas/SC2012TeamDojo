package sc2012.teamdojo;

public class MatrixGraph {
	private Programmer[] programmers;
	private Edge[][] programmerConnections;
	private int maxNumberOfProgrammers;
	private int programmerCount;

	public MatrixGraph(int n) {
		programmers = new Programmer[n];
		programmerConnections = new Edge[n][n];
		maxNumberOfProgrammers = n;
	}

	public void insertVertex(int programmerNumber, Programmer p) {
		// TODO: array boundary checks.
		// TODO: copy programmer object, rather than use reference.
		programmers[programmerNumber] = p;
		programmerCount++;
	}

}
