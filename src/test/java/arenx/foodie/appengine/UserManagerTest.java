package arenx.foodie.appengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import net.arenx.PMF;
import net.arenx.UserManager;
import net.arenx.api.bean.UserBean;
import net.arenx.jdo.UserJDO;

public class UserManagerTest {
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
	public void get_appengin_user_firsttime()  {
		Date start = new Date();
		String eEmail = RandomStringUtils.randomAlphabetic(10)+"@test.test";
		String eDomain = RandomStringUtils.randomAlphabetic(10)+".test";
		String eid = "testID_"+RandomStringUtils.randomAlphabetic(10);
		User eUser = new User(eEmail, eDomain, eid);
		
		UserManager m = UserManager.instance();
		UserBean aUser = m.get(eUser);		
		Date end = new Date();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(UserJDO.class, "appengineId == idParam");
		q.declareParameters("String idParam");
		List<UserJDO> results = (List<UserJDO>) q.execute(eid);
		UserJDO eUserjdo = results.get(0);
		
		assertTrue(1 == results.size());
		
		assertNotNull(aUser.getId());
		assertEquals(aUser.getEmail(), eEmail);
		assertEquals(aUser.getEmail(), eEmail);
		assertTrue(start.getTime() <= aUser.getCreateDate().getTime() && aUser.getCreateDate().getTime() <= end.getTime());
		
		assertEquals(eUserjdo.getAppengineId(), eid);
		assertEquals(eUserjdo.getEmail(), eEmail);
		assertTrue(start.getTime() <= eUserjdo.getCreateDate().getTime() && eUserjdo.getCreateDate().getTime() <= end.getTime());
		assertEquals(eUserjdo.getId(), aUser.getId());
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q1 = new com.google.appengine.api.datastore.Query("user");
		PreparedQuery pq = ds.prepare(q1);
		assertEquals(1, pq.countEntities());
		Entity result = pq.asSingleEntity();
		assertEquals(eEmail, result.getProperty("email"));
		assertEquals(eid, result.getProperty("appengine_id"));
	}
	
	@Test
	public void get_appengin_user_secondtime() {
		Date start = new Date();
		String eEmail = RandomStringUtils.randomAlphabetic(10)+"@test.test";
		String eDomain = RandomStringUtils.randomAlphabetic(10)+".test";
		String eid = "testID_"+RandomStringUtils.randomAlphabetic(10);
		User eUser = new User(eEmail, eDomain, eid);
		
		UserManager m = UserManager.instance();
		UserBean aUser = m.get(eUser);
		aUser = m.get(eUser);
		Date end = new Date();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(UserJDO.class, "appengineId == idParam");
		q.declareParameters("String idParam");
		List<UserJDO> results = (List<UserJDO>) q.execute(eid);
		UserJDO eUserjdo = results.get(0);
		
		assertTrue(1 == results.size());
		
		assertNotNull(aUser.getId());
		assertEquals(aUser.getEmail(), eEmail);
		assertEquals(aUser.getEmail(), eEmail);
		assertTrue(start.getTime() <= aUser.getCreateDate().getTime() && aUser.getCreateDate().getTime() <= end.getTime());
		
		assertEquals(eUserjdo.getAppengineId(), eid);
		assertEquals(eUserjdo.getEmail(), eEmail);
		assertEquals(eUserjdo.getCreateDate(), aUser.getCreateDate());
		assertTrue(start.getTime() <= eUserjdo.getCreateDate().getTime() && eUserjdo.getCreateDate().getTime() <= end.getTime());
		assertEquals(eUserjdo.getId(), aUser.getId());
	}
	
	@Test
	public void get_appengin_multiuser() {
		String eEmail = RandomStringUtils.randomAlphabetic(10)+"@test.test";
		String eDomain = RandomStringUtils.randomAlphabetic(10)+".test";
		String eid = "testID_"+RandomStringUtils.randomAlphabetic(10);

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity user1 = new Entity("user");
		Entity user2 = new Entity("user");
		user1.setProperty("appengine_id", eid);
		user2.setProperty("appengine_id", eid);
		ds.put(user1);
		ds.put(user2);
		
		UserManager m = UserManager.instance();
		User eUser = new User(eEmail, eDomain, eid);
		try{
			m.get(eUser);
			fail();
		}catch(IllegalStateException e) {
			
		}
	}
	
	@Test
	public void get() throws InterruptedException {
		Date eDate = new Date();
		String eEmail = RandomStringUtils.randomAlphabetic(10)+"@test.test";
		String eid = "testID_"+RandomStringUtils.randomAlphabetic(10);

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity user = new Entity("user");
		user.setProperty("appengine_id", eid);
		user.setProperty("create_date", eDate);
		user.setProperty("email", eEmail);
		Key eKey = ds.put(user);
				
		UserManager m = UserManager.instance();
		UserBean eUserbean = m.get(eKey.getId());
		
		assertEquals(1, ds.prepare(new com.google.appengine.api.datastore.Query("user")).countEntities());
		assertEquals(eUserbean.getId(), (Long)eKey.getId());
		assertEquals(eUserbean.getEmail(), eEmail);
		assertEquals(eUserbean.getCreateDate().getTime(), eDate.getTime());
	}
	
	@Test
	public void get_notExist() throws InterruptedException {
		UserManager m = UserManager.instance();
		UserBean eUserbean = m.get(1l);
		assertNull(eUserbean);
	}
	
	@Test
	public void isExist_true() throws InterruptedException {
		Date eDate = new Date();
		String eEmail = RandomStringUtils.randomAlphabetic(10)+"@test.test";
		String eid = "testID_"+RandomStringUtils.randomAlphabetic(10);

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity user = new Entity("user");
		user.setProperty("appengine_id", eid);
		user.setProperty("create_date", eDate);
		user.setProperty("email", eEmail);
		Key eKey = ds.put(user);
				
		UserManager m = UserManager.instance();

		assertTrue(m.isExist(eKey.getId()));
	}
	
	@Test
	public void isExist_false() throws InterruptedException {
		UserManager m = UserManager.instance();
		assertFalse(m.isExist(1l));
	}
}
