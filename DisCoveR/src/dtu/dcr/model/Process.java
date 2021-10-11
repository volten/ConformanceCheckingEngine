package dtu.dcr.model;

import java.util.HashSet;

import dtu.dcr.model.Relation.TYPES;
import lombok.Getter;

public class Process {

	@Getter
	private HashSet<Activity> activities = new HashSet<Activity>();
	@Getter
	private HashSet<Relation> relations = new HashSet<Relation>();

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

	public void addRelation(Activity source, Relation.TYPES arr, Activity target) {
		relations.add(new Relation(source, arr, target));
	}
}
