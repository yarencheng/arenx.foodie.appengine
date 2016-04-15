package arenx.foodie.appengine.bean;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import net.arenx.api.bean.UserBean;

public class UserBeanTest {

	@Test
	public void UserBean() {
		UserBean b1 = new UserBean();
		UserBean b2 = new UserBean(b1);
		assertTrue(EqualsBuilder.reflectionEquals(b1, b2, true));
	}

}
