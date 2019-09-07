package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Player {

    static boolean isGettingOutOfPenaltyBox = false;
    private int place;
    private String playerName;
    private Game game;
    private boolean inPenaltyBox;

    Player(String playerName, Game game) {
        this.playerName = playerName;
        this.game = game;
    }

    boolean tryToGetOutOfPenaltyBox(int roll) {
        if (inPenaltyBox) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(this + " is getting out of the penalty box");
                return true;
            } else {
                System.out.println(this + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return playerName;
    }

    void takeTurn(int roll) {
        place = (place + roll) % Game.BOARD_SIZE;

        System.out.println(playerName
                + "'s new location is "
                + place);

        game.askQuestion(place);
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }
}

class Category {
    private String name;
    private LinkedList<String> questions;

    public Category(String name) {
        this.name = name;
        questions = new LinkedList<>();
        for (int i = 0; i < 50; i++) {
            questions.addLast(name + " Question " + i);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public void getQuestion() {
        System.out.println("The category is " + name);
        System.out.println(questions.removeFirst());
    }
}

public class Game {
    public static final int BOARD_SIZE = 12;
    private final Category[] board;

    private int[] purses = new int[6];
    private int currentPlayer = 0;
    private List<Player> players = new ArrayList<>();

    public Game() {
        Category pop = new Category("Pop");
        Category rock = new Category("Rock");
        Category sports = new Category("Sports");
        Category science = new Category("Science");
        board = new Category[]{
                pop,
                science,
                sports,
                rock,
                pop,
                science,
                sports,
                rock,
                pop,
                science,
                sports,
                rock
        };
    }

    public void addPlayer(String playerName) {
        players.add(new Player(playerName, this));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    public void roll(int roll) {
        System.out.println(getCurrentPlayer() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if(getCurrentPlayer().tryToGetOutOfPenaltyBox(roll)) {
            getCurrentPlayer().takeTurn(roll);
        }
    }

    void askQuestion(int place) {
        board[place].getQuestion();
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public boolean wasCorrectlyAnswered() {
        if (getCurrentPlayer().isInPenaltyBox()) {
            if (Player.isGettingOutOfPenaltyBox) {
                return scoreCurrentPlayer();
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }
        } else {
            return scoreCurrentPlayer();
        }
    }

    private boolean scoreCurrentPlayer() {
        System.out.println("Answer was correct!!!!");
        purses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;

        return winner;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        getCurrentPlayer().setInPenaltyBox(true);

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
