import java.awt.*;

public class Ball {
    //defines a variable for the WIDTH/HEIGHT of the ball. Since WIDTH = HEIGHT we will use diameter
    final int diameter = 15;
    //defines a constant for the ball's speed
    final int SPEED = 6;
    //defines variables for the POSITION of the ball
    int x, y;
    double dx = SPEED, dy = SPEED;

    double MAXANGLE = 5*Math.PI/12; //70 degrees

    Board board;

    //ball constructor to set initial position values
    //passes in a parameter to access a board object
    public Ball(Board board){
        this.board = board;
        x = 0;
        y = 0;
    }

    public void move(Paddle other){
        //ball bounces off bottom and top
        if(y+diameter > board.getHeight()|| y < 0)
            dy*=-1;
        //ball bounces off right and left
        if(x + diameter > board.getWidth() - board.getEDGESPACE()){
            GAMESTATES.increasePScore();
            board.gameReset();
            dx*=-1;
        }
        if(x < board.getEDGESPACE()){
            GAMESTATES.increaseCScore();
            board.gameReset();
            dx*=-1;
        }

        //updates x and y based on velocity
        x += dx;
        y += dy;

    }

    public void checkCollisions(Paddle other){

        if(getBounds().intersects(other.getBounds())){
           double paddleY = other.getBounds().getY();
           double paddleC = other.getBounds().getHeight()/2;
           double bally = y;

           double relativeIntersect = (paddleY + paddleC) - bally;
           double normalIntersect = relativeIntersect/paddleC;
           double bounceAngle = MAXANGLE * normalIntersect;

           if(y+(diameter/2) < paddleY)
               bally = y + diameter;
           else if(y + diameter/2 > paddleY + other.getBounds().getHeight())
               bally = y;
           else
               bally = y + diameter/2;

           if(x < board.getWidth()/2){
               dx = (int)(SPEED*Math.cos(bounceAngle));
           }
           if(x > board.getWidth()/2){
               dx = (int)(SPEED*-Math.cos(bounceAngle));
           }
           dy = (int)(SPEED*-Math.sin(bounceAngle));

        }
    }

    public Rectangle getBounds(){

        return new Rectangle(x, y, diameter, diameter);
    }


    //method to set the position of the ball. Adjust the position to reflect where the user wants
    //the CENTER of the ball
    public void setPosition(int x, int y){
        this.x = x - diameter/2;
        this.y = y - diameter/2;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getDiam(){
        return diameter;
    }

    //paint method to render the ball graphics
    public void paint(Graphics g){
        g.fillOval(x, y, diameter, diameter);
    }
}
