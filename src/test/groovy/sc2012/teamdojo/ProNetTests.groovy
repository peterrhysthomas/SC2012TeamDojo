package sc2012.teamdojo

import org.codehaus.groovy.tools.ast.TestHarnessClassLoader;
import org.junit.Test;

class ProNetTests {
	
	@Test
    void dummy(){
		def records = new XmlSlurper().parse( new File(this.getClass().getClassLoader().getResource("ProNet.xml").getPath()));
		println records
		
	}
	

}
