package net.arenx;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;

import net.arenx.api.bean.UserBean;
import net.arenx.jdo.UserJDO;

public class UserManager {
	private static final UserManager instance = new UserManager();

	public static UserManager instance() {
		return instance;
	}

	private UserManager() {

	}

	public UserBean get(User user){
		checkNotNull(user);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			Query q = pm.newQuery(UserJDO.class, "appengineId == id");
			q.declareParameters("String id");
			List<UserJDO> users = (List<UserJDO>) q.execute(user.getUserId());
			if (1 < users.size()) {
				throw new IllegalStateException("more than one entities are found with appengine ID: "+user.getUserId());
			} else if (1 == users.size()) {
				return users.get(0).toBean();
			}
		}finally{
			pm.close();
		}
		
		// not found create new one
		return add(user);
	}
		
	public UserBean get(Long id){
		checkNotNull(id);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			return pm.getObjectById(UserJDO.class, id).toBean();
		}catch(JDOObjectNotFoundException e){
			return null;
		}finally{
			pm.close();
		}
	}
	
	public boolean isExist(Long id){
		checkNotNull(id);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(1);
		
		try{
			pm.getObjectById(UserJDO.class, id);
			return true;
		}catch(JDOObjectNotFoundException e){
			return false;
		}finally{
			pm.close();
		}
	}
		
	private UserBean add(User user){
		checkNotNull(user);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			UserJDO jdo = new UserJDO();
			jdo.setAppengineId(user.getUserId());
			jdo.setEmail(user.getEmail());
			pm.makePersistent(jdo);
			return jdo.toBean();
		}finally{
			pm.close();
		}
	}
}
