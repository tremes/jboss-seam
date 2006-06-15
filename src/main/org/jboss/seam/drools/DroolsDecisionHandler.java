package org.jboss.seam.drools;

import java.util.List;

import org.drools.WorkingMemory;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

/**
 * A jBPM DecisionHandler that delegates to a Drools WorkingMemory
 * held in a Seam context variable. The decision outcome is returned
 * by setting the outcome attribute of the global named "decision".
 * 
 * @author Gavin King
 *
 */
public class DroolsDecisionHandler extends DroolsHandler implements DecisionHandler
{

   public List<String> assertObjects;
   public String workingMemoryName;

   /**
    * The FireRulesActionHandler gets variables from the Instance, and asserts
    * them into the Rules Engine and invokes the rules.
    */
   public String decide(ExecutionContext executionContext) throws Exception
   {
      WorkingMemory workingMemory = getWorkingMemory(workingMemoryName, assertObjects, executionContext);
      workingMemory.setGlobal( "decision", new Decision() );
      workingMemory.fireAllRules();
      return ( (Decision) workingMemory.getGlobal("decision") ).getOutcome();
   }
   
}