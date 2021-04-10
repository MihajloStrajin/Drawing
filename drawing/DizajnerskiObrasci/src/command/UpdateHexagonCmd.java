package command;


import java.awt.Color;

import adapter.HexagonAdapter;
import hexagon.Hexagon;

public class UpdateHexagonCmd implements Command {
	
	private HexagonAdapter oldState = new HexagonAdapter(-1,-1,-1,Color.black,Color.black);
	private HexagonAdapter newState = new HexagonAdapter(-1,-1,-1,Color.black,Color.black);
	private HexagonAdapter original = new HexagonAdapter(-1,-1,-1,Color.black,Color.black);
	
	public UpdateHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	

	@Override
	public void execute() {
		original = oldState.clone();

		oldState.getHexagon().setX(newState.getHexagon().getX());
		oldState.getHexagon().setY(newState.getHexagon().getY());
		oldState.getHexagon().setR(newState.getHexagon().getR());
		oldState.getHexagon().setBorderColor(newState.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(newState.getHexagon().getAreaColor());

	}

	@Override
	public void unexecute() {
		oldState.getHexagon().setX(original.getHexagon().getX());
		oldState.getHexagon().setY(original.getHexagon().getY());
		oldState.getHexagon().setR(original.getHexagon().getR());
		oldState.getHexagon().setBorderColor(original.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(original.getHexagon().getAreaColor());

	}

}
