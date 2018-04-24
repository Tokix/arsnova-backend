package de.thm.arsnova.web;

import de.thm.arsnova.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class PathApiVersionContentNegotiationStrategy extends HeaderContentNegotiationStrategy {
	private static final Logger logger = LoggerFactory.getLogger(PathApiVersionContentNegotiationStrategy.class);

	private MediaType fallback;

	public PathApiVersionContentNegotiationStrategy(MediaType fallback) {
		this.fallback = fallback;
	}

	@Override
	public List<MediaType> resolveMediaTypes(final NativeWebRequest webRequest)
			throws HttpMediaTypeNotAcceptableException {
		final HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		final List<MediaType> mediaTypes = new ArrayList<>();
		if (servletRequest.getServletPath().startsWith("/v2/")) {
			logger.trace("Negotiating content based on path for API v2");
			mediaTypes.add(AppConfig.API_V2_MEDIA_TYPE);
			mediaTypes.add(MediaType.TEXT_PLAIN);
		} else {
			logger.trace("Content negotiation falling back to {}", fallback);
			mediaTypes.add(fallback);
		}

		return mediaTypes;
	}
}
