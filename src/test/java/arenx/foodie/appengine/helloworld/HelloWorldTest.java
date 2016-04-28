package arenx.foodie.appengine.helloworld;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.AdminDatastoreService.KeyBuilder;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import net.arenx.api.bean.IdBean;

public class HelloWorldTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
			).setEnvIsAdmin(true).setEnvIsLoggedIn(true);

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
	
	@Test
	public void ttt() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException{
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		
		Key k1 = KeyFactory.createKey("test", "111");
		Entity e1 = new Entity(k1);
		e1.setProperty("value", "sss");
		ds.put(e1);
		
		Key k2 = KeyFactory.createKey("test", "222");
		Entity e2 = new Entity(k2);
		e2.setProperty("value", "sdasdasdasda");
		ds.put(e2);
		
		Query q = new Query("test");

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = ds.prepare(q);


		System.out.println();
		System.out.println();
		for (Entity result : pq.asIterable()) {
			System.out.println("key="+result.getKey());
			System.out.println("value="+result.getProperty("value"));
		}
		
		//fail();
	}
}
