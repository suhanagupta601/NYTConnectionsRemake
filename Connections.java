import tester.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javalib.worldimages.*;
import javalib.impworld.*;
import java.awt.Color;

// represents the displayed words
class Words {
  String word;
  String category;
  String color;
  int x;
  int y;
  boolean clicked;
  boolean guessed;
  
  
  
  
  Words(String word, String category, String color) {
    this.word = word;
    this.category = category;
    this.color = color;
    
    this.x = -1;
    this.y = -1;
    this.clicked = false;
    guessed = false;
  }
  

  // draws a single word over ws
  void drawWord(WorldScene ws, int givenx, int giveny) {
    TextImage wordImage = new TextImage(this.word, 16, Color.black);
    WorldImage overlay;
    
    if (this.clicked) {
      overlay = new OverlayImage(wordImage, 
          (new RectangleImage(140, 80, OutlineMode.SOLID, Color.GRAY)));
    }
    
    else {
      overlay = new OverlayImage(wordImage, 
          (new RectangleImage(140, 80, OutlineMode.SOLID, Color.LIGHT_GRAY)));
    }
    
    ws.placeImageXY(overlay, givenx + 100, giveny + 100);
    
  }
  
  // to check the bounds of where the user clicks
  boolean clickHelper(Posn pos) {
    
    return (pos.x - 100 > this.x - 70 
        && pos.x - 100 < this.x + 70 
        && pos.y - 100 > this.y - 40 
        && pos.y - 100 < this.y + 40);
      
  }
}
  
  
// represents the game connections world
class ConnectionsWorld extends World {
  
  ArrayList<Words> set;
  Random rand;
  int tries;
  ArrayList<Words> clickedSet;
  int numberOfSolvedSets;
  
  int yellowCategory;
  int greenCategory;
  int blueCategory;
  int purpleCategory;
  
  int yellowExists;
  int greenExists;
  int blueExists;
  int purpleExists;
  
  int positionYellow;
  int positionGreen;
  int positionBlue;
  int positionPurple;
  
  ConnectionsWorld(ArrayList<Words> set) {
    this.set = set;
    this.tries = 4;
    this.clickedSet = new ArrayList<Words>(0);
    this.numberOfSolvedSets = -1;
    
    this.yellowCategory = 0;
    this.greenCategory = 0;
    this.blueCategory = 0;
    this.purpleCategory = 0;
    
    this.yellowExists = 0;
    this.greenExists = 0;
    this.blueExists = 0;
    this.purpleExists = 0;
    
    this.positionYellow = 0;
    this.positionGreen = 0;
    this.positionBlue = 0;
    this.positionPurple = 0;
    
   
    
  }

