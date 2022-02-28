import acm.graphics.*;
import svu.csc213.Dialog;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Hangman extends GraphicsProgram {

    /*
    All the variables outside a method are meant to be accessible throughout the class.
    This helps ensure that every method can access the picked words, the GLabels for the letters, and so on.
     */

    //initializing and assigning arrays which will hold values for the assigned word
    private char[] pickedWord = retrieveWord();
    GLabel[] letterLabels = new GLabel[pickedWord.length];

    //initializing and pre-populating the blanks array
    GRect[] blanks = new GRect[pickedWord.length];

    //setting the blank's dimensions and spacing
    int blankHeight = 1;
    int blankWidth = 30;
    int blankSpacing = 2;

    //setting up lives. The stick-man has 6 limbs, so this means 6 lives.
    int lives = 6;

    //initializing and assigning the game control booleans
    boolean gameOver = false;
    boolean win = false;

    //initializing the noose position variables
    double nooseX;
    double nooseY;

    String pickedWordString;

    //initializing the head, body, and limbs. Assigning the head and torso. Limbs will be assigned later.
    GOval head = new GOval(50,50);
    GRect torso = new GRect(.5,70);
    GLine lArm = new GLine(0,0,0,0);
    GLine rArm = new GLine(0,0,0,0);
    GLine lLeg = new GLine(0,0,0,0);
    GLine rLeg = new GLine(0,0,0,0);

    //initialize buttons
    JButton guessBtn = new JButton("Guess Letter");
    JButton quitBtn = new JButton("Quit Game");
    JButton playAgain = new JButton("Play Again");
    JButton iGiveUp = new JButton("I Give Up");
    JButton guessWord = new JButton("Guess Whole Word");

    //initialize usedLettersBox
    GRect usedButtonsBox = new GRect(200,40);


    @Override
    public void init(){

        drawHangStation();
        drawBlanks();
        addButtons();

        setupStickDude();

        for (int i = 0; i < pickedWord.length; i++) {
            letterLabels[i] = new GLabel(Character.toString(pickedWord[i]));
            add(letterLabels[i], blanks[i].getX() + (blanks[i].getWidth()/2 - letterLabels[i].getWidth()/2),blanks[i].getY() - 10);
            letterLabels[i].setVisible(false);
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae){
        switch(ae.getActionCommand()){
            case "Guess Letter":
                guessLetter(Dialog.getString("What letter do you guess?"));
                break;
            case "Quit Game":
                System.exit(0);
                break;
            case "I Give Up":
                iGiveUp();
                break;
            case "Guess Whole Word":
                guessWord();
                break;
            case "Play Again":
                playAgain();
        }
    }

    private void playAgain(){
        //resetting all variables
        lives = 6;
        pickedWord = retrieveWord();
        gameOver = false;
        win = false;

        removeAll();
        init();

        /*
        updateHangMan();
        guessBtn.setVisible(true);
        guessWord.setVisible(true);
        playAgain.setVisible(false);
        iGiveUp.setVisible(true);
        quitBtn.setVisible(true);
        */
    }

    private void guessWord(){
        String guess = Dialog.getString("What do you think the word is? If you guess incorrectly, it's game over...");
        if(guess.equals(pickedWordString)){
            win();
        }else{
            endGame();
        }
    }

    private void revealLetter(int letterPlacement){
        letterLabels[letterPlacement-1].setVisible(true);
    }

    private void updateHangMan(){

        if(lives == 6){
            head.setVisible(false);
            torso.setVisible(false);
            lArm.setVisible(false);
            rArm.setVisible(false);
            lLeg.setVisible(false);
            rLeg.setVisible(false);
        }else if(lives == 5){
            head.setVisible(true);
            torso.setVisible(false);
            lArm.setVisible(false);
            rArm.setVisible(false);
            lLeg.setVisible(false);
            rLeg.setVisible(false);
        }else if(lives == 4){
            head.setVisible(true);
            torso.setVisible(true);
            lArm.setVisible(false);
            rArm.setVisible(false);
            lLeg.setVisible(false);
            rLeg.setVisible(false);
        }else if(lives == 3){
            head.setVisible(true);
            torso.setVisible(true);
            lArm.setVisible(true);
            rArm.setVisible(false);
            lLeg.setVisible(false);
            rLeg.setVisible(false);
        }else if(lives == 2){
            head.setVisible(true);
            torso.setVisible(true);
            lArm.setVisible(true);
            rArm.setVisible(true);
            lLeg.setVisible(false);
            rLeg.setVisible(false);
        }else if(lives == 1){
            head.setVisible(true);
            torso.setVisible(true);
            lArm.setVisible(true);
            rArm.setVisible(true);
            lLeg.setVisible(true);
            rLeg.setVisible(false);
        }else if(lives == 0){
            head.setVisible(true);
            torso.setVisible(true);
            lArm.setVisible(true);
            rArm.setVisible(true);
            lLeg.setVisible(true);
            rLeg.setVisible(true);
            gameOver = true;
            endGame();
        }

    }

    private void iGiveUp(){
        if(Dialog.getYesOrNo("Are you sure you give up? This will end the game.") == true) {
            revealAllLetters();
            playAgain.setVisible(false);
            guessBtn.setVisible(false);
            iGiveUp.setVisible(false);
            Dialog.showMessage("You ended this game.");
        }
    }

    private void endGame(){
        revealAllLetters();
        playAgain.setVisible(false);
        guessBtn.setVisible(false);
        Dialog.showMessage("YOU LOOSE...");
        iGiveUp.setVisible(false);
        guessWord.setVisible(false);
    }

    private void revealAllLetters(){
        for (int i = 0; i < pickedWord.length; i++) {
            revealLetter(i+1);
        }
    }

    private void guessLetter(String guessedLetter){

        char guessedChar = guessedLetter.charAt(0);

        if(checkForLetter(guessedChar,pickedWord)){
            guessedCorrectly(guessedChar);
        }else{
            guessedWrongly();
        }
    }

    private void guessedWrongly(){
        System.out.println("Answer Incorrect.");
        lives = lives - 1;
        System.out.println("Life count –1. Remaining lives: " + lives);

        if(lives == 0){
            gameOver = true;
            System.out.println("You loose.");
        }

        updateHangMan();

    }

    private void guessedCorrectly(char guessedChar){
        System.out.println("Answer Correct.");
        int letterPlacement = findLetterPlacement(guessedChar, pickedWord);

        revealLetter(letterPlacement);
        System.out.println(letterPlacement);

        checkForWin();

        if(win == true){
            win();
        }
    }

    private void win(){
        Dialog.showMessage("YOU WIN!");
        System.out.println("You win.");
        guessBtn.setVisible(false);
        playAgain.setVisible(false);
        iGiveUp.setVisible(false);
        guessWord.setVisible(false);
    }

    private void checkForWin(){
        //boolean[] revealedLetters = new boolean[pickedWord.length];
        boolean isWon = true;
        for (int i = 0; i < pickedWord.length; i++) {

            if(!(letterLabels[i].isVisible() == true)){
                isWon = false;
            }

        }

        if(isWon){
            win = true;
        }
    }

    private boolean checkForLetter(char guessedChar, char[] pickedWord){

        boolean containsChar = false;

        for (int i = 0; i < pickedWord.length; i++) {
            if(guessedChar == pickedWord[i]){
                containsChar = true;
                letterLabels[i].setVisible(true);
            }
        }
        return containsChar;
    }

    private int findLetterPlacement(char guessedChar, char[] pickedWord){

        int charLocation = 0;

        for (int i = 0; i < pickedWord.length; i++) {
            if(guessedChar == pickedWord[i]){
                charLocation = i+1;
            }
        }
        return charLocation;
    }

    private void addButtons(){
        add(guessBtn,WEST);
        add(guessWord, WEST);

        add(playAgain, WEST);
        add(iGiveUp,WEST);
        add(quitBtn,WEST);

        playAgain.setVisible(false);

        addActionListeners();
    }

    private void drawBlanks(){

        for (int i = 0; i < blanks.length; i++) {

            int startingX = 200;
            int currentX = 50;

            blanks[i] = new GRect(blankWidth,blankHeight);
            add(blanks[i],startingX+(currentX*i), 300);
        }

    }

    private void drawHangStation(){
        Color darkBrown = new Color(59, 42, 0);
        Color brown = new Color(154, 111, 33);

        GRect hangBase = new GRect(70,25);
        add(hangBase, 50, 300);
        hangBase.setFilled(true);
        hangBase.setFillColor(darkBrown);

        GRect hangPost = new GRect(10,210);
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
        nooseX = hangHandle.getX() + hangHandle.getWidth();
        nooseY = hangNPost.getY() + hangNPost.getHeight();
    }

    private void setupStickDude(){

        int headOffset = 6;
        int armXOffset = 13;
        int armYOffset = 23;
        int legXOffset = 13;
        int legYOffset = 21;

        add(head,nooseX - head.getWidth()/2 - headOffset,nooseY);
        head.setFilled(true);
        head.setFillColor(Color.lightGray);

        add(torso, head.getX() + head.getWidth()/2, head.getY() + head.getHeight());
        torso.setFilled(true);

        lArm.setStartPoint(torso.getX(),torso.getY() + torso.getHeight()/4);
        lArm.setEndPoint(torso.getX() - armXOffset, (torso.getY() + torso.getHeight()/4) + armYOffset);
        add(lArm);

        rArm.setStartPoint(torso.getX() + torso.getWidth(),torso.getY() + torso.getHeight()/4);
        rArm.setEndPoint(torso.getX() + torso.getWidth() + armXOffset, (torso.getY() + torso.getHeight()/4) + armYOffset);
        add(rArm);

        lLeg.setStartPoint(torso.getX(),torso.getY()+torso.getHeight());
        lLeg.setEndPoint(torso.getX() - legXOffset, torso.getY() + torso.getHeight() + legYOffset);
        add(lLeg);

        rLeg.setStartPoint(torso.getX() + torso.getWidth(),torso.getY()+torso.getHeight());
        rLeg.setEndPoint(torso.getX() +  legXOffset, torso.getY() + torso.getHeight() + legYOffset);
        add(rLeg);

        head.setVisible(false);
        torso.setVisible(false);
        lArm.setVisible(false);
        rArm.setVisible(false);
        lLeg.setVisible(false);
        rLeg.setVisible(false);

    }

    private char[] retrieveWord(){

        // todo : update the words list with more words
        // todo : fix flexibility with words
        String[] words = {
                "paper","dog","mouse","house","cat","personal","computer","nine","uneven","delete","function","program","cut","fiddlesticks","dine",
                "big","laptop","mate","small","dill","chortled","high","drug","medicine","english","teacher","journey","duty","child","banana","fruit","apple","grape",
                "pickle", "chrome","matte","pencil","acne","word","hangman","egg","waffle","bacon","ice","plot","graph","math","help"};

        int wordValue = RandomGenerator.getInstance().nextInt(0, words.length-1);
        pickedWordString = words[wordValue];
        char[] convertedWord = pickedWordString.toCharArray();
        return convertedWord;

    }

    public static void main(String[] args) {
        Hangman x = new Hangman();
        x.start();
    }

}