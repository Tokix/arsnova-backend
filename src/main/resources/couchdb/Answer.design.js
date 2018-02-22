var designDoc = {
	"_id": "_design/Answer",
	"language": "javascript",
	"views": {
		"by_contentid": {
			"map": function (doc) {
				if (doc.type === "Answer") {
					emit(doc.contentId, {_rev: doc._rev});
				}
			}
		},
		"by_contentid_round_body_subject": {
			"map": function (doc) {
				if (doc.type === "Answer") {
					emit([doc.contentId, doc.round, doc.abstention, doc.body, doc.subject, doc.successfulFreeTextAnswer], {_rev: doc._rev});
				}
			},
			"reduce": "_count"
		},
		"by_contentid_creationtimestamp": {
			"map": function (doc) {
				if (doc.type === "Answer") {
					emit([doc.contentId, doc.creationTimestamp], {_rev: doc._rev});
				}
			}
		},
		"by_contentid_user_round": {
			"map": function (doc) {
				if (doc.type === "Answer") {
					emit([doc.contentId, doc.user, doc.round], {_rev: doc._rev});
				}
			}
		},
		"by_roomid": {
			"map": function (doc) {
				if (doc.type === "Answer") {
					emit(doc.roomId, {_rev: doc._rev});
				}
			},
			"reduce": "_count"
		},
		"by_roomid_variant": {
			"map": function (doc) {
				if (doc.type === "Answer") {
					emit([doc.roomId, doc.questionVariant], {_rev: doc._rev});
				}
			},
			"reduce": "_count"
		},
		"by_creatorid_roomid": {
			"map": function (doc) {
				if (doc.type === "Answer") {
					emit([doc.creatorId, doc.roomId], {_rev: doc._rev});
				}
			}
		},
		"contentid_by_creatorid_roomid_variant": {
			"map": function (doc) {
				if (doc.type === "Answer") {
					emit([doc.user, doc.roomId, doc.questionVariant], doc.contentId);
				}
			}
		},
		"contentid_round_by_creatorid_roomid_variant": {
			"map": function (doc) {
				if (doc.type === "Answer") {
					emit([doc.creatorId, doc.roomId, doc.questionVariant], [doc.contentId, doc.round]);
				}
			}
		}
	}
};
