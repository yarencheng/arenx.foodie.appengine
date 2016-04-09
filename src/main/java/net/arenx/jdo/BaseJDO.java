package net.arenx.jdo;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import net.arenx.api.v1.BaseBean;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class BaseJDO {

	@PrimaryKey
	@Persistent(column="id", valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent(column="create_date")
	private Date createDate = new Date();
	
	
	public final Long getId() {
		return id;
	}
	
	public final Date getCreateDate() {
		return createDate;
	}

	public <T extends BaseBean> T toBean(Class<T> clazz){
		checkNotNull(clazz);
		
		T t = null;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Faile to create bean", e);
		}
		
		t.setCreateDate(createDate);
		t.setId(id);
		
		return t;
	}
}
