package dtu.dcr.ProcessModel;

import org.apache.commons.lang3.tuple.ImmutablePair;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Relation {

	public enum TYPES {
		CONDITION, RESPONSE, INCLUDE, EXCLUDE, MILESTONE
	}

	@Getter
	private String sourceId;
	@Getter
	private String targetId;
	@Getter
	private TYPES constraint;

	public Relation() {

	}

	public Relation(Activity source, TYPES constraint, Activity target) {
		this.sourceId = source.getId();
		this.targetId = target.getId();
		this.constraint = constraint;
	}

	public Relation(Activity source, ImmutablePair<TYPES,Activity> relationTuple) {
        this.sourceId = source.getId(); 
        this.targetId = relationTuple.getRight().getId();
        this.constraint = relationTuple.getKey();
    }
}