package br.udc.engenharia.agenda.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.udc.engenharia.agenda.Application;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

/**
 * 
 * @author rodrigo
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners(mergeMode=MergeMode.MERGE_WITH_DEFAULTS, 
	listeners={
		TransactionDbUnitTestExecutionListener.class,
		CustomDbUnitTestExecutionListener.class
	}
)
@DatabaseSetup(value="/dataset/AbstractDataSet.xml", type=DatabaseOperation.DELETE_ALL)
public abstract class AbstractIntegrationTests 
{
	@Before
    public void setUp()
    {
    }
}