import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
private int stepsTaken = Mauritius.MAXSTEPS;
private int myNrOfEggs = 0;
    public void eggValueSwap() {
        int temporaryValueEgg;
        BlueEgg valueBlueEgg = new BlueEgg();
        GoldenEgg valueGoldenEgg = new GoldenEgg();

        temporaryValueEgg = valueBlueEgg.getValue();
        valueBlueEgg.setValue(valueGoldenEgg.getValue());
        valueGoldenEgg.setValue(temporaryValueEgg);
    }
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
    // Laat Dodo één stap vooruit zetten als dat kan, anders foutmelding "I'm stuck!"
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
    // Checkt of Dodo kan bewegen: false als er een hek of de rand van de wereld voor haar is
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
    // Pakt het ei in de huidige cel op (laat het "uitkomen") en verhoogt de teller; foutmelding als er geen ei ligt
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
    // Geeft het aantal eieren terug dat Dodo tot nu toe heeft laten uitkomen
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
    // Laat Dodo "distance" stappen vooruit lopen, met een teller die elke stap print
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
    // Laat Dodo vooruit lopen totdat ze de rand van de wereld bereikt
    public void walkToWorldEdge(){
        while(!borderAhead()){
            move();
        }
    }     
    // Checkt of Dodo op de huidige cel een ei mag leggen (alleen als er nog geen ei ligt)
    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
        } else {
            return true;
        }
    }
    // Laat Dodo over een hek heen klimmen door er zijwaarts omheen te stappen, als er een hek voor haar staat
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
 // Draait Dodo 180 graden om, door twee keer naar rechts te draaien
  public void turn180(){
      turnRight();
      turnRight();
  }
  // Checkt of er graan ligt in de cel direct voor Dodo, zonder dat ze daar blijft staan
  public boolean grainAhead(){
      step();
      boolean grainFound = onGrain();
      stepOneCellBackwards();
      return grainFound;
    }
    // Laat Dodo vooruit lopen tot ze op een ei staat of de rand van de wereld bereikt
  public void goToEgg(){
      while (!onEgg() && !borderAhead())
      move();
    }
    // Draait Dodo om, loopt terug naar het begin van de rij en draait weer terug naar de oorspronkelijke richting
    public void goBackToStartOfRowAndFaceBack(){
      turn180();
      walkToWorldEdge();
      turn180();
 }
 // Loopt door de wereld tot de rand, legt een ei op elk nest en klimt over elk hek heen
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
// Loopt door de rij, pakt al het graan op en print de coordinaten waar dat gebeurt
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
// Laat Dodo één cel naar achteren stappen zonder haar kijkrichting te veranderen
 public void stepOneCellBackwards(){
    turn180();
    step();
    turn180();
    }
    // Loopt door de rij en legt een ei op elk nest waar nog geen ei ligt
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
  // Loopt langs de rand van een omsloten gebied (rechterhand-regel) totdat een ei gevonden wordt
  public void walkAroundFencedArea() {
      while (!onEgg()){
      turnRight();
      if (fenceAhead()){
          turnLeft();
        }
        move();
  }
}      
// Alternatieve manier om langs een omsloten gebied te lopen totdat een ei gevonden wordt, werkt ook bij complexere hekvormen
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
  // Draait Dodo net zo lang naar rechts totdat ze in de opgegeven richting kijkt
  public void face (int direction){
    while (getDirection() !=direction){
        turnRight();
    }
}
// Draait Dodo zodat ze naar het westen, noorden, zuiden of oosten kijkt
    public void faceWest()  
    { face(WEST);  }
    public void faceNorth() 
    { face(NORTH); }
    public void faceSouth() 
    { face(SOUTH); }
    public void faceEast(){
        face(EAST);
    }
    // Checkt of Dodo precies op de opgegeven coordinaten staat
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
      System.out.println("Aantal eieren in rij: " + count);
      return count;
  }
  // Navigeert door een doolhof van hekken (rechterhand-regel) totdat een nest gevonden wordt
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
// Volgt een spoor van eieren totdat een nest bereikt wordt
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
  // Draait Dodo naar de opgegeven richting, mits die geldig is (0 t/m 3)
   public void faceDirection(int direction) {
        if (direction >= 0 && direction <= 3)
        {
        while (getDirection() !=direction){
            turnRight();
        }
    }
}
// Laat Dodo n stappen vooruit lopen en legt op elke nieuwe cel een ei
  public void layTrailOfEggs(int n){
    for (int i = 0; i < n; i++){
        move();
        layEgg();
    }
    }
    // Telt het totale aantal eieren in de hele wereld door elke cel te bezoeken
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
// Zoekt de rij met het meeste aantal eieren en print het resultaat (of een melding bij gelijkspel)
 public void searchRowWithMostEggs() {
    int height = getWorld().getHeight();
    int row = 0;
        int bestRow = 0;
    int bestCount = 0;
    boolean equalEggCount = false;
 
    while (row < height) {
        goToLocation(0, row);
        faceDirection(1);
        int eggCount = countEggsInRow();
        row++;
        if (eggCount > bestCount) {
            bestCount = eggCount;
            bestRow = row;
            equalEggCount = false;
        } else if (eggCount == bestCount) {
            equalEggCount = true;
        }
    }
    goToLocation(0, 0);
    faceDirection(1);
        if (equalEggCount) {
        System.out.println("There are multiple rows with " + bestCount + " eggs.");
    } else {
        System.out.println("Row with most eggs: " + bestRow + " (" + bestCount + ")");
    }
}
// Legt een driehoekig monument van eieren: rij 0 krijgt 1 ei, rij 1 krijgt 2, enzovoort
public void makeMonumentOfEggs() {
    int startX = getX();
    int startY = getY();
    int aantalRijen = getWorld().getHeight() - startY;

    for (int rij = 0; rij < aantalRijen; rij++) {
        for (int kolom = 0; kolom <= rij; kolom++) {
            goToLocation(startX + kolom, startY + rij);
            if (canLayEgg()) {
                layEgg();
            }
        }
    }

    goToLocation(startX, startY);
}
 /**
 * Vult de wereld zover mogelijk met een verdubbelend patroon van eieren.
 * Rij 1 krijgt 1 ei, rij 2 krijgt 2 eieren, rij 3 krijgt 4 eieren, etc.
 * 
 * <p> Initial: Dodo staat ergens in de wereld.
 * <p> Final:   De wereld is gevuld met een verdubbelend patroon van eieren
 *              vanaf de startpositie van Dodo.
 */
