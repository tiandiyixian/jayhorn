/**
 * 
 */
package jayhorn.test.assorted_tests;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import jayhorn.checker.Checker;
import jayhorn.checker.EldaricaChecker;
import jayhorn.checker.SpacerChecker;
import jayhorn.solver.ProverFactory;
import jayhorn.solver.princess.PrincessProverFactory;
import jayhorn.solver.spacer.SpacerProverFactory;
import jayhorn.test.Util;
import scala.actors.threadpool.Arrays;
import soottocfg.cfg.Program;
import soottocfg.soot.SootToCfg;

/**
 * @author schaef
 *
 */
@RunWith(Parameterized.class)
public class SimpleHornTest {

	private static final String userDir = System.getProperty("user.dir") + "/";
	private static final String testRoot = userDir + "src/test/resources/";

	private File sourceFile;

	@Parameterized.Parameters(name = "{index}: check ({1})")
	public static Collection<Object[]> data() {
		List<Object[]> filenames = new LinkedList<Object[]>();
		final File source_dir = new File(testRoot + "horn-encoding/arrays");
		collectFileNamesRecursively(source_dir, filenames);
		if (filenames.isEmpty()) {
			throw new RuntimeException("Test data not found!");
		}
		return filenames;
	}
	
	private static void collectFileNamesRecursively(File file, List<Object[]> filenames) {
		File[] directoryListing = file.listFiles();
		if (directoryListing != null) {
			Arrays.sort(directoryListing);
			for (File child : directoryListing) {
				if (child.isFile() && child.getName().endsWith(".java")) {
					filenames.add(new Object[] { child, child.getName() });
				} else if (child.isDirectory()) {
					collectFileNamesRecursively(child, filenames);
				} else {
					// Ignore
				}
			}
		}
	}

	public SimpleHornTest(File source, String name) {
		this.sourceFile = source;
	}

//	@Test
//	public void testWithPrincess() {
//		verifyAssertions(new PrincessProverFactory(), false);
//	}

	@Test
	public void testWithSpacer() {
		verifyAssertions(new SpacerProverFactory(), true);
	}

	
	protected void verifyAssertions(ProverFactory factory, boolean is_spacer) {
		System.out.println("\nRunning test " + this.sourceFile.getName() + " with "+factory.getClass()+"\n");
		File classDir = null;
		try {
			classDir = Util.compileJavaFile(this.sourceFile);
			SootToCfg soot2cfg = new SootToCfg();
//			soottocfg.Options.v().setPrintCFG(true);
			soottocfg.Options.v().setMemPrecision(3);
//			soottocfg.Options.v().setInlineCount(3);
//			soottocfg.Options.v().setInlineMaxSize(20);
			soot2cfg.run(classDir.getAbsolutePath(), null);
			

			jayhorn.Options.v().setTimeout(300);
			Program program = soot2cfg.getProgram();
			boolean result;
			
			if (is_spacer){
				SpacerChecker spacer = new SpacerChecker(factory);
				
				result = spacer.checkProgram(program);
			} else {
				EldaricaChecker eldarica = new EldaricaChecker(factory);
				result = eldarica.checkProgram(program);
			}
		 
			boolean expected = this.sourceFile.getName().startsWith("Sat");
			Assert.assertTrue("For "+this.sourceFile.getName()+": expected "+expected + " but got "+result, expected==result);

		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		} finally {
			if (classDir!=null) {
				classDir.deleteOnExit();
			}
		}	
	}
		
}