  @Override
  // displays all stages of the game when started
  public WorldScene makeScene() {
    WorldScene ws0 = new WorldScene(700, 500);
    
    
    
    if (this.tries == 0) {
      
      TextImage t = new TextImage("Out of tries, type \"r\" to try again", Color.BLACK);
      //OverlayImage image = new OverlayImage(t, new RectangleImage(1000, 1000, 
      //OutlineMode.SOLID, Color.YELLOW));
      
      ws0.placeImageXY(t, 325, 20);
      
    }
    
    
   
    
    if (set.get(0).x == -1) {
      
      for (int y = 0; y < 600; y += 150) {
        for (int x = 0; x < 352; x += 88) {
            
          this.set.get(x / 22 + y / 150).drawWord(ws0, y, x);
          this.set.get(x / 22 + y / 150).x = y;
          this.set.get(x / 22 + y / 150).y = x;
        }
      }
      
    }
    
    else {
      for (int i = 0; i < 16; i++) { 
        this.set.get(i).drawWord(ws0, this.set.get(i).x, this.set.get(i).y);
            
      }
    }
    
   
    WorldImage overlayYellow = new EmptyImage();
   
    
    if (yellowCategory > 0) {
      TextImage wordImageYellow1 = new TextImage(this.coloredLabels("yellow").get(0), 20, 
          FontStyle.BOLD, Color.black); 
      TextImage wordImageYellow2 = new TextImage(this.coloredLabels("yellow").get(1), 20, 
          Color.black); 
      
      overlayYellow = new OverlayOffsetImage(wordImageYellow2, 0, -10, 
          new OverlayOffsetImage(wordImageYellow1, 0, 20,
          (new RectangleImage(590, 90, OutlineMode.SOLID, Color.YELLOW))));
      
      yellowExists++;
       
      if (yellowExists == 1) {
        positionYellow = (90 * (numberOfSolvedSets + 1) + 5);
        
      }
       
    }
    
    WorldImage overlayGreen = new EmptyImage();
    
    
    if (greenCategory > 0) {
      TextImage wordImageGreen1 = new TextImage(this.coloredLabels("green").get(0), 20, 
          FontStyle.BOLD, Color.black); 
      TextImage wordImageGreen2 = new TextImage(this.coloredLabels("green").get(1), 20, 
          Color.black);  
      
      overlayGreen = new OverlayOffsetImage(wordImageGreen2, 0, -10, 
          new OverlayOffsetImage(wordImageGreen1, 0, 20,
          (new RectangleImage(590, 90, OutlineMode.SOLID, Color.GREEN))));
      
      greenExists ++;
      
      if (greenExists == 1) {
        positionGreen = (90 * (numberOfSolvedSets + 1) + 5);
        
      }
        
    }
    
    WorldImage overlayBlue = new EmptyImage();
   
    
    if (blueCategory > 0) {
      TextImage wordImageBlue1 = new TextImage(this.coloredLabels("blue").get(0), 20, 
          FontStyle.BOLD, Color.black); 
      TextImage wordImageBlue2 = new TextImage(this.coloredLabels("blue").get(1), 20, 
          Color.black);  
      
      overlayBlue = new OverlayOffsetImage(wordImageBlue2, 0, -10, 
          new OverlayOffsetImage(wordImageBlue1, 0, 20,
          (new RectangleImage(590, 90, OutlineMode.SOLID, Color.BLUE))));
      
      blueExists++;
      
      if (blueExists == 1) {
        positionBlue = (90 * (numberOfSolvedSets + 1) + 5);
        
      }
      
      
    }
    
    WorldImage overlayPurple = new EmptyImage();
    
    if (purpleCategory > 0) {
      TextImage wordImagePurple1 = new TextImage(this.coloredLabels("purple").get(0), 20, 
          FontStyle.BOLD, Color.black); 
      TextImage wordImagePurple2 = new TextImage(this.coloredLabels("purple").get(1), 20, 
          Color.black);  
      
      overlayPurple = new OverlayOffsetImage(wordImagePurple2, 0, -10, 
          new OverlayOffsetImage(wordImagePurple1, 0, 20,
          (new RectangleImage(590, 90, 
              OutlineMode.SOLID, Color.MAGENTA))));
      
      
      purpleExists++;
      
      if (purpleExists == 1) {
        positionPurple = (90 * (numberOfSolvedSets + 1) + 5);
      }
      
    }
    
   
    
    ws0.placeImageXY(overlayYellow, 325, positionYellow);
    ws0.placeImageXY(overlayGreen, 325, positionGreen);
    ws0.placeImageXY(overlayBlue, 325, positionBlue);
    ws0.placeImageXY(overlayPurple, 325, positionPurple);
    
    
    TextImage triesDisplay = new TextImage("TRIES: " + this.tries, 20, Color.black);
    ws0.placeImageXY(triesDisplay, 320, 480);
   
    return ws0;
   
  }
  
  // the game's reaction to where the user clicks on the screen
  public void onMouseClicked(Posn pos) {
    
    //ArrayList<Words> clickedSet = new ArrayList<Words>(0);
    
    for (int i = 0; i < this.set.size(); i++) {
      if (this.set.get(i).clickHelper(pos) && this.clickedSet.size() < 4) {
        
        this.set.get(i).clicked = true;
        //cant put same element in clickedSet twice
        
        if (!this.clickedSet.contains(this.set.get(i))) {
          this.clickedSet.add(this.set.get(i));
         
        }
      }
    }
  } 
  
