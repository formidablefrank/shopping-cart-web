package com.example.dao.user;

import java.io.File;
import java.io.FileInputStream;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

public class UserDBTest extends DBTestCase {
	
	public UserDBTest(String name){
		super(name);
		System.setProperty(
			PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
			"com.mysql.jdbc.Driver");
		System.setProperty(
			PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
			"jdbc:mysql://localhost:3306/shoppingcart");
		System.setProperty(
			PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
			"root");
		System.setProperty(
			PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
			"");
		//System.setProperty(
		//	PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA,
		//	"com.mysql.jdbc.Driver");
	}

	@Test
	public void test() throws Exception {
		assertEquals(3, getDataSet().getTable("tbl_user").getRowCount());
		ITable dataset = getDataSet().getTable("tbl_user");
		//^ Outputs an ITable specifically the "user" table that can be found in our "standard" snapshot 
		Assertion.assertEquals(dataset, dataset);
	}
	
	@Test
	public void testdb() throws Exception {
		//Fetch database data after executing your code
		IDataSet databaseDataSet = getConnection().createDataSet();
		//^ represents the database as it now appears
		ITable actualTable = databaseDataSet.getTable("tbl_user");
		//^ Extract the current ITable that represents the "user" table
		
		//Load expected data from an XML dataset
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
			.build(new File("resources/data-snapshot-1.xml"));
		ITable expectedTable = expectedDataSet.getTable("tbl_user");
		//^ Creates an IDataSet from an arbitrary xml file, not necessarily the "standard" snapshot.
		// Again we get just one of the tables using the second line.
		
		//Insert actual database table match expected table
		Assertion.assertEquals(expectedTable, actualTable);
	}
	
	@Test
	public void testQueryType() throws Exception{
		// Objective: match the second field of one of the rows
		// select password
		// omit primary key column for query to query snapshot comparison
		ITable queriedTable = getConnection()
				.createQueryTable("something", "select fld_password from tbl_user where fld_username='admin'");
		//^ Here we make a query on the actual database to form an ITable
		// We can query just a part of the table.
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
			.build(new File("resources/query-table-result.xml"));
		ITable expectedTable = expectedDataSet.getTable("tbl_user");
		//^ Creates an IDataSet from an arbitrary xml file, not necessarily the "standard" snapshot.
		// Again we get just one of the tables using the second line.
		Assertion.assertEquals(expectedTable, queriedTable);
	}

	@Override
	/*
	 * Outputs an IDataSet that represents the contents of the "standard" snapshot.
	 * This is the snapshot we want loaded before every test.
	 * The database will be written with this image.
	 * @see org.dbunit.DatabaseTestCase#getDataSet()
	 */
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder()
			.build(new FileInputStream("resources/data-snapshot-1.xml"));
	}
	
	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception{
		return DatabaseOperation.CLEAN_INSERT;
	}
	
	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception{
		return DatabaseOperation.NONE;
	}

}
