package sc2012.teamdojo

import org.codehaus.groovy.tools.ast.TestHarnessClassLoader;
import org.junit.Test
import org.junit.Before;

class ProNetTests {
    def programmers = []

    @Before
    void setup(){
		def network = new XmlSlurper().parse( new File(this.getClass().getResource("/ProNet.xml").getPath()));
        def index = 0

        network.Programmer.each { prog ->
            Programmer programmer = new Programmer()
            programmer.id = index++
            programmer.name = prog.@name
            prog.Recommendations.Recommendation.each { recommend ->
                programmer.recommends.add(recommend)
            }
            prog.Skills.Skill.each { skill ->
                programmer.skills.add(skill)
            }
            programmers.add(programmer)
        }





	}

    @Test
    void acceptanceTest1() {
        println "Name \t\t\t Skills \t\t\t Recommends"
        programmers.sort{it.name}
        programmers.each { programmer ->
            print programmer.name
            print " \t\t\t "
            print programmer.skills.join(", ")
            print " \t\t\t "
            print programmer.recommends.join(", ")
            println ""
        }
    }

    @Test
    void acceptanceTest2() {
        ProgrammerNetwork programmerNetwork = new ProgrammerNetwork(programmers.size())
        programmers.each {
           programmerNetwork.addProgrammer(it.id, it)
        }

        programmers.each { programmer ->
            programmers.each( {
//                if (programmer.contains())
            })
            programmerNetwork.connectProgrammers(programmer.id, )
        }
    }
}
