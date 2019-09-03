package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

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

    public void askQuestion() {
        System.out.println("The category is " + name);
        System.out.println(questions.removeFirst());
    }
}

public class Game {
    private final Category[] categories;

    private ArrayList players = new ArrayList();
    private int[] places = new int[6];
    private int[] purses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        Category pop = new Category("Pop");
        Category rock = new Category("Rock");
        Category sports = new Category("Sports");
        Category science = new Category("Science");
        categories = new Category[]{
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

    public boolean add(String playerName) {
        players.add(playerName);
        places[players.size()] = 0;
        purses[players.size()] = 0;
        inPenaltyBox[players.size()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                movePlayer(roll);
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            movePlayer(roll);
        }

    }

    private void movePlayer(int roll) {
        places[currentPlayer] = (places[currentPlayer] + roll) % 12;

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);

        categories[places[currentPlayer]].askQuestion();

    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
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

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;

        return didPlayerWin();
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
