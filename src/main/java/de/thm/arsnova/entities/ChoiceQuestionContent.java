package de.thm.arsnova.entities;

import com.fasterxml.jackson.annotation.JsonView;
import de.thm.arsnova.entities.serialization.View;

import java.util.ArrayList;
import java.util.List;

public class ChoiceQuestionContent extends Content {
	public static class AnswerOption {
		private String label;
		private int points;

		@JsonView({View.Persistence.class, View.Public.class})
		public String getLabel() {
			return label;
		}

		@JsonView({View.Persistence.class, View.Public.class})
		public void setLabel(String label) {
			this.label = label;
		}

		@JsonView({View.Persistence.class, View.Public.class})
		public int getPoints() {
			return points;
		}

		@JsonView({View.Persistence.class, View.Public.class})
		public void setPoints(int points) {
			this.points = points;
		}
	}

	private List<AnswerOption> options = new ArrayList<>();
	private List<Integer> correctOptionIndexes = new ArrayList<>();
	private boolean multiple;

	@JsonView({View.Persistence.class, View.Public.class})
	public List<AnswerOption> getOptions() {
		return options;
	}

	@JsonView({View.Persistence.class, View.Public.class})
	public void setOptions(final List<AnswerOption> options) {
		this.options = options;
	}

	/* TODO: A new JsonView is needed here. */
	@JsonView(View.Persistence.class)
	public List<Integer> getCorrectOptionIndexes() {
		return correctOptionIndexes;
	}

	@JsonView({View.Persistence.class, View.Public.class})
	public void setCorrectOptionIndexes(final List<Integer> correctOptionIndexes) {
		this.correctOptionIndexes = correctOptionIndexes;
	}

	@JsonView({View.Persistence.class, View.Public.class})
	public boolean isMultiple() {
		return multiple;
	}

	@JsonView({View.Persistence.class, View.Public.class})
	public void setMultiple(final boolean multiple) {
		this.multiple = multiple;
	}
}
