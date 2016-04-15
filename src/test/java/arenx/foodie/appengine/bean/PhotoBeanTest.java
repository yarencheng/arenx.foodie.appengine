package arenx.foodie.appengine.bean;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import net.arenx.api.bean.LocationBean;
import net.arenx.api.bean.PhotoBean;
import net.arenx.api.bean.UserBean;

public class PhotoBeanTest {

	@Test
	public void PhotoBean() {
		PhotoBean b1 = new PhotoBean();
		PhotoBean b2 = new PhotoBean(b1);
		assertTrue(EqualsBuilder.reflectionEquals(b1, b2, true));
	}

	@Test
	public void set_getLocatione() {
		PhotoBean b = new PhotoBean();
		LocationBean e = new LocationBean();
		b.setLocation(e);
		LocationBean a = b.getLocation();
		assertTrue(EqualsBuilder.reflectionEquals(e, a, true));
		assertTrue(e != a);
	}
	
	@Test
	public void set_getLocatione_null() {
		PhotoBean b = new PhotoBean();
		LocationBean a = b.getLocation();
		assertNull(a);
	}
	
	@Test
	public void set_getUser() {
		PhotoBean b = new PhotoBean();
		UserBean e = new UserBean();
		b.setUser(e);
		UserBean a = b.getUser();
		assertTrue(EqualsBuilder.reflectionEquals(e, a, true));
		assertTrue(e != a);
	}
	
	@Test
	public void set_getUser_null() {
		PhotoBean b = new PhotoBean();
		UserBean a = b.getUser();
		assertNull(a);
	}
}