public void makeSolidMonumnet() {
    int startX = getX();
    int startY = getY();
    int aantalRijen = getWorld().getHeight() - startY;
    int aantalEieren = 1;

    for (int rij = 0; rij < aantalRijen; rij++) {
        for (int kolom = 0; kolom < aantalEieren; kolom++) {
            goToLocation(startX + kolom, startY + rij);
            if (canLayEgg()) {
                layEgg();
            }
        }
        aantalEieren = aantalEieren * 2; // verdubbel voor de volgende rij
    }

    goToLocation(startX, startY);
}
// Legt een piramidevorm van eieren, gecentreerd rond de startkolom van Dodo
  public void makePiramideOfEggs() {
    int startX = getX();
    int startY = getY();
    int aantalRijen = getWorld().getHeight() - startY;

    for (int rij = 0; rij < aantalRijen; rij++) {
        for (int kolom = -rij; kolom <= rij; kolom++) {
            goToLocation(startX + kolom, startY + rij);
            if (canLayEgg()) {
                layEgg();
            }
        }
    }

    goToLocation(startX, startY);
 }
 // Berekent en print het gemiddeld aantal eieren per rij in de hele wereld
 public double averageEggsPerRow() {
    int aantalRijen = getWorld().getHeight();
    int totaalEieren = 0;

    for (int rij = 0; rij < aantalRijen; rij++) {
        goToLocation(0, rij);
        face(EAST);
        totaalEieren += countEggsInRow();
    }

    double gemiddelde = (double) totaalEieren / aantalRijen;
    System.out.println("Gemiddeld aantal eieren per rij: " + gemiddelde);

    goToLocation(0, 0);
    face(EAST);
    return gemiddelde;
}
// Zoekt een rij en kolom met een oneven aantal eieren en legt op hun kruispunt een extra ei
  public void parityTester() {
        int worldHeight = getWorld().getHeight();
        int worldWidth = getWorld().getWidth();
        int savedx = -1;
        int savedy = -1;
        for(int i = 0; i < worldHeight; i++) {
            goToLocation(0, i);
            faceEast();
            if(countEggsInRow() % 2 != 0) {
                savedy = i;
                System.out.println(savedy);
            }
        }
        
        for(int i = 0; i < worldWidth; i++) {
            goToLocation(i, 0);
            faceSouth();
            if(countEggsInRow() % 2 != 0) {
                savedx = i;
                System.out.println(savedx);
            }
        }
        
        if (savedx != -1 && savedy != -1) {
            goToLocation(savedx, savedy);
            if(canLayEgg()) {
                layEgg();
            }
        }
    }
    // Bepaalt de huidige kijkrichting van Dodo als getal (0=noord, 1=oost, 2=zuid, 3=west)
    public int direction() {
        int previousX = getX();
        int previousY = getY();
        int direction = -1;
        
        if(borderAhead()) {
            stepOneCellBackwards();
            previousX = getX();
            previousY = getY();
            move();
            if(previousX < getX()) {
                direction = 1;
            } else if(previousX > getX()) {
                direction = 3;
            } else if(previousY < getY()) {
                direction = 2;
            } else if(previousY > getY()){
                direction = 0;
            }
        } else {
            move();
            if(previousX < getX()) {
                direction = 1;
            } else if(previousX > getX()) {
                direction = 3;
            } else if(previousY < getY()) {
                direction = 2;
            } else if(previousY > getY()){
                direction = 0;
            }
            stepOneCellBackwards();
        }
        return direction;
    }
    // Zelfde als parityTester(), maar gebruikt faceDirection() in plaats van faceEast()/faceSouth()
    public void parityTesterWithoutDirection() {
        int worldHeight = getWorld().getHeight();
        int worldWidth = getWorld().getWidth();
        int savedx = -1;
        int savedy = -1;
        for(int i = 0; i < worldHeight; i++) {
            goToLocation(0,i);
            faceDirection(1);
            if(countEggsInRow() % 2 != 0) {
                savedy = i;
                System.out.println(savedy);
            }
        }
        
        for(int i = 0; i < worldWidth; i++) {
            goToLocation(i,0);
            faceDirection(2);
            if(countEggsInRow() % 2 != 0) {
                savedx = i;
                System.out.println(savedx);
            }
        }
        
        if (savedx != -1 && savedy != -1) {
            goToLocation(savedx, savedy);
            if(canLayEgg()) {
                layEgg();
            }
        }
    }
    //maak een 10 suprise eggs aan in de wereld, dus krijgt de grootte van de wereld mee
    public List <SurpriseEgg> makeListOfSurpriseEgg(){
      return SurpriseEgg.generateListOfSurpriseEggs(10, getWorld());
    }
     public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }
    //print de coordinaten van de ei op de X en Y as.
    public void printCoordinatesOfEgg(Egg egg){
        System.out.println(getX() + "X" + getY() + "Y");
    }
    // combineert de 2 functies bij elkaar
    public void makeListOfSupriseEggAndPrintCoordinatesOfEgg(){
    for(Egg egg : makeListOfSurpriseEgg()){
        printCoordinatesOfEgg(egg);
    }
    }
    // hij zoekt voor de meest waardevolle ei en kijkt tussen 10 eieren
    public void searchForMostValuedEgg(){
    List<SurpriseEgg> eggValue = makeListOfSurpriseEgg();
    int mostValuedEgg = 0;
    for(Egg egg: eggValue){
        if(mostValuedEgg < egg.getValue()){
            mostValuedEgg = egg.getValue();
    }
  }
   System.out.println(mostValuedEgg + " meest waardevolle ei ");
 } 
 // deze functie zoek voor de gemiddelde ei tussen de grootte van de aantal eieren
 public void searchForAverageEgg(){
    List<SurpriseEgg> eggValue = makeListOfSurpriseEgg();
    int total = 0;
    for(Egg egg: eggValue){
        total += egg.getValue();
  }
   double averageValueEgg = (double)total / eggValue.size();
   System.out.println(averageValueEgg + " de gemiddelde ei ");
 } 
 /**
 * Laat het object willekeurig bewegen totdat het maximale aantal stappen is bereikt.
 * Bij elke stap wordt een willekeurige richting gekozen.
 * Als bewegen niet mogelijk is of er een rand voor het object ligt,
 * draait het object 180 graden om en probeert vervolgens te bewegen.
 * Na elke stap wordt de score bijgewerkt.
 */
 public void moveRandom() {
        int myNrOfStepsTaken = 0;
        while(myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            faceDirection(randomDirection());
            if(!canMove() || borderAhead()) {
                turn180();
            }
            move();
            myNrOfStepsTaken++;
            getScore(Mauritius.MAXSTEPS - myNrOfStepsTaken,0);
        }
    } 
    /**
 * Werkt de score in de wereld bij.
 *
 * @param score1 De score voor speler/object 1.
 * @param score2 De score voor speler/object 2.
 */
    public void getScore(int score1, int score2) {
        ((Mauritius)getWorld()).updateScore(score1,score2);
    }
    //dodo pakt de ei die het dichtbijzijnde van de dodo af staat
 public void pickUpNearestEggInList() {
        List<Egg> eggs = getListOfEggsInWorld();
        int closestEgg = 10000;
        Egg eggLocation = null;
        for(Egg egg: eggs) {
            int x = getX() - egg.getX();
            int y = getY() - egg.getY();
            if(x < 0){
                x = -x;
            }
            if(y < 0){
                y = -y;
            }
            int stappen = x + y;
            if(stappen < closestEgg) {
                closestEgg = stappen;
                eggLocation = egg;
            }
        }
        if(eggLocation != null) {
            goToLocation(eggLocation.getX(), eggLocation.getY());
            myNrOfEggs += eggLocation.getValue();
        } else {
            showError("Er zijn geen eieren!");
        }
    }

    // dodo gaat naar de dichtbijzijnde eieren binnen 40 stappen
    public void dodoRace() {
        List<Egg> eggs = getListOfEggsInWorld();
        
        for(Egg egg : eggs) {
            pickUpNearestEggInList();
            if(onEgg()){
                hatchEgg();
            }
            getScore(stepsTaken, myNrOfEggs);
        }
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

    

