package net.arenx.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable(table="like_location")
public class LikeLocationJDO extends BaseJDO{


	@Persistent(column="like")
	private Boolean like;

	@Persistent(column="user_id",defaultFetchGroup="true")
	@Unowned
	private UserJDO user;
	
	@Persistent(column="location_id",defaultFetchGroup="true")
	@Unowned
	private LocationJDO location;
	

	public final UserJDO getUser() {
		return user;
	}

	public final void setUser(UserJDO user) {
		this.user = user;
	}

	public final LocationJDO getLocation() {
		return location;
	}

	public final void setLocation(LocationJDO location) {
		this.location = location;
	}

	public final Boolean isLike() {
		return like;
	}

	public final void setLike(Boolean like) {
		this.like = like;
	}
	
}
