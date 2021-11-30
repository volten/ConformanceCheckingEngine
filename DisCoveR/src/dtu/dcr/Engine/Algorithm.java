package dtu.dcr.Engine;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;
import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;

import dtu.dcr.ProcessModel.*;

public class Algorithm {


    @Getter
	private LinkedHashSet<Activity> executedSet = new LinkedHashSet<Activity>();
    @Getter
	private LinkedHashSet<Activity> includedSet = new LinkedHashSet<Activity>();
    @Getter
	private LinkedHashSet<Activity> pendingSet = new LinkedHashSet<Activity>();
    @Getter
	private Set<Activity> excludedSet;
    @Getter 
    private ArrayList<Trace> traces;
    @Getter
    private ArrayList<Relation> violatingRelations = new ArrayList<Relation>();
    @Getter ArrayList<ImmutablePair<Trace, String>> violatingTraces = new ArrayList<ImmutablePair<Trace, String>>();
 

    public void checkRelationConstraints(Trace trace, Model relations) {
        ArrayList<ImmutablePair<Relation.TYPES, Activity>> constraints = relations.getAllRelations(trace);

        for (ImmutablePair<Relation.TYPES, Activity> constraintTuple : constraints) {
            switch (constraintTuple.getKey().toString()) {
                case "INCLUDE":
                    /* set relation.target as included */
                    /* remove relation.target from excluded set and add to included set */
                    constraintTuple.getValue().setMarkings(true, 0);
                    excludedSet.remove(constraintTuple.getValue());
                    includedSet.add(constraintTuple.getValue());
                case "RESPONSE":
                    /* if relation.target is included */
                    /* set relation.target as pending and add to pending set */
                    if (includedSet.contains(constraintTuple.getValue())) {
                        constraintTuple.getValue().setMarkings(true, 1);
                        pendingSet.add(constraintTuple.getValue()); 
                }
                case "CONDITION":
                /* SPECIAL CASE: trace must be considered as target! */
                /* if relation.source of incoming condition is not executed, flag relation as violation */
                    if (!isIncomingConditionSatisfied(trace, constraintTuple.getValue(), relations)) {
                        addViolatingRelation(trace, constraintTuple);
                    }
                case "EXCLUDE":
                    /* set relaton.target as excluded */
                    /* add relation.target to excludedSet and remove from includedSet */
                    constraintTuple.getValue().setMarkings(false, 0);
                    excludedSet.add(constraintTuple.getValue());
                    /* set relation.target as excluded */

                case "MILESTONE": 
                    /* this needs to be clear. documentation is unclear 
                    https://documentation.dcr.design/documentation/introduction-to-business-rules/#Milestone */
                    
                default:
                    break;
            }
        }

    }

    public boolean isIncomingConditionSatisfied(Activity source, Activity target, Model relations) {
        ArrayList<ImmutablePair<Relation.TYPES, Activity>> conditionRelations = relations.getAllRelations(target);
        for (ImmutablePair<Relation.TYPES, Activity> conditionRelation : conditionRelations) {
            if (conditionRelation.getLeft().toString().equals("CONDITION") & 
                conditionRelation.getRight().equals(source) & 
                executedSet.contains(conditionRelation.getRight())) {
                    return true;
            } 
        }
        return false;
    }

    public void addViolatingRelation(Relation relation) {
		violatingRelations.add(relation);
	}
	public void addViolatingRelation(Activity source, Relation.TYPES constraint, Activity target) {
		violatingRelations.add(new Relation(source, constraint, target));
	}
	public void addViolatingRelation(Activity source, ImmutablePair<Relation.TYPES, Activity> constraintTuple) {
		violatingRelations.add(new Relation(source, constraintTuple));
	}

    public void TraceReplay(ArrayList<Trace> traces, Model processModel) {
        for (Trace trace : traces){
            if (processModel.getActivityFromId(trace.getId()) != null) {
                if (!includedSet.contains(trace)) {
                    ImmutablePair<Trace, String> violatingTrace = new ImmutablePair<Trace, String>(trace, "Excluded trace executed");
                    violatingTraces.add(violatingTrace);
                } 
            checkRelationConstraints(trace, processModel);

            }
        }
        
        /* Load ProcessModel object */

        /* Load Traces object */

            /* For each trace: */
            
                /* See if trace exists in ProcessModel */
                
                /* If exists: */
                
                /* See if trace is included */
                /* If Excluded: flag violation*/
                
                /* Initialize constraints and activate impacted activities */
                
                /* Change markings of activiites */

                /* Update model lists with executed, pending, excluded */


                /* Set trace to executed */
                trace.setMarkings(true, 2);
                executedSet.add(trace);
            
            /* Flag activities which are pending as violations */

            /* See requirements of conformity and return conformity or list of violations */
    }

    
    
}
