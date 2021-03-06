package de.thm.arsnova.persistence.couchdb;

import com.fasterxml.jackson.databind.JsonNode;
import de.thm.arsnova.model.Comment;
import de.thm.arsnova.model.migration.v2.ClientAuthentication;
import de.thm.arsnova.model.migration.v2.CommentReadingCount;
import de.thm.arsnova.persistence.CommentRepository;
import de.thm.arsnova.persistence.LogEntryRepository;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.UpdateConflictException;
import org.ektorp.ViewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CouchDbCommentRepository extends CouchDbCrudRepository<Comment> implements CommentRepository {
	private static final Logger logger = LoggerFactory.getLogger(CouchDbCommentRepository.class);

	@Autowired
	private LogEntryRepository dbLogger;

	public CouchDbCommentRepository(final CouchDbConnector db, final boolean createIfNotExists) {
		super(Comment.class, db, "by_id", createIfNotExists);
	}

	@Override
	public int countByRoomId(final String sessionId) {
		final ViewResult result = db.queryView(createQuery("by_roomid")
				.key(sessionId)
				.reduce(true)
				.group(true));
		if (result.isEmpty()) {
			return 0;
		}

		return result.getRows().get(0).getValueAsInt();
	}

	@Override
	public CommentReadingCount countReadingByRoomId(final String roomId) {
		final ViewResult result = db.queryView(createQuery("by_roomid_read")
				.startKey(ComplexKey.of(roomId))
				.endKey(ComplexKey.of(roomId, ComplexKey.emptyObject()))
				.reduce(true)
				.group(true));
		return calculateReadingCount(result);
	}

	@Override
	public CommentReadingCount countReadingByRoomIdAndUser(final String roomId, final ClientAuthentication user) {
		final ViewResult result = db.queryView(createQuery("by_roomid_creatorid_read")
				.startKey(ComplexKey.of(roomId, user.getId()))
				.endKey(ComplexKey.of(roomId, user.getId(), ComplexKey.emptyObject()))
				.reduce(true)
				.group(true));
		return calculateReadingCount(result);
	}

	private CommentReadingCount calculateReadingCount(final ViewResult viewResult) {
		if (viewResult.isEmpty()) {
			return new CommentReadingCount();
		}
		// A complete result looks like this. Note that the second row is optional, and that the first one may be
		// 'unread' or 'read', i.e., results may be switched around or only one result may be present.
		// count = {"rows":[
		// {"key":["cecebabb21b096e592d81f9c1322b877","Guestc9350cf4a3","read"],"value":1},
		// {"key":["cecebabb21b096e592d81f9c1322b877","Guestc9350cf4a3","unread"],"value":1}
		// ]}
		int read = 0, unread = 0;
		boolean isRead = false;
		final ViewResult.Row fst = viewResult.getRows().get(0);
		final ViewResult.Row snd = viewResult.getRows().size() > 1 ? viewResult.getRows().get(1) : null;

		final JsonNode fstkey = fst.getKeyAsNode();
		if (fstkey.size() == 2) {
			isRead = fstkey.get(1).asBoolean();
		} else if (fstkey.size() == 3) {
			isRead = fstkey.get(2).asBoolean();
		}
		if (isRead) {
			read = fst.getValueAsInt();
		} else {
			unread = fst.getValueAsInt();
		}

		if (snd != null) {
			final JsonNode sndkey = snd.getKeyAsNode();
			if (sndkey.size() == 2) {
				isRead = sndkey.get(1).asBoolean();
			} else {
				isRead = sndkey.get(2).asBoolean();
			}
			if (isRead) {
				read = snd.getValueAsInt();
			} else {
				unread = snd.getValueAsInt();
			}
		}
		return new CommentReadingCount(read, unread);
	}

	@Override
	public List<Comment> findByRoomId(final String roomId, final int start, final int limit) {
		final int qSkip = start > 0 ? start : -1;
		final int qLimit = limit > 0 ? limit : -1;

		final List<Comment> comments = db.queryView(createQuery("by_roomid_creationtimestamp")
						.skip(qSkip)
						.limit(qLimit)
						.descending(true)
						.startKey(ComplexKey.of(roomId, ComplexKey.emptyObject()))
						.endKey(ComplexKey.of(roomId))
						.includeDocs(true),
				Comment.class);
//		for (Comment comment : comments) {
//			comment.setRoomId(session.getKeyword());
//		}

		return comments;
	}

	@Override
	public List<Comment> findByRoomIdAndUser(final String roomId, final ClientAuthentication user, final int start, final int limit) {
		final int qSkip = start > 0 ? start : -1;
		final int qLimit = limit > 0 ? limit : -1;

		final List<Comment> comments = db.queryView(createQuery("by_roomid_creatorid_creationtimestamp")
						.skip(qSkip)
						.limit(qLimit)
						.descending(true)
						.startKey(ComplexKey.of(roomId, user.getUsername(), ComplexKey.emptyObject()))
						.endKey(ComplexKey.of(roomId, user.getUsername()))
						.includeDocs(true),
				Comment.class);
//		for (Comment comment : comments) {
//			comment.setRoomId(session.getKeyword());
//		}

		return comments;
	}

	@Override
	public int deleteByRoomId(final String roomId) {
		final ViewResult result = db.queryView(createQuery("by_roomid").key(roomId));

		return delete(result);
	}

	@Override
	public int deleteByRoomIdAndUser(final String roomId, final ClientAuthentication user) {
		final ViewResult result = db.queryView(createQuery("by_roomid_creatorid_read")
				.startKey(ComplexKey.of(roomId, user.getUsername()))
				.endKey(ComplexKey.of(roomId, user.getUsername(), ComplexKey.emptyObject())));

		return delete(result);
	}

	private int delete(final ViewResult comments) {
		if (comments.isEmpty()) {
			return 0;
		}
		/* TODO: use bulk delete */
		for (final ViewResult.Row row : comments.getRows()) {
			try {
				db.delete(row.getId(), row.getValueAsNode().get("rev").asText());
			} catch (final UpdateConflictException e) {
				logger.error("Could not delete comments.", e);
			}
		}

		/* This does account for failed deletions */
		dbLogger.log("delete", "type", "comment", "commentCount", comments.getSize());

		return comments.getSize();
	}
}
