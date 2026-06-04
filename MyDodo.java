import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead ()){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
            System.out.println ("Moved " + nrStepsTaken + " steps");
        }
    }

    
    
    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdge(){
        while(!borderAhead()){
            move();
        }
    }            
    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
        } else {
            return true;
        }
    }
    public void climboverFence(){
    if (fenceAhead()) {   
    turnLeft();
    move();
    turnRight();
    move();
    move();
    turnRight();
    move();
    turnLeft();
    }
 }
  public void turn180(){
      turnRight();
      turnRight();
  }
  public boolean grainAhead(){
      step();
      boolean grainFound = onGrain();
      stepOneCellBackwards();
      return grainFound;
    }
  public void goToEgg(){
      while (!onEgg() && !borderAhead())
      move();
    }
    public void goBackToStartOfRowAndFaceBack(){
      turn180();
      walkToWorldEdge();
      turn180();
 }
   public void walkToWorldEdgeClimbingOverFences() {
    while (!borderAhead()) {
        if (onNest()) {
            layEgg();
        }

        if (fenceAhead()) {
            climboverFence();
        } else {
            move();
        }
    }
}

 public void pickUpGrainsAndPrintCoordinates() { // dodo loop naar vooren en onder weg pakt ie graan op en prinie cordinaten
      while (!borderAhead()) {
          if (onGrain()) {
              System.out.println(getX() + ", " + getY());
              pickUpGrain();
          }
          move();
      }
      if (onGrain()) {
          System.out.println(getX() + ", " + getY());
          pickUpGrain();
      }
}
 public void stepOneCellBackwards(){
    turn180();
    step();
    turn180();
    }
    public void layEggInEmptyNests() {
      while (!borderAhead()) {
          if (onNest() && !onEgg()) {
              layEgg();
          }
          move();
      }
      if (onNest() && !onEgg()) {
          layEgg();
      }
  }
  public void walkAroundFencedArea() {
      while (!onEgg()){
      turnRight();
      if (fenceAhead()){
          turnLeft();
        }
        move();
  }
}      
 public void walkAroundOtherFencedArea() {  
      while (!onEgg()) {
          turnRight();
          if (canMove()) {
              move();
          } else {
              turnLeft();
              while (!canMove()) {
                  turnLeft();
              }
              move();
          }
      }
  }
  public void face (int direction){
    while (getDirection() !=direction){
        turnRight();
    }
}
    public void faceWest()  
    { face(WEST);  }
    public void faceNorth() 
    { face(NORTH); }
    public void faceSouth() 
    { face(SOUTH); }
  public boolean locationReached(int x, int y) {
      return getX() == x && getY() == y;
  }  //check of de dodo al op de coordinaten juiste coordinaten staat.
  public void goToLocation(int coordX, int coordY) {  // vind coordinaten bijv als is te klein is 4.2 stap oost 5.2 of als zuid te klein is 5.2 stap zuid 5.3
      while (!locationReached(coordX, coordY)) {
          if (getX() < coordX) {
              setDirection(EAST);
              move();
          } else if (getX() > coordX) {
              setDirection(WEST);
              move();
          } else if (getY() < coordY) {
              setDirection(SOUTH);
              move();
          } else if (getY() > coordY) {
              setDirection(NORTH);
              move();
          }
      }
  } 
  public boolean validCoordinates(int x, int y) { // check of de gegeven codinaten passen bij de wereld 
      int width = getWorld().getWidth();
      int height = getWorld().getHeight();
      if (x >= 0 && x < width && y >= 0 && y < height) {
          return true;
      } else {
          showError("Invalid coordinates");
          return false;
      }
  }
  public int countEggsInRow() {   // telt eiren in een rij 
      int count = 0;
      if (onEgg()) { count++; }
      while (!borderAhead()) {
          move();
          if (onEgg()) { count++; }
      }
      goBackToStartOfRowAndFaceBack();
      showCompliment("Aantal eieren in rij: " + count);
      return count;
  }
  public void simpleMaze () {  
      while (!onNest()) {
          turnRight();
          if (canMove()) {
              move();
          } else {
              turnLeft();
              while (!canMove()) {
                  turnLeft();
              }
              move();
          }
      }
      showCompliment("Nest gevonden!");
}
public void eggTrailToNest() {
      while (!onNest()) {
          if (eggAhead() || nestAhead()) {
              move();
          } else {
              turnRight();
              if (!eggAhead()) {
                  turn180();
                }
          }
      }
  }
  public void layTrailOfEggs(int n){
    for (int i = 0; i < n; i++){
        move();
        layEgg();
    }
    }
   public int countEggsInWorld() {
    int total = 0;
    int height = getWorld().getHeight();
    int width = getWorld().getWidth();
    
    for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
            goToLocation(col, row);
            if (onEgg()) {
                total++;
            }
        }
    }
    
    System.out.println("Totaal aantal eieren: " + total);
    return total;
}
}
   





    

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    

