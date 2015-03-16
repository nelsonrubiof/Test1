/**
 * Clase para ejecutar manualmente todos los JUnit Test
 * se deben agregar los suite generardos a mano o automaticos para manatener actualizado los test
 * 
 */

import com.scopix.periscope.PeriscopeSuite;
import com.scopix.periscope.corporatestructuremanagement.CorporatestructuremanagementSuite;
import com.scopix.periscope.evaluationmanagement.evaluators.EvaluatorsSuite;
import com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator.SpringbeanevaluatorSuite;
import com.scopix.periscope.extractionplanmanagement.ExtractionplanmanagementSuite;
import com.scopix.periscope.templatemanagement.TemplatemanagementSuite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author nelson
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    PeriscopeSuite.class,
    CorporatestructuremanagementSuite.class,
    com.scopix.periscope.corporatestructuremanagement.commands.CommandsSuite.class,
    com.scopix.periscope.evaluationmanagement.commands.CommandsSuite.class,
    com.scopix.periscope.corporatestructuremanagement.CorporatestructuremanagementSuite.class,
    com.scopix.periscope.extractionplanmanagement.commands.CommandsSuite.class,
    com.scopix.periscope.templatemanagement.commands.CommandsSuite.class,
    EvaluatorsSuite.class,
    SpringbeanevaluatorSuite.class,
    ExtractionplanmanagementSuite.class,
    TemplatemanagementSuite.class
})
public class RootTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