  // determines what happens to the game when the user presses a specific key
  public void onKeyEvent(String key) {
    
    //System.out.println("Received Key: " + key);
    
    ArrayList<Words> restOfSet = new ArrayList<Words>(0);
    
    if (key.equals("enter")) {
      
      if (this.clickedSet.size() == 4) { 
       
        if (this.clickedSet.get(0).category.equals(this.clickedSet.get(1).category) 
            && this.clickedSet.get(1).category.equals(this.clickedSet.get(2).category) 
            && this.clickedSet.get(2).category.equals(this.clickedSet.get(3).category)) {     
        
          numberOfSolvedSets ++;
          
              
          for (int y = 1; y < 600; y += 150) {
            for (int x = 0; x < 352; x += 88) {
              
              if (this.set.get(x / 22 + y / 150).word.equals(this.clickedSet.get(0).word)) {
                this.set.get(x / 22 + y / 150).x = 0;
                this.set.get(x / 22 + y / 150).y = 88 * numberOfSolvedSets;
                this.set.get(x / 22 + y / 150).guessed = true;
                
                if (this.set.get(x / 22 + y / 150).color.equals("yellow")) {
                  yellowCategory++;
                }
                else if (this.set.get(x / 22 + y / 150).color.equals("green")) {
                  greenCategory++;
                }
                else if (this.set.get(x / 22 + y / 150).color.equals("blue")) {
                  blueCategory++;
                }
                else if (this.set.get(x / 22 + y / 150).color.equals("purple")) {
                  purpleCategory++;
                }
              }
              
              if (this.set.get(x / 22 + y / 150).word.equals(this.clickedSet.get(1).word)) {
                this.set.get(x / 22 + y / 150).x = 150;
                this.set.get(x / 22 + y / 150).y = 88 * numberOfSolvedSets;
                this.set.get(x / 22 + y / 150).guessed = true;
              }
              
              if (this.set.get(x / 22 + y / 150).word.equals(this.clickedSet.get(2).word)) {
                this.set.get(x / 22 + y / 150).x = 300;
                this.set.get(x / 22 + y / 150).y = 88 * numberOfSolvedSets;
                this.set.get(x / 22 + y / 150).guessed = true;
              }
              
              if (this.set.get(x / 22 + y / 150).word.equals(this.clickedSet.get(3).word)) {
                this.set.get(x / 22 + y / 150).x = 450;
                this.set.get(x / 22 + y / 150).y = 88 * numberOfSolvedSets;
                this.set.get(x / 22 + y / 150).guessed = true;
              }
            }
          }
          
          for (int y = 1; y < 600; y += 150) {
            for (int x = 0; x < 352; x += 88) {
              
              if (!this.set.get(x / 22 + y / 150).guessed) {
                restOfSet.add(this.set.get(x / 22 + y / 150));
              }
            }
          }
          
          
          if (numberOfSolvedSets == 0) {
            
            for (int y1 = 0; y1 < 600; y1 += 150) {        
              for (int x1 = 88; x1 < 352; x1 += 88) {   //x1 = 88, 176, 264
                int index = (x1 / 88 - 1) + (y1 / 150) * 3;
                restOfSet.get(index).x = y1;
                restOfSet.get(index).y = x1;
              }
            }
          }
          
          if (numberOfSolvedSets == 1) {
            
            for (int y1 = 0; y1 < 600; y1 += 150) {        
              for (int x1 = 176; x1 <= 264; x1 += 88) {  //x1 = 176, 264 
                int index = ((x1 - 176) / 88) + (y1 / 150) * 2;
                restOfSet.get(index).x = y1;
                restOfSet.get(index).y = x1;
              }
            }
          }
          
          if (numberOfSolvedSets == 2) {
            for (int y1 = 0; y1 < 600; y1 += 150) {     
              int x1 = 264;  
              int index = (y1 / 150) * 1;  
              restOfSet.get(index).x = y1;
              restOfSet.get(index).y = x1;
            }
          }
          
    
          for (int i = 0; i < restOfSet.size(); i++) {       
            for (int j = 0; j < this.set.size(); j++) {
              
              if (restOfSet.get(i).word.equals(this.set.get(j).word)) {         
                this.set.get(j).x = restOfSet.get(i).x;
                this.set.get(j).y = restOfSet.get(i).y;
              }
            }
          }
     
          this.clickedSet.clear();
        }
        
        else {
          this.tries--;
          this.clickedSet.get(0).clicked = false;
          this.clickedSet.get(1).clicked = false;
          this.clickedSet.get(2).clicked = false;
          this.clickedSet.get(3).clicked = false;
          this.clickedSet.clear();
        }
      }
    }
    
    if (key.equalsIgnoreCase("r") && this.tries == 0) {
      this.tries = 4;
      this.clickedSet = new ArrayList<Words>(0);
      this.numberOfSolvedSets = -1;
      
      this.yellowCategory = 0;
      this.greenCategory = 0;
      this.blueCategory = 0;
      this.purpleCategory = 0;
      
      this.yellowExists = 0;
      this.greenExists = 0;
      this.blueExists = 0;
      this.purpleExists = 0;
      
      this.positionYellow = 0;
      this.positionGreen = 0;
      this.positionBlue = 0;
      this.positionPurple = 0;
      
      for (int i = 0; i < 16; i++) {
        this.set.get(i).clicked = false;
      }  
      
      
    }
  }
  
