import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.*;

public class Hangman extends GraphicsProgram {

    @Override
    public void init(){

        drawHangStation();

    }

    public Hangman(){

    }

    private void drawHangStation(){
        Color darkBrown = new Color(59, 42, 0);
        Color brown = new Color(154, 111, 33);

        GRect hangBase = new GRect(70,25);
        add(hangBase, 90, 300);
        hangBase.setFilled(true);
        hangBase.setFillColor(darkBrown);

        GRect hangPost = new GRect(10,180);
        add(hangPost, hangBase.getX() + hangBase.getWidth()/2 - hangPost.getWidth()/2,hangBase.getY() - hangPost.getHeight());
        hangPost.setFilled(true);
        hangPost.setFillColor(brown);

        GRect hangHandle = new GRect(75,10);
        add(hangHandle, hangPost.getX(),hangPost.getY());
        hangHandle.setFilled(true);
        hangHandle.setFillColor(brown);
    }

    public static void main(String[] args) {
        new Hangman().start();
    }

}
