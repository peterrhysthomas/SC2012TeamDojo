package sc2012.teamdojo;

public class Edge {

	private Programmer fromProgrammer;
	private Programmer toProgrammer;

	public Edge(Programmer fromProgrammer, Programmer toProgrammer) {
		super();
		this.fromProgrammer = fromProgrammer;
		this.toProgrammer = toProgrammer;
	}

	@Override
	public String toString() {
		return "Edge [fromProgrammer=" + fromProgrammer + ", toProgrammer="
				+ toProgrammer + "]";
	}

}