  // abstracting the colored labels in which each solved case is organized into
  ArrayList<String> coloredLabels(String color) {
    ArrayList<String> arr = new ArrayList<String>();
    String category = "";
    String wordsInCategory = "";
    
    for (int i = 0; i < 16; i++) {
      if (this.set.get(i).color.equals(color)) {
        category = this.set.get(i).category;
        wordsInCategory += this.set.get(i).word + ", ";
      }
    }
    
    arr.add(category);
    arr.add(wordsInCategory);
    return arr;
  }

}


// represents all the examples and tests
class Examples {
  
  Words tambourine = new Words("TAMBOURINE", "MUSICAL INSTRUMENTS", "yellow");
  Words theremin = new Words("THEREMIN", "MUSICAL INSTRUMENTS", "yellow");
  Words timpani = new Words("TIMPANI", "MUSICAL INSTRUMENTS", "yellow");
  Words trombone = new Words("TROMBONE", "MUSICAL INSTRUMENTS", "yellow");
  
  Words tchotchke = new Words("TCHOTCHKE", "KNICKKNACK", "green");
  Words thingamajig = new Words("THINGAMAJIG", "KNICKKNACK", "green");
  Words trifle = new Words("TRIFLE", "KNICKKNACK", "green");
  Words trinket = new Words("TRINKET", "KNICKKNACK", "green");
  
  Words triangle = new Words("TRIANGLE", "WORDS WITH THE PREFIX MEANING \"THREE\"", "blue");
  Words trident = new Words("TRIDENT", "WORDS WITH THE PREFIX MEANING \"THREE\"", "blue");
  Words trillion = new Words("TRILLION", "WORDS WITH THE PREFIX MEANING \"THREE\"", "blue");
  Words trilobite = new Words("TRILOBITE", "WORDS WITH THE PREFIX MEANING \"THREE\"", "blue");
  
  Words television = new Words("TELEVISION", "WORDS ABBREVIATED WITH \"T\" + LETTER", "purple");
  Words touchdown = new Words("TOUCHDOWN", "WORDS ABBREVIATED WITH \"T\" + LETTER", "purple");
  Words trademark = new Words("TRADEMARK", "WORDS ABBREVIATED WITH \"T\" + LETTER", "purple");
  Words tuberculosis = new Words("TUBERCULOSIS", "WORDS ABBREVIATED WITH \"T\" + LETTER", "purple");
  
  ArrayList<Words> set1 = new ArrayList<Words>(Arrays.asList(
      this.tambourine, this.theremin, this.timpani, this.trombone,
      this.tchotchke, this.thingamajig, this.trifle, this.trinket,
      this.triangle, this.trident, this.trillion, this.trilobite,
      this.television, this.touchdown, this.trademark, this.tuberculosis));
  
  Words absorb = new Words("ABSORB", "CAPTIVATE", "yellow");
  Words entrance = new Words("ENTRANCE", "CAPTIVATE", "yellow");
  Words grab = new Words("GRAB", "CAPTIVATE", "yellow");
  Words rivet = new Words("RIVET", "CAPTIVATE", "yellow");
  
  Words airplane = new Words("AIRPLANE", "THINGS WITH WINGS", "green");
  Words fairy = new Words("FAIRY", "THINGS WITH WINGS", "green");
  Words fly = new Words("FLY", "THINGS WITH WINGS", "green");
  Words hospital = new Words("HOSPITAL", "THINGS WITH WINGS", "green");
  
  Words pocket = new Words("POCKET", "WORDS THAT MODIFY \"WATCH\"", "blue");
  Words smart = new Words("SMART", "WORDS THAT MODIFY \"WATCH\"", "blue");
  Words stop = new Words("STOP", "WORDS THAT MODIFY \"WATCH\"", "blue");
  Words wrist = new Words("WRIST", "WORDS THAT MODIFY \"WATCH\"", "blue");
  
  
  Words back = new Words("BACK", "WORDS REPEATED IN \"MISS MARY MACK\"", "purple");
  Words black = new Words("BLACK", "WORDS REPEATED IN \"MISS MARY MACK\"", "purple");
  Words buttons = new Words("BUTTONS", "WORDS REPEATED IN \"MISS MARY MACK\"", "purple");
  Words mack = new Words("MACK", "WORDS REPEATED IN \"MISS MARY MACK\"", "purple");
  

