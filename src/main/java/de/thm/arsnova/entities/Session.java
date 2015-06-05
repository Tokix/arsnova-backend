/*
 * This file is part of ARSnova Backend.
 * Copyright (C) 2012-2015 The ARSnova Team
 *
 * ARSnova Backend is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ARSnova Backend is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.thm.arsnova.entities;

import java.io.Serializable;
import java.util.List;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents an ARSnova session.
 */
@ApiModel( value = "session" , description = "the Session API")
public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;
	private String name;
	private String shortName;
	private String keyword;
	private String creator;
	private boolean active;
	private long lastOwnerActivity;
	private String courseType;
	private String courseId;
	private List<String> _conflicts;
	private long creationTime;
	private LearningProgressOptions learningProgressOptions = new LearningProgressOptions();
	private SessionFeature features = new SessionFeature();

	private String ppAuthorName;
	private String ppAuthorMail;
	private String ppUniversity;
	private String ppLogo;
	private String ppSubject;
	private String ppLicense;
	private String ppDescription;
	private String ppFaculty;
	private String ppLevel;
	private String sessionType;

	private String _id;
	private String _rev;

	/**
	 * Returns a copy of the given session without any information that identifies a person.
	 * @param original The session to create a anonymized copy of
	 * @return
	 */
	public static Session anonymizedCopy(final Session original) {
		final Session copy = new Session();
		copy.type = original.type;
		copy.name = original.name;
		copy.shortName = original.shortName;
		copy.keyword = original.keyword;
		copy.creator = ""; // anonymous
		copy.active = original.active;
		copy.lastOwnerActivity = original.lastOwnerActivity;
		copy.courseType = original.courseType;
		copy.courseId = original.courseId;
		copy.creationTime = original.creationTime;
		copy.learningProgressOptions = new LearningProgressOptions(original.learningProgressOptions);
		copy.features = new SessionFeature(original.features);
		// public pool
		copy.ppAuthorName = original.ppAuthorName;
		copy.ppAuthorMail = original.ppAuthorMail;
		copy.ppUniversity = original.ppUniversity;
		copy.ppLogo = original.ppLogo;
		copy.ppSubject = original.ppSubject;
		copy.ppLicense = original.ppLicense;
		copy.ppDescription = original.ppDescription;
		copy.ppFaculty = original.ppFaculty;
		copy.ppLevel = original.ppLevel;
		copy.sessionType = original.sessionType;

		copy._id = original._id;
		copy._rev = original._rev;
		return copy;
	}

	@ApiModelProperty(position = 1, required = true, value = "used to display the Type")
	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@ApiModelProperty(position = 2, required = true, value = "used to display name")
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ApiModelProperty(position = 3, required = true, value = "used to display short name")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	@ApiModelProperty(position = 4, required = true, value = "used to display keyword")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@ApiModelProperty(position = 5, required = true, value = "used to display creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(final String creator) {
		this.creator = creator;
	}

	@ApiModelProperty(position = 6, required = true, value = "is active")
	public boolean isActive() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	@ApiModelProperty(position = 7, required = true, value = "used to display the last owner activity")
	public long getLastOwnerActivity() {
		return lastOwnerActivity;
	}

	public void setLastOwnerActivity(final long lastOwnerActivity) {
		this.lastOwnerActivity = lastOwnerActivity;
	}

	public void set_id(final String id) {
		_id = id;
	}

	@ApiModelProperty(position = 8, required = true, value = "used to display id")
	public String get_id() {
		return _id;
	}

	public void set_rev(final String rev) {
		_rev = rev;
	}

	@ApiModelProperty(position = 9, required = true, value = "used to display rev")
	public String get_rev() {
		return _rev;
	}

	public void set_conflicts(final List<String> conflicts) {
		_conflicts = conflicts;
	}

	@ApiModelProperty(position = 10, required = true, value = "used to display conflicts")
	public List<String> get_conflicts() {
		return _conflicts;
	}

	public boolean isCreator(final User user) {
		return user.getUsername().equals(creator);
	}

	@ApiModelProperty(position = 11, required = true, value = "used to display course type")
	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(final String courseType) {
		this.courseType = courseType;
	}

	@ApiModelProperty(position = 12, required = true, value = "used to display coures id")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(final String courseId) {
		this.courseId = courseId;
	}

	@JsonIgnore
	public boolean isCourseSession() {
		return getCourseId() != null && !getCourseId().isEmpty();
	}

	@ApiModelProperty(position = 13, required = true, value = "used to display the creation time")
	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	
	@ApiModelProperty(position = 14, required = true, value = "used to display learnin progress options")
	public LearningProgressOptions getLearningProgressOptions() {
		return learningProgressOptions;
	}

	public void setLearningProgressOptions(LearningProgressOptions learningProgressOptions) {
		this.learningProgressOptions = learningProgressOptions;
	}

	@ApiModelProperty(position = 15, required = true, value = "used to display features")
	public SessionFeature getFeatures() {
		return features;
	}

	public void setFeatures(SessionFeature features) {
		this.features = features;
	}

	@ApiModelProperty(position = 16, required = true, value = "used to display author name")
	public String getPpAuthorName() {
		return ppAuthorName;
	}

	public void setPpAuthorName(final String ppAuthorName) {
		this.ppAuthorName = ppAuthorName;
	}

	@ApiModelProperty(position = 17, required = true, value = "used to display author mail")
	public String getPpAuthorMail() {
		return ppAuthorMail;
	}

	public void setPpAuthorMail(final String ppAuthorMail) {
		this.ppAuthorMail = ppAuthorMail;
	}
	
	@ApiModelProperty(position = 18, required = true, value = "used to display university")
	public String getPpUniversity() {
		return ppUniversity;
	}

	public void setPpUniversity(final String ppUniversity) {
		this.ppUniversity = ppUniversity;
	}

	@ApiModelProperty(position = 19, required = true, value = "used to display logo")
	public String getPpLogo() {
		return ppLogo;
	}

	public void setPpLogo(final String ppLogo) {
		this.ppLogo = ppLogo;
	}

	@ApiModelProperty(position = 20, required = true, value = "used to display subject")
	public String getPpSubject() {
		return ppSubject;
	}

	public void setPpSubject(final String ppSubject) {
		this.ppSubject = ppSubject;
	}

	@ApiModelProperty(position = 21, required = true, value = "used to display license")
	public String getPpLicense() {
		return ppLicense;
	}

	public void setPpLicense(final String ppLicense) {
		this.ppLicense = ppLicense;
	}

	@ApiModelProperty(position = 22, required = true, value = "used to display description")
	public String getPpDescription() {
		return ppDescription;
	}

	public void setPpDescription(final String ppDescription) {
		this.ppDescription = ppDescription;
	}

	@ApiModelProperty(position = 23, required = true, value = "used to display faculty")
	public String getPpFaculty() {
		return ppFaculty;
	}

	public void setPpFaculty(final String ppFaculty) {
		this.ppFaculty = ppFaculty;
	}

	@ApiModelProperty(position = 24, required = true, value = "used to display level")
	public String getPpLevel() {
		return ppLevel;
	}

	public void setPpLevel(final String ppLevel) {
		this.ppLevel = ppLevel;
	}

	@ApiModelProperty(position = 25, required = true, value = "used to display the session type")
	public String getSessionType() {
		return sessionType;
	}

	public void setSessionType(final String sessionType) {
		this.sessionType = sessionType;
	}

	@Override
	public String toString() {
		return "Session [keyword=" + keyword + ", type=" + type + ", creator=" + creator + "]";
	}

	@Override
	public int hashCode() {
		// See http://stackoverflow.com/a/113600
		final int theAnswer = 42;
		final int theOthers = 37;

		return theOthers * theAnswer + this.keyword.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		}
		Session other = (Session) obj;
		return this.keyword.equals(other.keyword);
	}

}
