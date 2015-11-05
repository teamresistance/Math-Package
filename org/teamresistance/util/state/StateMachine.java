package org.teamresistance.util.state;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Finite State Machine (FSM). The FSM can be in one single state at a time. 
 * Users should instantiate the machine and provide the initial state information,
 * and then add state instances to the machine.
 * 
 * Currently, State.onEntry is NOT invoked in state initialization: The design assumes eternal state.
 * 
 * @author Mathis
 */
public class StateMachine {
	/**
	 * This FSM's current state. Can never be null. 
	 */
	private State currentState;
	
	/*
	 * There is one instance of each implementation of State per lifetime of this State Machine. 
	 * Each is mutated.
	 */
	
	private Map<Class<? extends State>, State> stateTypeMap;
	private Map<String, State> stateNameMap;
	
	/**
	 * Constructs a new State Machine, and sets its state to the specified State object.
	 * @param initialState the state
	 * @throws NullPointerException if <code>state</code> is a null pointer
	 */
	public StateMachine(State initialState) {
		this();
		addState(initialState);
		currentState = initialState;
	}
	
	/**
	 * Constructs a new State Machine sets its state to the specified State object, 
	 * and associates the state with a name.
	 * @param initialState the state
	 * @param initialStateName the name
	 * @throws NullPointerException if <code>state</code> is <code>null</code>
	 */
	public StateMachine(State initialState, String initialStateName) {
		this();
		addState(initialState, initialStateName);
		currentState = initialState;
	}
	
	private StateMachine() {
		stateTypeMap = new HashMap<>();
		stateNameMap = new HashMap<>();
	}
	
	/**
	 * Updates this State Machine's current state.
	 */
	public void update() {
		currentState.update();
	}

	/**
	 * Registers a new instance of an implementation of state.
	 * @param state the instance
	 * @throws NullPointerException if <code>state</code> is <code>null</code>.
	 */
	public void addState(State state) {
		if (state == null) {
			throw new NullPointerException ();
		} 
		state.setStateMachine(this);
		stateTypeMap.put(state.getClass(), state);
	}
	
	/**
	 * Registers a new instance of an implementation of state, and associates it with a name.
	 * If the name is a null pointer, then it is ignored.
	 * @param state the instance
	 * @param stateName the name
	 * @throws NullPointerException if <code>state</code> is <code>null</code>.
	 */
	public void addState(State state, String stateName) {
		addState(state);
		if (stateName != null) {
			stateNameMap.put(stateName, state);
		}
	}
	
	/**
	 * Exits the current state, and enters the state of the specified type. 
	 * If the state is the current state, Then this method returns without altering this StateMachine.
	 * @param stateType the type
	 * @return <code>true</code> if and only if the state of this machine was changed.
	 */
	public boolean setState(Class<? extends State> stateType) {
		if (stateType == null) {
			return false;
		}
		return transition(stateNameMap.get(stateType));
	}
	
	/**
	 * Exits the current state, and enters the state of the specified name.
	 * If the state is the current state, Then this method returns without altering this StateMachine.
	 * @param stateName the name
	 * @return <code>true</code> if and only if the state of this machine was changed
	 */
	public boolean setState(String stateName) {
		if (stateName == null) {
			return false;
		}
		return transition(stateNameMap.get(stateName));
	}
	
	public State getState() {
		return currentState;
	}
	/**
	 * Attempts to transition into the specified state.
	 * @param newState the state
	 * @return <code>true</code> if and only if the transition was successful.
	 */
	private boolean transition(State newState) {
		if (newState == null || newState == currentState) {
			return false;
		}
		StateTransition transition = new StateTransition(currentState, newState);
		
		currentState.onExit(transition);
		newState.onEntry(transition);
		
		currentState = newState;
		return true;
	}
}