  ArrayList<Words> set2 = new ArrayList<Words>(Arrays.asList(
      this.absorb, this.entrance, this.grab, this.rivet,
      this.airplane, this.fairy, this.fly, this.hospital,
      this.pocket, this.smart, this.stop, this.wrist,
      this.back, this.black, this.buttons, this.mack));
  
  Words foul = new Words("FOUL", "BAD-SMELLING", "yellow");
  Words rank = new Words("RANK", "BAD-SMELLING", "yellow");
  Words ripe = new Words("RIPE", "BAD-SMELLING", "yellow");
  Words sour = new Words("SOUR", "BAD-SMELLING", "yellow");
  
  Words constant = new Words("CONSTANT", "UNFLUCTUATING", "green");
  Words level = new Words("LEVEL", "UNFLUCTUATING", "green");
  Words stable = new Words("STABLE", "UNFLUCTUATING", "green");
  Words uniform = new Words("UNIFORM", "UNFLUCTUATING", "green");
  
  Words adultsOnly = new Words("ADULTS ONLY", "WHO VIDEO GAMES ARE FOR, PER ESRB RATINGS", "blue");
  Words everyone = new Words("EVERYONE", "WHO VIDEO GAMES ARE FOR, PER ESRB RATINGS", "blue");
  Words teen = new Words("TEEN", "WHO VIDEO GAMES ARE FOR, PER ESRB RATINGS", "blue");
  Words mature = new Words("MATURE", "WHO VIDEO GAMES ARE FOR, PER ESRB RATINGS", "blue");
  
  Words earth = new Words("EARTH", "THINGS WITH LAYERS", "purple");
  Words henhouse = new Words("HENHOUSE", "THINGS WITH LAYERS", "purple");
  Words onion = new Words("ONION", "THINGS WITH LAYERS", "purple");
  Words photoshop = new Words("PHOTOSHOP", "THINGS WITH LAYERS", "purple");
  
  ArrayList<Words> set3 = new ArrayList<Words>(Arrays.asList(
      this.foul, this.rank, this.ripe, this.sour,
      this.constant, this.level, this.stable, this.uniform,
      this.adultsOnly, this.everyone, this.teen, this.mature,
      this.earth, this.henhouse, this.onion, this.photoshop));
  
  Words crossword = new Words("CROSSWORDS", "BLACK-AND-WHITE THINGS", "yellow");
  Words oreo = new Words("OREO", "BLACK-AND-WHITE THINGS", "yellow");
  Words panda = new Words("PANDA", "BLACK-AND-WHITE THINGS", "yellow");
  Words tuxedo = new Words("TUXEDO", "BLACK-AND-WHITE THINGS", "yellow");
  
  Words abel = new Words("ABEL", "ANAGRAM", "green");
  Words able = new Words("ABLE", "ANAGRAM", "green");
  Words bale = new Words("BALE", "ANAGRAM", "green");
  Words bela = new Words("BELA", "ANAGRAM", "green");
  
  Words abe = new Words("ABE", "U.S. PRESIDENTIAL NICKNAMES", "blue");
  Words cal = new Words("CAL", "U.S. PRESIDENTIAL NICKNAMES", "blue");
  Words dick = new Words("DICK", "U.S. PRESIDENTIAL NICKNAMES", "blue");
  Words teddy = new Words("TEDDY", "U.S. PRESIDENTIAL NICKNAMES", "blue");
  
  Words aBell = new Words("A BELL", "CLEAR AS __", "purple");
  Words crystal = new Words("CRYSTAL", "CLEAR AS __", "purple");
  Words day = new Words("DAY", "CLEAR AS __", "purple");
  Words mud = new Words("MUD", "CLEAR AS __", "purple");
  
  ArrayList<Words> set4 = new ArrayList<Words>(Arrays.asList(
      this.crossword, this.oreo, this.panda, this.tuxedo,
      this.abel, this.able, this.bale, this.bela,
      this.abe, this.cal, this.dick, this.teddy,
      this.aBell, this.crystal, this.day, this.mud));
  
