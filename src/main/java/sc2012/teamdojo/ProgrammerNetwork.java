package sc2012.teamdojo;

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

}
