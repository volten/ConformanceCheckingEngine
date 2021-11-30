package dtu.dcr.ProcessModel;

import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import lombok.Getter;

import dtu.dcr.ProcessModel.Relation.*;

public class Model {

	@Getter
	private HashSet<Activity> activities = new HashSet<Activity>();
	@Getter
	private HashMap<Activity, ArrayList<ImmutablePair<TYPES, Activity>>> relations = 
		new HashMap<Activity, ArrayList<ImmutablePair<TYPES, Activity>>>();
	@Getter
	private ArrayList<Relation> violations = new ArrayList<Relation>();
	


	public Activity addActivity(String activityName) {
		Activity a = new Activity(activityName);
		addActivity(a);
		return a;
	}

	public Activity addActivity(Activity act) {
		activities.add(act);
		return act;
	}

	public Activity getActivityFromName(String activityName) {
		for (Activity a : activities) {
			if (a.getName().equals(activityName)) {
				return a;
			}
		}
		return null;
	}

	public Activity getActivityFromId(String activityId) {
		for (Activity a : activities) {
			if (a.getId().equals(activityId)) {
				return a;
			}
		}
		return null;
	}

	public void addRelation(String sourceId, String relation, String targetId) {
		Activity srcActivity = getActivityFromId(sourceId);
		Activity trtActivity = getActivityFromId(targetId);
		TYPES relationType = TYPES.valueOf(relation);
		addRelation(srcActivity, relationType, trtActivity);

	}
	public void addRelation(Activity source, ImmutablePair<TYPES,Activity> relationTuple) {
		addRelation(source, relationTuple.getLeft(), relationTuple.getRight());
	}

	public void addRelation(Activity source, Relation.TYPES arr, Activity target) {
		ImmutablePair<TYPES, Activity> tuple = new ImmutablePair<>(arr, target);
		ArrayList<ImmutablePair<TYPES, Activity>> list = new ArrayList<>();
		list.add(tuple);
		relations.put(source, list);
	}

	public ArrayList<ImmutablePair<TYPES, Activity>> getAllRelations(Activity source) {
			return relations.get(source);
	}

	public void addViolatingRelation(Relation relation) {
		violations.add(relation);
	}
	public void addViolatingRelation(Activity source, TYPES constraint, Activity target) {
		violations.add(new Relation(source, constraint, target));
	}
	public void addViolatingRelation(Activity source, ImmutablePair<TYPES, Activity> constraintTuple) {
		violations.add(new Relation(source, constraintTuple));
	}
	
}
