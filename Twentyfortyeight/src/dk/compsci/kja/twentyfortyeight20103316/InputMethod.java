package dk.compsci.kja.twentyfortyeight20103316;

public enum InputMethod {
	SWIPE, VOLUME, ROTATE;
	
	public static InputMethod valueOf(int n) {
		return values()[n];
	}
}
