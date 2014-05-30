package dk.compsci.kja.twentyfortyeight;

import java.util.ArrayList;

public abstract class AbstractEngineController implements EngineController {

	private ArrayList<Engine> _horizontalAxes;
	private ArrayList<Engine> _verticalAxes;

	public AbstractEngineController() {
		_horizontalAxes = new ArrayList<Engine>();
		_verticalAxes = new ArrayList<Engine>();
	}
	
	@Override
	public void attachHorizontal(Engine e) {
		_horizontalAxes.add(e);
	}
	
	@Override
	public void attachVertical(Engine e) {
		_verticalAxes.add(e);
	}
	
	@Override
	public void removeHorizontal(Engine e) {
		_horizontalAxes.remove(e);		
	}
	
	@Override
	public void removeVertical(Engine e) {
		_verticalAxes.remove(e);
	}
	
	protected void left() {
		for (Engine e : _horizontalAxes) {
			e.left();
		}
	}
	
	protected void right() {
		for (Engine e : _horizontalAxes) {
			e.right();
		}
	}
	
	protected void down() {
		for(Engine e : _verticalAxes) {
			e.down();
		}
	}

	protected void up() {
		for(Engine e : _verticalAxes) {
			e.up();
		}
	}
}
