package arenx.foodie.appengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import net.arenx.PhotoManager;
import net.arenx.api.bean.PhotoBean;

public class PhotoManagerTest {
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
	public void add()  {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Date start = new Date();
		Key e_locationKey = ds.put(new Entity("location"));
		Key e_userKey = ds.put(new Entity("user"));
		String e_description = RandomStringUtils.random(10);
		
		PhotoManager pm = PhotoManager.instance();
		PhotoBean a_photp = pm.add(e_userKey.getId(), e_locationKey.getId(), e_description);
		Date end = new Date();
		
		assertEquals((Long)e_userKey.getId(), a_photp.getUser().getId());
		assertEquals((Long)e_locationKey.getId(), a_photp.getLocation().getId());
		assertEquals(e_description, a_photp.getDescription());
		assertTrue(0 >= start.compareTo(a_photp.getCreateDate()));
		assertTrue(0 <= end.compareTo(a_photp.getCreateDate()));
		
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("photo");
		PreparedQuery pq = ds.prepare(q);
		assertEquals(1, pq.countEntities());
		Entity result = pq.asSingleEntity();
		assertEquals(e_userKey, result.getProperty("user_id"));
		assertEquals(e_locationKey, result.getProperty("location_id"));
		assertEquals(e_description, result.getProperty("description"));
	}
	
	@Test
	public void add_nullDescription()  {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Date start = new Date();
		Key e_locationKey = ds.put(new Entity("location"));
		Key e_userKey = ds.put(new Entity("user"));
		String e_description = null;
		
		PhotoManager pm = PhotoManager.instance();
		PhotoBean a_photp = pm.add(e_userKey.getId(), e_locationKey.getId(), e_description);
		Date end = new Date();
		
		assertEquals((Long)e_userKey.getId(), a_photp.getUser().getId());
		assertEquals((Long)e_locationKey.getId(), a_photp.getLocation().getId());
		assertEquals(e_description, a_photp.getDescription());
		assertTrue(0 >= start.compareTo(a_photp.getCreateDate()));
		assertTrue(0 <= end.compareTo(a_photp.getCreateDate()));
		
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("photo");
		PreparedQuery pq = ds.prepare(q);
		assertEquals(1, pq.countEntities());
		Entity result = pq.asSingleEntity();
		assertEquals(e_userKey, result.getProperty("user_id"));
		assertEquals(e_locationKey, result.getProperty("location_id"));
		assertEquals(e_description, result.getProperty("description"));
	}
	
