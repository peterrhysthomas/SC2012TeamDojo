package sc2012.teamdojo;

/**
 * 
 * TODO: 
 * 1. finish java implementation of network/graph
 * 2. re-write as groovy
 * 3. throw all that away and use http://jgrapht.org (thank you filippo...)
 *
 */
public class ProgrammerNetwork {
	private Programmer[] programmers;
	private Edge[][] programmerConnections;
	private int maxNumberOfProgrammers;
	private int programmerCount;

	public ProgrammerNetwork(int n) {
		programmers = new Programmer[n];
		programmerConnections = new Edge[n][n];
		maxNumberOfProgrammers = n;
	}

	public void addProgrammer(int programmerNumber, Programmer p) {
		// TODO: array boundary checks.
		// TODO: copy programmer object, rather than use reference.
		programmers[programmerNumber] = p;
		programmerCount++;
	}

	public void connectProgrammers(int fromProgrammerNumber, int toProgrammerNumber) {
		programmerConnections[fromProgrammerNumber][toProgrammerNumber] = new Edge(programmers[fromProgrammerNumber], programmers[toProgrammerNumber]);
	}
}
