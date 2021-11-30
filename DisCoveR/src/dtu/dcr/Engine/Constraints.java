package dtu.dcr.Engine;

import java.util.ArrayList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import lombok.Getter;
import lombok.Setter;

import dtu.dcr.ProcessModel.*;

public class Constraints {

    @Getter @Setter 
    ArrayList<Trace> Traces;
 

    public void setConstraint(Trace trace, Model relations) {
        ArrayList<ImmutablePair<Relation.TYPES, Activity>> constraints = relations.getAllRelations(trace);

        for (ImmutablePair<Relation.TYPES, Activity> constraintTuple : constraints) {
            switch (constraintTuple.getKey().toString()) {
                case "INCLUDE":
                    constraintTuple.getValue().setMarkings(true, 0);
                    /* set relation.target as included */
                
                case "RESPONSE":
                    if (constraintTuple.getValue().getMarkings()[0]) {
                        constraintTuple.getValue().setMarkings(true, 1); 
                }
                /* relation.source is executed and relation.target is included */
                    /* set relation.target as pending */
                case "CONDITION":
                    if (!isIncomingConditionSatisfied(trace, constraintTuple.getValue(), relations)) {
                        relations.addViolatingRelation(trace, constraintTuple);
                    }
                    /* if relation.source is not executed, flag action as violation */
                
                case "EXCLUDE":
                    constraintTuple.getValue().setMarkings(false, 0);
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
                conditionRelation.getRight().getMarkings()[2]) {
                    return true;
            } 
        }
        return false;
    }


    



}
