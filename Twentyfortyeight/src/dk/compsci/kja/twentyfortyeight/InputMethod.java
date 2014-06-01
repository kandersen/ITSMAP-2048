package dk.compsci.kja.twentyfortyeight;

public enum InputMethod {
	SWIPE, VOLUME, ROTATE;
	
	public static InputMethod valueOf(int n) {
		return values()[n];
	}
}
