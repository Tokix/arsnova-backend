/*
 * This file is part of ARSnova Backend.
 * Copyright (C) 2012-2018 The ARSnova Team
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
package de.thm.arsnova.security.pac4j;

import de.thm.arsnova.security.User;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Authentication token implementation for OAuth.
 *
 * @author Daniel Gerhardt
 */
public class OAuthToken extends AbstractAuthenticationToken {
	private User principal;

	public OAuthToken(User principal, CommonProfile profile,
			Collection<? extends GrantedAuthority> grantedAuthorities) {
		super(grantedAuthorities);
		this.principal = principal;
		this.setDetails(profile);
		setAuthenticated(!grantedAuthorities.isEmpty());
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return super.isAuthenticated();
	}
}
