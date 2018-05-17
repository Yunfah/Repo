package client;

/**
 * 
 * This class contains an interface, which makes it possible to switch between 
 * panels in the applications
 * 
 */
public interface ContinueListener {
	
	/**
	 * Method which makes the panel progress one step in singleplayer
	 */
	public void nextPanel();
	
	/**
	 * Method which makes the panel progress to the in game panel
	 */
	public void skipToGame();
	
	/**
	 * Method which makes the panel go back to the previous panel in singleplayer
	 */
	public void goBack();
	
	/**
	 * Method which makes the panel go back to the previous panel in multiplayer
	 */
	public void goBackMP();
	
	/**
	 * Method which makes the panel progress one step in multiplayer
	 */
	public void nextPanelMP();
}