	@Test
	public void add_desTooLong()  {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Long e_locationId = ds.put(new Entity("location")).getId();
		Long e_userId = ds.put(new Entity("user")).getId();
		String e_description = RandomStringUtils.random(1001);
		
		PhotoManager pm = PhotoManager.instance();
		
		try {
			pm.add(e_userId, e_locationId, e_description);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void add_noLocation()  {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Long e_userId = ds.put(new Entity("user")).getId();
		String e_description = RandomStringUtils.random(1001);
		
		PhotoManager pm = PhotoManager.instance();
		
		try {
			pm.add(e_userId, 123l, e_description);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void add_noUser()  {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Long e_locationId = ds.put(new Entity("location")).getId();
		String e_description = RandomStringUtils.random(1001);
		
		PhotoManager pm = PhotoManager.instance();
		
		try {
			pm.add(123l, e_locationId, e_description);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void add_invalLocationId()  {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Long e_locationId = 123l;
		Long e_userId = ds.put(new Entity("user")).getId();
		String e_description = RandomStringUtils.random(1001);
		
		PhotoManager pm = PhotoManager.instance();
		
		try {
			pm.add(e_userId, e_locationId, e_description);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void add_invalUserId()  {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Long e_locationId = ds.put(new Entity("location")).getId();
		Long e_userId = 123l;
		String e_description = RandomStringUtils.random(1001);
		
		PhotoManager pm = PhotoManager.instance();
		
		try {
			pm.add(e_userId, e_locationId, e_description);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void get(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Key e_locationKey = ds.put(new Entity("location"));
		Key e_userKey = ds.put(new Entity("user"));
		String e_description = RandomStringUtils.random(10);
		
		Entity photoEntity = new Entity("photo");
		photoEntity.setProperty("location_id", e_locationKey);
		photoEntity.setProperty("user_id", e_userKey);
		photoEntity.setProperty("description", e_description);
		Long e_photoId = ds.put(photoEntity).getId();
		
		PhotoManager pm = PhotoManager.instance();
		PhotoBean a_photp = pm.get(e_photoId);
		
		assertEquals(e_photoId, a_photp.getId());
		assertEquals(e_description, a_photp.getDescription());
		assertEquals((Long)e_locationKey.getId(), a_photp.getLocation().getId());
		assertEquals((Long)e_userKey.getId(), a_photp.getUser().getId());
	}
	
	@Test
	public void get_notExist(){
		assertNull(PhotoManager.instance().get(123l));
	}
	
	@Test
	public void get_photos(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Key e_locationKey = ds.put(new Entity("location"));

		int e_photoSize = RandomUtils.nextInt(2, 10);
		List<Key>e_photoKeys = new ArrayList<Key>();
		for (int i =0;i<e_photoSize;i++){
			Entity photoEntity = new Entity("photo");
			photoEntity.setProperty("location_id", e_locationKey);
			e_photoKeys.add(ds.put(photoEntity));
		}
				
		PhotoManager pm = PhotoManager.instance();
		List<PhotoBean> a_photos = pm.getallFromLocation(e_locationKey.getId());
		
		assertEquals(e_photoSize, a_photos.size());
		loop:
		for (Key e_photoKey: e_photoKeys) {
			for (PhotoBean a_photo: a_photos) {
				if (e_photoKey.getId() == a_photo.getId()){
					assertEquals((Long)e_locationKey.getId(), a_photo.getLocation().getId());
					continue loop;
				}
			}
			fail();
		}
	}
	
	@Test
	public void get_photos_empty(){
				
		PhotoManager pm = PhotoManager.instance();
		List<PhotoBean> a_photos = pm.getallFromLocation(123l);
		
		assertEquals(0, a_photos.size());
	}
	
	@Test
	public void like(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Key e_photoKey = ds.put(new Entity("photo"));
		Key e_userKey = ds.put(new Entity("user"));
				
		PhotoManager pm = PhotoManager.instance();
		pm.like(e_userKey.getId(), e_photoKey.getId());
		
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("like_photo");
		PreparedQuery pq = ds.prepare(q);

		assertEquals(1, pq.countEntities());

		Entity result = pq.asSingleEntity();
		assertEquals(e_userKey, result.getProperty("user_id"));
		assertEquals(e_photoKey, result.getProperty("photo_id"));
		assertEquals(true, (boolean)result.getProperty("like"));

	}
	
	@Test
	public void like_overrideLike(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Key e_photoKey = ds.put(new Entity("photo"));
		Key e_userKey = ds.put(new Entity("user"));
		
		Entity likeEntity = new Entity("like_photo");
		likeEntity.setProperty("user_id", e_userKey);
		likeEntity.setProperty("photo_id", e_photoKey);
		likeEntity.setProperty("like", false);
		ds.put(likeEntity);
				
		PhotoManager pm = PhotoManager.instance();
		pm.like(e_userKey.getId(), e_photoKey.getId());
		
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("like_photo");
		PreparedQuery pq = ds.prepare(q);

		assertEquals(1, pq.countEntities());

		Entity result = pq.asSingleEntity();
		assertEquals(e_userKey, result.getProperty("user_id"));
		assertEquals(e_photoKey, result.getProperty("photo_id"));
		assertEquals(true, (boolean)result.getProperty("like"));

	}
	
	@Test
	public void dislike(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Key e_photoKey = ds.put(new Entity("photo"));
		Key e_userKey = ds.put(new Entity("user"));
				
		PhotoManager pm = PhotoManager.instance();
		pm.dislike(e_userKey.getId(), e_photoKey.getId());
		
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("like_photo");
		PreparedQuery pq = ds.prepare(q);

		assertEquals(1, pq.countEntities());

		Entity result = pq.asSingleEntity();
		assertEquals(e_userKey, result.getProperty("user_id"));
		assertEquals(e_photoKey, result.getProperty("photo_id"));
		assertEquals(false, (boolean)result.getProperty("like"));
	}
	
	@Test
	public void dislike_overrideLike(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Key e_photoKey = ds.put(new Entity("photo"));
		Key e_userKey = ds.put(new Entity("user"));
		
		Entity likeEntity = new Entity("like_photo");
		likeEntity.setProperty("user_id", e_userKey);
		likeEntity.setProperty("photo_id", e_photoKey);
		likeEntity.setProperty("like", true);
		ds.put(likeEntity);
		
		PhotoManager pm = PhotoManager.instance();
		pm.dislike(e_userKey.getId(), e_photoKey.getId());
		
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("like_photo");
		PreparedQuery pq = ds.prepare(q);

		assertEquals(1, pq.countEntities());

		Entity result = pq.asSingleEntity();
		assertEquals(e_userKey, result.getProperty("user_id"));
		assertEquals(e_photoKey, result.getProperty("photo_id"));
		assertEquals(false, (boolean)result.getProperty("like"));
	}
	
	@Test
	public void like_towLike(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Key e_photoKey = ds.put(new Entity("photo"));
		Key e_userKey = ds.put(new Entity("user"));
		
		Entity likeEntity1 = new Entity("like_photo");
		likeEntity1.setProperty("user_id", e_userKey);
		likeEntity1.setProperty("photo_id", e_photoKey);
		likeEntity1.setProperty("like", true);
		ds.put(likeEntity1);
		
		Entity likeEntity2 = new Entity("like_photo");
		likeEntity2.setProperty("user_id", e_userKey);
		likeEntity2.setProperty("photo_id", e_photoKey);
		likeEntity2.setProperty("like", true);
		ds.put(likeEntity2);
		
		PhotoManager pm = PhotoManager.instance();
		try {
			pm.like(e_userKey.getId(), e_photoKey.getId());
			fail();
		} catch (IllegalStateException e) {
		}

	}
	
	@Test
	public void like_invalUser(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Key e_photoKey = ds.put(new Entity("photo"));
				
		PhotoManager pm = PhotoManager.instance();
		try {
			pm.like(123l, e_photoKey.getId());
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void like_invalPhoto(){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Key e_userKey = ds.put(new Entity("user"));
				
		PhotoManager pm = PhotoManager.instance();
		try {
			pm.like(e_userKey.getId(), 123l);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
}
