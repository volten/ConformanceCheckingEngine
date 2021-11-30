package dtu.dcr.Engine;

import dtu.dcr.ProcessModel.*;

public class Algorithm {


    public boolean isEventIncluded(Trace trace, Model activities) {
        return activities.getActivityFromId(trace.getId()).getMarkings()[0];
    }

    public boolean isEventPending(Trace trace, Model activities) {
        return activities.getActivityFromId(trace.getId()).getMarkings()[1];
    }

    public boolean isEventExecuted(Trace trace, Model activities) {
        return activities.getActivityFromId(trace.getId()).getMarkings()[2];
    }

    public void TraceReplay(Trace trace, Model processModel) {

        

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
            
            /* Flag activities which are pending as violations */

            /* Oversee requirements of conformity and */

            /* return conformity or list of violations */
    }

    
    
}
