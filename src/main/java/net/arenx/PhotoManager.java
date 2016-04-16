package net.arenx;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.arenx.api.bean.PhotoBean;
import net.arenx.jdo.LikePhotoJDO;
import net.arenx.jdo.LocationJDO;
import net.arenx.jdo.PhotoJDO;
import net.arenx.jdo.UserJDO;

public class PhotoManager {
	
	private static final Logger log = Logger.getLogger(PhotoManager.class.getName());

	private static final PhotoManager instance = new PhotoManager();

	public static PhotoManager instance() {
		return instance;
	}

	private PhotoManager() {

	}

	public PhotoBean add(Long userId, Long locationId, String description){
		checkNotNull(userId);
		checkNotNull(locationId);
		if (null != description) {
			checkArgument(1000 >= description.length());
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		PhotoJDO photo = new PhotoJDO();

		photo.setDescription(description);
		
		LocationJDO lj;
		try {
			lj = pm.getObjectById(LocationJDO.class, locationId);
		} catch (JDOObjectNotFoundException e) {
			throw new IllegalArgumentException("no such location", e);
		}
		photo.setLocation(lj);
		
		UserJDO user;
		try {
			user = pm.getObjectById(UserJDO.class, userId);
		} catch (JDOObjectNotFoundException e) {
			throw new IllegalArgumentException("no such user", e);
		}
		photo.setUser(user);
	
		try {
			pm.makePersistent(photo);
			return photo.toBean();
		} finally {
			pm.close();
		}
	}

	public PhotoBean get(Long id){
		checkNotNull(id);

		PersistenceManager pm = PMF.get().getPersistenceManager();
	
		try {
			PhotoJDO photo = pm.getObjectById(PhotoJDO.class,id);
			return photo.toBean();
		} catch (JDOObjectNotFoundException e) {
			return null;
		}finally {
			pm.close();
		}
	}

	public List<PhotoBean> getallFromLocation(Long id){
		checkNotNull(id);

		PersistenceManager pm = PMF.get().getPersistenceManager();
	
		try {
			Query q = pm.newQuery(PhotoJDO.class, "location == locationParam");
			q.declareParameters(Long.class.getName()+" locationParam");
			List<PhotoJDO> photos = (List<PhotoJDO>) q.execute(id);
			List<PhotoBean> photoBeans = new ArrayList<PhotoBean>();
			for (PhotoJDO p:photos){
				photoBeans.add(p.toBean());
			}
			return photoBeans;
		}finally {
			pm.close();
		}
	}

	public void like(Long userId, Long photoId){
		like(userId, photoId, true);
	}
	
	public void dislike(Long userId, Long photoId){
		like(userId, photoId, false);
	}
	
	private void like(Long userId, Long photoId, boolean isLike){
		checkNotNull(userId);
		checkNotNull(photoId);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(1);

		try{
			Query q = pm.newQuery(LikePhotoJDO.class, "user == userParam && photo == photoParam");
			q.declareParameters(Long.class.getName()+" userParam, "+Long.class.getName()+" photoParam");
			List<LikePhotoJDO> likes = (List<LikePhotoJDO>) q.execute(userId, photoId);
			LikePhotoJDO like = null;
			if (1 < likes.size()) {
				throw new IllegalStateException("more than one entities are found with [user_id: "+userId+", photo_id: "+photoId+"]");
			} else if (1 == likes.size()) {
				like = likes.get(0);
			} else {
				like = new LikePhotoJDO();

				UserJDO user;
				try {
					user = pm.getObjectById(UserJDO.class, userId);
				} catch (JDOObjectNotFoundException e) {
					throw new IllegalArgumentException("no such user", e);
				}
				like.setUser(user);
				
				PhotoJDO pj;
				try {
					pj = pm.getObjectById(PhotoJDO.class, photoId);
				} catch (JDOObjectNotFoundException e) {
					throw new IllegalArgumentException("no such photo", e);
				}
				like.setPhoto(pj);
			}
									
			like.setLike(isLike);
			pm.makePersistent(like);
			
			return;
		}finally{
			pm.close();
		}
	}
	
}
