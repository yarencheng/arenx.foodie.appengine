package net.arenx.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable(table="like_photo")
public class LikePhotoJDO extends BaseJDO{


	@Persistent(column="like")
	private Boolean like;

	@Persistent(column="user_id",defaultFetchGroup="true")
	@Unowned
	private UserJDO user;
	
	@Persistent(column="photo_id",defaultFetchGroup="true")
	@Unowned
	private PhotoJDO photo;
	

	public final UserJDO getUser() {
		return user;
	}

	public final void setUser(UserJDO user) {
		this.user = user;
	}

	public final PhotoJDO getPhoto() {
		return photo;
	}

	public final void setPhoto(PhotoJDO photo) {
		this.photo = photo;
	}

	public final Boolean isLike() {
		return like;
	}

	public final void setLike(Boolean like) {
		this.like = like;
	}
	
}
