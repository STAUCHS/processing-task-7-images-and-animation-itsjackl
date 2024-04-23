import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {

  // Initialize images
  PImage imgCannon;
  PImage imgFiredCannon;
  PImage imgBackground;
  PImage imgCannonball;
  PImage imgBang;
  PImage[] Bird = new PImage[6]; // Special initialization for bird as it is animated

  // Declare variables and assign values if needed
  float BirdY = height / 2;
  float ySpeed = 60; // Speed of the bird, increase for difficulty
  float CannonballX;
  float CannonballY;
  float blackRectY;
  float yRectSpeed = 20; // Speed of the barrier, increase for difficulty
  float angle = PI / 10; // Initial angle of cannonball
  float velocity = 120; // Initial velocity of the cannonball
  float time = 0; // Time variable
  boolean fired = false;
  boolean victory = false;

  public void settings() {
    size(800, 800); // Size of the window, variable
  }

  // Load and resize images
  public void setup() {
    imgBackground = loadImage("cartoon_background.jpg");
    imgBackground.resize(width, height);
    imgCannon = loadImage("cannon.png");
    imgCannon.resize(width / 3, height / 3);
    imgFiredCannon = loadImage("fired_cannon.png");
    imgFiredCannon.resize(width/3, height/3);
    imgCannonball = loadImage("cannonball.png");
    imgCannonball.resize(width / 25, height / 25);
    imgBang = loadImage("bang.png");
    imgBang.resize(width / 5, height / 5);
    Bird[0] = loadImage( "bird-0.gif" );
    Bird[1] = loadImage( "bird-1.gif" );
    Bird[2] = loadImage( "bird-2.gif" );
    Bird[3] = loadImage( "bird-3.gif" );
    Bird[4] = loadImage( "bird-4.gif" );
    Bird[5] = loadImage( "bird-5.gif" );
    Bird[0].resize(width / 3, height / 4);
    Bird[1].resize(width / 3, height / 4);
    Bird[2].resize(width / 3, height / 4);
    Bird[3].resize(width / 3, height / 4);
    Bird[4].resize(width / 3, height / 4);
    Bird[5].resize(width / 3, height / 4);
    frameRate(15);
  }

  public void draw() {
    background(imgBackground);
    
    // Drawing cannonball animation with conditionals
    if (fired && !victory) {
      image(imgFiredCannon, 0, height / 2); // Fired cannon image
      CannonballX = velocity * cos(angle) * time + width/3; // Calculate x position of cannonball
      CannonballY = height/2 - (velocity * sin(angle) * time - 1 * 19.6f * time * time); // Calculate y position of cannonball
      time += 0.75; // Increment time
      image(imgCannonball, CannonballX, CannonballY); // Draw cannonball

      // If it hits the barrier rectangle
      if (CannonballY >= blackRectY && CannonballY <= blackRectY + 100 && CannonballX <= width / 2 + 100) {
        fired = false; // Stop firing
      }
      
      // If it hits the bird
      if (dist(CannonballX, CannonballY, width / 1.3f, BirdY + 60) < 75) {
        victory = true; // Victory condition
        fired = false; // Stop updating cannonball position
        ySpeed = 0; // Stop updating bird position
        yRectSpeed = 0; // Stop updating barrier position
      }

      // If it misses both
      if (CannonballY >= 2 * height / 3) {
        fired = false; // Reset when cannonball hits the ground
        time = 0; // Reset time
      }
      
    } else {
      image(imgCannon, 0, height / 2); // Draw cannon
    }

    // Draw moving barrier
    fill(0);
    rect(width / 2, blackRectY, width / 80, width / 8);
    blackRectY += yRectSpeed; // Move the black rectangle
    if (blackRectY <= 0 || blackRectY >= 3 * height / 4) { // If rectangle hits ground or top, bounce
      yRectSpeed *= -1;
    }

    // Draw bird
   image(Bird[frameCount%6], width / 1.5f, BirdY); // Frame count allows bird to cycle through frames so the bird is animated
    BirdY += ySpeed; // Move the bird
    if (BirdY < 0 || BirdY > 3 * height / 4) { // If the bird hits the bottom or top, bounce
      ySpeed *= -1;
    } 

    // Victory condition
    if (victory) {
      image(imgBang, width/1.35f, BirdY + 60); // Draw explosion on bird
      textSize(50);
      textAlign(CENTER, CENTER);
      fill(255, 0, 0);
      text("VICTORY", width/2, height/2); // Victory text
    } 
  }

  // Condition to fire cannonball
  public void mousePressed() {
    fired = true; // Fire the cannonball
  }
}