  Words fork = new Words("FORK", "PARTS OF A TABLE SETTING", "yellow");
  Words glass = new Words("GLASS", "PARTS OF A TABLE SETTING", "yellow");
  Words napkin = new Words("NAPKIN", "PARTS OF A TABLE SETTING", "yellow");
  Words plate = new Words("PLATE", "PARTS OF A TABLE SETTING", "yellow");

  Words flew = new Words("FLEW", "INCREASED, WITH \"UP\"", "green");
  Words rose = new Words("ROSE", "INCREASED, WITH \"UP\"", "green");
  Words shot = new Words("SHOT", "INCREASED, WITH \"UP\"", "green");
  Words thrust = new Words("THRUST", "INCREASED, WITH \"UP\"", "green");
  
  Words card = new Words("CARD", "KINDS OF DIGITAL STORAGE", "blue");
  Words cloud = new Words("CLOUD", "KINDS OF DIGITAL STORAGE", "blue");
  Words disk = new Words("DISK", "KINDS OF DIGITAL STORAGE", "blue");
  Words drive = new Words("DRIVE", "KINDS OF DIGITAL STORAGE", "blue");
  
  Words bounce = new Words("BOUNCE", "UNITS OF VOLUME PLUS LETTER", "purple");
  Words galleon = new Words("GALLEON", "UNITS OF VOLUME PLUS LETTER", "purple");
  Words pinot = new Words("PINOT", "UNITS OF VOLUME PLUS LETTER", "purple");
  Words quartz = new Words("QUARTZ", "UNITS OF VOLUME PLUS LETTER", "purple");
  
  ArrayList<Words> set5 = new ArrayList<Words>(Arrays.asList(
      this.fork, this.glass, this.napkin, this.plate,
      this.flew, this.rose, this.shot, this.thrust,
      this.card, this.cloud, this.disk, this.drive,
      this.bounce, this.galleon, this.pinot, this.quartz));
  
  ArrayList<ArrayList<Words>> allSets = new ArrayList<ArrayList<Words>>(Arrays.asList(
      set1, set2, set3, set4, set5));
  
  Random random = new Random();
  int randomNumber = random.nextInt(5);
  

            
  
  void testBigBang(Tester t) {
    
    Random random = new Random();
    int randomNumber = random.nextInt(5);
    
    
    Collections.shuffle(allSets.get(randomNumber));
    ConnectionsWorld world = new ConnectionsWorld(allSets.get(randomNumber));
    int worldWidth = 700;
    int worldHeight = 500;
    world.bigBang(worldWidth, worldHeight, 0);
  }
  
  
  WorldScene mtWs;
  WorldScene wsWithOneWord;
  WorldScene wsWithTwoWords;
  
  WorldScene filledWorld;
  
  ConnectionsWorld world;

  
  void initData() {
    
    mtWs = new WorldScene(700, 500);
    wsWithOneWord = new WorldScene(700, 500);
    wsWithTwoWords = new WorldScene(700, 500);
    world = new ConnectionsWorld(this.set1);
    
    TextImage wordImage1 = new TextImage("BUTTONS", 16, Color.black);
    WorldImage overlay1 = new OverlayImage(wordImage1, 
          (new RectangleImage(140, 80, OutlineMode.SOLID, Color.LIGHT_GRAY)));
    
    wsWithOneWord.placeImageXY(overlay1, 100, 100);
    
    TextImage wordImage2 = new TextImage("POCKET", 16, Color.black);
    WorldImage overlay2 = new OverlayImage(wordImage2, 
          (new RectangleImage(140, 80, OutlineMode.SOLID, Color.LIGHT_GRAY)));
    
    wsWithTwoWords.placeImageXY(overlay1, 100, 100);
    wsWithTwoWords.placeImageXY(overlay2, 200, 200);
    
    filledWorld = new WorldScene(700, 500);
    
    for (int y = 0; y < 600; y += 150) {
      
      for (int x = 0; x < 352; x += 88) {
        this.set1.get(x / 22 + y / 150).drawWord(filledWorld, y, x);
      }
    }
    
    TextImage livesDisplay = new TextImage("lives: 4", 20, Color.black);
    filledWorld.placeImageXY(livesDisplay, 320, 480);
   
  }
    
