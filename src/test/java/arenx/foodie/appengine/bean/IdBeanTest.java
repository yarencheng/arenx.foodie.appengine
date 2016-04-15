package arenx.foodie.appengine.bean;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import net.arenx.api.bean.IdBean;

public class IdBeanTest {

	@Test
	public void IdBean() {
		IdBean b1 = new IdBean();
		IdBean b2 = new IdBean(b1);
		assertTrue(EqualsBuilder.reflectionEquals(b1, b2, true));
	}

	@Test
	public void set_getCreateDate() {
		IdBean b = new IdBean();
		Date ed = new Date();
		b.setCreateDate(ed);
		Date ad = b.getCreateDate();
		assertEquals(ed, ad);
		assertTrue(ed != ad);
	}
	
	@Test
	public void set_getCreateDate_null() {
		IdBean b = new IdBean();
		Date ad = b.getCreateDate();
		assertNull(ad);
	}
}
