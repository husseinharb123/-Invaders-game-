

/**
 * An entity which represents one of our space invader aliens.
 * 
 * @author islam and hussien 
 */
public class AlienEntity extends Entity {
	private static String sprite;
	/** The speed at which the alient moves horizontally */
	private double moveSpeed = -100;
	/** The game in which the entity exists */
	private Game game;
	int value;
	int temp;
	
	/**
	 * Create a new alien entity
	 * 
	 * @param game The game in which this entity is being created
	 * @param ref The sprite which should be displayed for this alien
	 * @param x The intial x location of this alien
	 * @param y The intial y location of this alient
	 */
	public AlienEntity(Game game,String ref,int x,int y,int value) {
	 super(ref,x,y);
		
		this.game = game;
		this.value =value;
		dy = moveSpeed;
		
	}

	/**
	 * Request that this alien moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {

		dy = moveSpeed;
		dx = moveSpeed;
		
		
		x+=value;
		// proceed with normal move
		super.move(delta);
		// if we shot off the screen, remove ourselfs
		if (y < -100) {
			game.removeEntity(this);
		}
		
	}
	
	/**
	 * Update the game logic related to aliens
	 */
	public void doLogic() {
//		// swap over horizontal movement and move down the
//		// screen a bit
//		dx = -dx;
//		x += 10;
//		
//		// if we've reached the bottom of the screen then the player
//		// dies
//		if (y > 570) {
//			game.notifyDeath();
//		}
	}
	
	/**
	 * Notification that this alien has collided with another entity
	 * 
	 * @param other The other entity
	 */
	public void collidedWith(Entity other) {
		// collisions with aliens are handled elsewhere
	}
}