  void testOnMouseClicked(Tester t) {
    initData();
    ConnectionsWorld OMC5 = new ConnectionsWorld(this.set5);
    Posn validPos = new Posn(100, 150);
    OMC5.onMouseClicked(validPos);
    
    t.checkExpect(OMC5.clickedSet.size(), 0);
    //t.checkExpect(OMC5.clickedSet.get(0).clicked, true);
    
    Posn validPos2 = new Posn(200, 150);
    OMC5.onMouseClicked(validPos2);
    
    t.checkExpect(OMC5.clickedSet.size(), 0);

    Posn invalidPosn = new Posn(-100, -150);
    OMC5.onMouseClicked(invalidPosn);

    t.checkExpect(OMC5.clickedSet.size(), 0);
    
    // check if none of the words in the set were actually clicked
    for (int i = 0; i < this.set5.size(); i++) {
      t.checkExpect(this.set5.get(i).clicked, false);
    }


  }
  
  
  // tests for key event (when enter each time you choose 4 words, 
  // when r is clicked - reset world, or when nothing is pressed)
  void testOnKeyEvent(Tester t) {
    initData();
    ConnectionsWorld KE5 = new ConnectionsWorld(this.set5);
    KE5.onKeyEvent("enter");
    t.checkExpect(KE5.clickedSet.size(), 0);
    t.checkExpect(KE5.tries, 4);
    
    KE5.onKeyEvent("r");
    t.checkExpect(KE5.clickedSet.size(), 0);
    t.checkExpect(KE5.tries, 4);
    
    KE5.onKeyEvent("t");
    t.checkExpect(KE5, KE5);
    
        
  }
  
  void testClickHelper(Tester t) {
    t.checkExpect(this.bounce.clickHelper(new Posn(0, 0)), false);
    t.checkExpect(this.mack.clickHelper(new Posn(200, 200)), false);
    t.checkExpect(this.wrist.clickHelper(new Posn(-100, -200)), false);
    t.checkExpect(this.card.clickHelper(new Posn(100, 100)), true);
    t.checkExpect(this.fairy.clickHelper(new Posn(70, 40)), false);
    t.checkExpect(this.mud.clickHelper(new Posn(300, 40)), false);

    
  }


  void testColoredLabels(Tester t) {
    ConnectionsWorld set1Label = new ConnectionsWorld(this.set1);
    t.checkExpect(set1Label.coloredLabels("purple"), new ArrayList<String>(
        Arrays.asList("WORDS ABBREVIATED WITH \"T\" + LETTER", 
            "TELEVISION, TOUCHDOWN, TRADEMARK, TUBERCULOSIS, ")));
    
    ConnectionsWorld set2Label = new ConnectionsWorld(this.set2);
    t.checkExpect(set2Label.coloredLabels("green"), new ArrayList<String>(
        Arrays.asList("THINGS WITH WINGS", "AIRPLANE, FAIRY, FLY, HOSPITAL, ")));
    
    //    ConnectionsWorld set3Label = new ConnectionsWorld(this.set3);
    //    t.checkExpect(set3Label.coloredLabels("blue"), new ArrayList<String>(
    //        Arrays.asList("U.S. PRESIDENTIAL NICKNAMES", "ABE, CAL, DICK, TEDDY, ")));
    
    
    t.checkExpect(set1Label.coloredLabels("red"), 
        new ArrayList<String>(Arrays.asList("", "")));
    ConnectionsWorld mtW = new ConnectionsWorld(new ArrayList<Words>());
    t.checkExpect(mtW.coloredLabels("blue"), 
        new ArrayList<String>(Arrays.asList("", "")));
  }
  
  

  //tests for drawWord
  void testDrawWord(Tester t) {
  
    initData();
    this.buttons.drawWord(mtWs, 0, 0);
    t.checkExpect(mtWs, wsWithOneWord);
    this.pocket.drawWord(mtWs, 100, 100);
    t.checkExpect(mtWs, wsWithTwoWords);
  }
  
  //tests for makeScene
  void testMakeScene(Tester t) {
    initData();
    //t.checkExpect(this.world.makeScene(), this.filledWorld);
    
    //    Random randPredict = new Random(3);
    //    ConnectionsWorld world = new ConnectionsWorld(this.set1);
   
    //    WorldScene newScene = new WorldScene(700, 500);
    //    t.checkExpect(this.world.makeScene(), randPredict.world);
    t.checkExpect(this.world.makeScene(), this.filledWorld);

    
    
  }
  
  
}