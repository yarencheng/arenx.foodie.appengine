package net.arenx.jdo;

import java.util.logging.Logger;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.datanucleus.annotations.Unowned;

import net.arenx.api.bean.PhotoBean;

@PersistenceCapable(table="photo")
public class PhotoJDO extends BaseJDO{
	
	private static final Logger log = Logger.getLogger(PhotoJDO.class.getName());
	
	private ImagesService imagesService = ImagesServiceFactory.getImagesService();

	@Persistent(column="description")
	private String description;
	
	@Persistent(column="url")
	private String url;
	
	@Persistent(column="blob_key")
	private BlobKey blobKey;
	
	@Persistent(column="location_id",defaultFetchGroup="true")
	@Unowned
	private LocationJDO location;
	
	@Persistent(column="user_id",defaultFetchGroup="true")
	@Unowned
	private UserJDO user;

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BlobKey getBlobKey() {
		return blobKey;
	}

	public void setBlobKey(BlobKey blobKey) {
		this.blobKey = blobKey;
	}

	public LocationJDO getLocation() {
		return location;
	}

	public void setLocation(LocationJDO location) {
		this.location = location;
	}

	public UserJDO getUser() {
		return user;
	}

	public void setUser(UserJDO user) {
		this.user = user;
	}
	
	public PhotoBean toBean(){		
		PhotoBean bean = super.toBean(PhotoBean.class);
				
		bean.setDescription(description);
		if (null != blobKey)bean.setUrl(imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey)));
		if (null != location)bean.setLocation(location.toBean());
		if (null != user)bean.setUser(user.toBean());
		
		return bean;
	}

	@Override
	public String toString() {
		return "PhotoJDO [description=" + description + ", location=" + location + ", user=" + user + ", getId()=" + getId() + ", getCreateDate()=" + getCreateDate() + "]";
	}
	
	
	
}
