import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import javax.swing.*;
import java.awt.*;

public class Hangman extends GraphicsProgram {

    private char[] pickedWord = retrieveWord();
    GRect[] blanks = new GRect[pickedWord.length];
    int blankHeight = 1;
    int blankWidth = 30;
    int blankSpacing = (pickedWord.length*20);

    // JButton[] letterButtons = new JButton[26];
    String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    @Override
    public void init(){

        drawHangStation();
        drawBlanks();
        addLetterButtons();

    }

    private void addLetterButtons(){

        int xBtn = 15;
        int yBtn = 15;
        int buttonWidth = 50;
        int buttonHeight = 13;
        int buttonSpacing = 5;

        //initialize all buttons with pre-populated values
        GRect[] btnArr = {
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
                new GRect(buttonWidth,buttonHeight),
        };

        for (int i = 0; i < alphabet.length; i++){
            add(btnArr[i],xBtn,yBtn);
            btnArr[i].setFilled(true);
            btnArr[i].setFillColor(Color.lightGray);
            yBtn += buttonSpacing+buttonHeight;
        }

    }

    private void drawBlanks(){

        for (int i = 0; i < blanks.length; i++) {

            int currentX = 100;

            blanks[i] = new GRect(blankWidth,blankHeight);
            add(blanks[i],290+(currentX*i), 300);
            currentX += blankSpacing;
        }

    }

    private void drawHangStation(){
        Color darkBrown = new Color(59, 42, 0);
        Color brown = new Color(154, 111, 33);

        GRect hangBase = new GRect(70,25);
        add(hangBase, 140, 300);
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

        GRect hangNPost = new GRect(10,30);
        add(hangNPost, hangHandle.getX()+hangHandle.getWidth()-hangNPost.getWidth(),hangHandle.getY());
        hangNPost.setFilled(true);
        hangNPost.setFillColor(brown);
    }

    private char[] retrieveWord(){

        String[] words = {"dog","mouse"};

        int wordValue = RandomGenerator.getInstance().nextInt(0, words.length-1);
        String pickedWord = words[wordValue];
        char[] convertedWord = pickedWord.toCharArray();
        return convertedWord;

    }

    public static void main(String[] args) {
        Hangman x = new Hangman();
        x.start();
    }

}