package org.teamresistance.util.state;

public class StateTransition {
	private State initialState;
	private State newState;
	
	StateTransition(State initialState, State newState) {
		this.initialState = initialState;
		this.newState = newState;
	}

	public State getInitialState() {
		return initialState;
	}
	
	
	public State getNewState() {
		return newState;
	}
}
