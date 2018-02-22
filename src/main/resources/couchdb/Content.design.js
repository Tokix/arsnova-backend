var designDoc = {
	"_id": "_design/Content",
	"language": "javascript",
	"views": {
		"by_roomid": {
			"map": function (doc) {
				if (doc.type === "Content") {
					emit(doc.roomId, {_rev: doc._rev});
				}
			},
			"reduce": "_count"
		},
		"by_roomid_group_locked": {
			"map": function (doc) {
				if (doc.type === "Content") {
					emit([doc.roomId, doc.group, doc.locked, doc.subject, doc.body.substr(0, 16)], {_rev: doc._rev});
				}
			},
			"reduce": "_count"
		}
	}
};
