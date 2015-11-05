package org.teamresistance.util.state;



/**
 * Represents a Finite State Machine (FSM). The FSM can be in one single state at a time. 
 * Users can change the state of the machine.
 * @author Mathis
 */
public class StateMachine {
	/**
	 * This FSM's current state. Can never be null. 
	 */
	private State currentState;
	
	/**
	 * Constructs a new State Machine, and sets its state to the specified State object.
	 * @param initialState the state
	 * @throws NullPointerException if the state is a null pointer
	 */
	public StateMachine(State initialState) {
		if (initialState == null) {
			throw new NullPointerException();
		}
		currentState = initialState;
	}
	
	/**
	 * Updates this State Machine's current state.
	 */
	public void update() {
		currentState.update();
	}

	/**
	 * Exits the current state, and enters the specified new state. 
	 * If the new state is of the same type as the current state, 
	 * then this method returns without altering this StateMachine.
	 * @param newState the state
	 * @throws NullPointerException if state is null.
	 */
	public void setState(State newState) {
		if (newState == null) {
			throw new NullPointerException ();
		}
		if (newState.getClass().equals(currentState.getClass())) {
			return;
		}
		
		transition(newState);
	}
	
	public State getState() {
		return currentState;
	}
	
	private void transition(State newState) {
		StateTransition transition = new StateTransition(currentState, newState);
		
		currentState.onExit(transition);
		newState.onEntry(transition);
		
		currentState = newState;
	}
}
