package arenx.foodie.appengine.bean;

import static org.junit.Assert.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import net.arenx.api.bean.BaseBean;

public class BaseBeanTest {

	@Test
	public void BaseBean(){
		BaseBean b1 = new BaseBean();
		BaseBean b2 = new BaseBean(b1);
		assertTrue(EqualsBuilder.reflectionEquals(b1, b2, true));
	}
}
