package org.teamresistance.util.state;
/**
 * Represents an abstract state to be used by a StateMachine. 
 * An instance of State represents the state of the machine on a continuous interval of time, 
 * during which, the machine did not change state.
 * 
 * In other words, do not reuse instances of State, 
 * especially implementations of State whose behavior depends on fields.
 * 
 * An implementation of state whose behavior is pure (fields do not change behavior) will 
 * still abide by these requirements.
 * 
 * @author Mathis
 *
 */
public interface State {
	void onEntry(StateTransition e);
	void update();
	void onExit(StateTransition e);
}
