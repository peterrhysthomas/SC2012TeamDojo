package sc2012.teamdojo

import org.codehaus.groovy.tools.ast.TestHarnessClassLoader;
import org.junit.Test;

class ProNetTests {
	
	@Test
    void dummy(){
		def network = new XmlSlurper().parse( new File(this.getClass().getResource("/ProNet.xml").getPath()));
        def programmers = []

        network.Programmer.each { prog ->
            Programmer programmer = new Programmer()
            programmer.name = prog.@name
            prog.Recommendations.each { recommend ->
                programmer.recommends.add(recommend)
            }
            prog.Skills.each { skill ->
                programmer.skills.add(skill)
            }
            programmers.add(programmer)
        }

        programmers.each {
            println it.name + ' ' + it.skills + ' ' + it.recommends
        }

	}
}
