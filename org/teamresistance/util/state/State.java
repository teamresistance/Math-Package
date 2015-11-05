package org.teamresistance.util.state;
/**
 * Represents an abstract state to be used by a StateMachine. 
 * An instance of State represents the state of the machine on a continuous interval of time, 
 * during which, the machine did not change state.
 * 
 * 
 * @author Mathis
 *
 */
public abstract class State {
	
	private StateMachine stateMachine;
	
	void setStateMachine(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}
	
	/**
	 * Sets the state of this State's state machine to an instance of the specified type. 
	 * @param stateType the type of state
	 * @return true if and only if the state of the state machine was changed.
	 */
	final protected boolean gotoState(Class<? extends State> stateType) {
		return stateMachine.setState(stateType);
	}
	
	/**
	 * Sets the state of this State's state machine to an instance with the specified name.
	 * @param stateName the name of the state
	 * @return true if and only if the state of the state machine was changed.
	 */
	final protected boolean gotoState(String stateName) {
		return stateMachine.setState(stateName);
	}
	
	/**
	 * Called by the state machine whenever this state is entered.
	 * Object initialization code may be present, but it is recommended to be in the constructor.
	 * State reset code should be present.
	 * @param e State Transition event object
	 */
	public abstract void onEntry(StateTransition e);
	/**
	 * called by the state machine whenever this state is updated.
	 */
	public abstract void update();
	/**
	 * Called by the state machine whenever this state is exited.
	 * @param e State Transition event object
	 */
	public abstract void onExit(StateTransition e);
}
