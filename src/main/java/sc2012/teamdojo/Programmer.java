package sc2012.teamdojo;

import java.util.ArrayList;
import java.util.List;

public class Programmer {
    private int id;
	private String name;
    private List<String> skills = new ArrayList<String>();
    private List<String> recommends = new ArrayList<String>();
	//private List recommendations;



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    public void addRecommend(String recommend) {
       recommends.add(recommend);
    }

    public void addSkill(String skill) {
        recommends.add(skill);
    }



}
