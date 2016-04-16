package arenx.foodie.appengine.helloworld;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import net.arenx.api.bean.IdBean;

public class HelloWorldTest {

	@Test
	public void ttt() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException{
		
		IdBean id1 = new IdBean();
		id1.setCreateDate(new Date());
		id1.setId(123l);
		
		IdBean id2 = (IdBean) BeanUtils.cloneBean(id1);
		System.out.println(id1);
		System.out.println(id2);
				
		//Assert.assertTrue(false);
	}
}
