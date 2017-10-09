/*
 * This file is part of ARSnova Backend.
 * Copyright (C) 2012-2018 The ARSnova Team and Contributors
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
package de.thm.arsnova.services;

import de.thm.arsnova.entities.UserAuthentication;
import de.thm.arsnova.entities.UserProfile;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * The functionality the user service should provide.
 */
public interface UserService {
	UserAuthentication getCurrentUser();

	boolean isBannedFromLogin(String addr);

	void increaseFailedLoginCount(String addr);

	UserAuthentication getUser2SocketId(UUID socketId);

	void putUser2SocketId(UUID socketId, UserAuthentication user);

	void removeUser2SocketId(UUID socketId);

	Set<Map.Entry<UUID, UserAuthentication>> socketId2User();

	boolean isUserInSession(UserAuthentication user, String keyword);

	Set<UserAuthentication> getUsersBySessionKey(String keyword);

	String getSessionByUsername(String username);

	void addUserToSessionBySocketId(UUID socketId, String keyword);

	void removeUserFromSessionBySocketId(UUID socketId);

	void removeUserFromMaps(UserAuthentication user);

	int loggedInUsers();

	UserProfile getByUsername(String username);

	UserProfile create(String username, String password);

	UserProfile update(UserProfile userProfile);

	UserProfile deleteByUsername(String username);

	void initiatePasswordReset(String username);

	boolean resetPassword(UserProfile userProfile, String key, String password);
}
