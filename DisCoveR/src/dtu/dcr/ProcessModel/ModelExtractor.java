package dtu.dcr.ProcessModel;

import java.util.HashSet;
import lombok.Getter;

import dtu.dcr.ProcessModel.*;

public class Extractor {

    @Getter
    private HashSet<Activity> activities = new HashSet<Activity>();
    @Getter
    private HashSet<Relation> relations = new HashSet<Relation>();


    public Activity extractActivity(Model model) {

        /* extract activity from processmodel */

        if isActivityNested() {

            /* add marking if activity is in nesting or subprocess */

            return Activity();
        }
    
        return Activity();
    }

    public Relation extractRelation(Model model) {

        /* extract the relations between activities from the processmodel*/

        return Relation(Activity source, TYPES constraint, Activity target);

    
        public boolean isActivityNested() {

            /* helper to determine if the activity is nesting or subprocess */ 
            return true;
        }
    }


    public void extractExpression(Model model) {

        /* method for extraction expressions (data) related to an activity */

    }

    public void extractInitialMarking(Model model) {

    /* extract the initial [pending, included, executed] of the model */
    }

}
