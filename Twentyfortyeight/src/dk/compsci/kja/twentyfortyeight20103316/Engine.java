package dk.compsci.kja.twentyfortyeight20103316;

import java.util.ArrayList;

public abstract class Engine {

	public abstract void left();
	public abstract void up();
	public abstract void right();
	public abstract void down();
	
	public abstract int getScore();
	public abstract int[] getTiles();
	public abstract boolean isDone();
	public abstract void reset();
	
	private final ArrayList<EngineListener> _listeners;
	
	public Engine() {
		_listeners = new ArrayList<EngineListener>();
	}
	
	protected void notifyListeners() {
		for (EngineListener el : _listeners) {
			el.onChange(this);
		}
	}
	
	public void addChangeListener(EngineListener el) {
		_listeners.add(el);
	}
	
	public void removeChangeListener(EngineListener el) {
		_listeners.remove(el);
	}
}
