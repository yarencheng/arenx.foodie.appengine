package arenx.foodie.appengine.bean;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import net.arenx.api.bean.PositionBean;

public class PositionBeanTest {

	@Test
	public void PositionBean() {
		PositionBean b1 = new PositionBean();
		PositionBean b2 = new PositionBean(b1);
		assertTrue(EqualsBuilder.reflectionEquals(b1, b2, true));
	}

}
