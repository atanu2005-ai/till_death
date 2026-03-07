import java.nio.channels.Pipe.SourceChannel;
import java.util.*;

public class till_death {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        showRules(); //Showing rules

        System.out.println("Press Enter to start the game...");
        sc.nextLine(); //Wait for user to press Enter

        loadingEffect("Starting the battle");
        System.out.print("Enter your name: "); //Player name input
        String name = sc.nextLine();
        System.out.println();

        System.out.println("Choose Character:");
        showCharacter(); 
        int character = sc.nextInt(); //Character choice input
        loadingEffect("Choosing character");
        while(!characterLimitCheck(character)) {
            character = sc.nextInt(); //Ask again if invalid
        }

        int myHealth = 0;
        if(character == 1) {
            myHealth = 1400; //Warrior
        }else if(character == 2) {
            myHealth = 1600; //Assassin
        }else if(character == 3) {
            myHealth = 1800; //Mage
        }else {
            myHealth = 2000; //Paladin
        }

        int botHealth = 2000;

        int myPoints = 0;
        int botPoints = 0;
        int round = 3;

        //Game loop
        while(round > 0) {
            loadingEffect("Flipping the coin");
            Random rand = new Random();
            boolean turn = rand.nextBoolean();

            if(turn) {
                System.out.println(name + " won the toss and will go first!");
            } else {
                System.out.println("Bot won the toss and will go first!");
            }
            int crrPoint = gameLoop(turn, name, myHealth, botHealth, character, myPoints, botPoints, sc);
            if(crrPoint > 0) {
                myPoints += crrPoint;
            } else {
                botPoints -= crrPoint;
            }
            round--;
        }
        if(myPoints > botPoints) {
            System.out.println(name + " won the game with " + myPoints + " points!");
        } else if(botPoints > myPoints) {
            System.out.println("Bot won the game with " + botPoints + " points!");
        } else {
            System.out.println("The game is a draw with both players having " + myPoints + " points!");
        }
        sc.close();
    }

    public static int gameLoop(boolean turn, String name, int myHealth, int botHealth, int character, int myPoints, int botPoints, Scanner sc) {
        int healLeft = 1; //To track if heal is used
        int slayLeft = 2; //To track if slay is used
        int abilityLeft = 1; //To track if ability is used

        //Game loop
        while(true) {
            if(turn) { //Player's turn
                System.out.println("_________________________________");
                System.out.println(name + "'s Health: " + myHealth);
                System.out.println("Bot's Health: " + botHealth);
                System.out.println("_________________________________");
                showMenu(slayLeft, abilityLeft, healLeft); //Showing attack options
                int move = sc.nextInt(); //Player move input
                if(move < 1 || move > 4) {
                    System.out.println("Invalid move, try again");
                    continue; //ask again
                }
                if(!moveLimitCheck(move, healLeft, slayLeft, abilityLeft)) {
                    continue; //ask again
                }
                loadingEffect("Attacking the bot"); //Loading effect for player's attack
                if(move == 2) {
                    slayLeft--;
                } else if(move == 3) {
                    abilityLeft--;
                } else if(move == 4 && healLeft == 1) {
                    myHealth = Math.min(myHealth + 300, 1000); //Healing, but not exceeding 1000
                    healLeft = 0; //Heal used
                }
                if(move != 4) { //If not healing, then attack
                    botHealth = playerTurn(move, character, botHealth);
                }
            }else { //Bot's turn
                loadingEffect("Bot is attacking you"); //Loading effect for bot's attack    
                myHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(160, 200));
            }
            //check if game is over
            if(myHealth <= 0) { //If bot wins
                System.out.println("________________________________");
                System.out.println(name + "'s Health: " + 0);
                System.out.println("Bot's Health: " + botHealth);
                System.out.println("________________________________");
                System.out.println("Bot won the round!");
                return -botHealth; //negetive means bot wins, return bot's health as points
            }else if(botHealth <= 0) { //If player wins
                System.out.println("________________________________");
                System.out.println(name + "'s Health: " + myHealth);
                System.out.println("Bot's Health: " + 0);
                System.out.println("________________________________");
                System.out.println(name + ", you won the round!");
                return myHealth; //positive means player wins, return player's health as points
            }
            turn = !turn;
        }
    }

    public static void showRules() {
        System.out.println("Welcome to Till Death! A 1v1 battle simulation");
        System.out.println();
        System.out.println("Rules:");
        System.out.println("1) Bot has 1000 HP while you have 800-1200 HP depending on your character choice");
        System.out.println("2) You have 3 differnt attack modes, choose wisely as per limits!");
        System.out.println("3) You have 4 different characters to choose from");
        System.out.println("4) Toss will decide who goes first");
        System.out.println("5) First player to hit 0 HP will loose lol :)");
    }

    public static void showMenu(int slayLeft, int abilityLeft, int healLeft) {
        System.out.println("Choose button:");
        System.out.println("1/ Basic");
        System.out.println("2/ slay" + "(Left: " + slayLeft + ")");
        System.out.println("3/ Ability" + "(Left: " + abilityLeft + ")");            
        System.out.println("4/ heal" + "(Left: " + healLeft + ")");
    }

    public static void showCharacter() {
        System.out.println("1/ Warrior");
        System.out.println("2/ Assassin");
        System.out.println("3/ Mage");
        System.out.println("4/ Paladin");
    }

    public static boolean moveLimitCheck(int move, int healLeft, int slayLeft, int abilityLeft) {
        if(move == 2 && slayLeft == 0) {
            System.out.println("Slay already used, choose another move");
            return false;
        } else if(move == 3 && abilityLeft == 0) {
            System.out.println("Ability already used, choose another move");
            return false;
        } else if(move == 4 && healLeft == 0) {
            System.out.println("Heal already used, choose another move");
            return false;
        }
        return true;
    }

    public static boolean characterLimitCheck(int character) {
        if(character < 1 || character > 4) {
            System.out.println("Invalid character, choose again");
            return false;
        }
        return true;
    }

    public static void loadingEffect(String message) {
        System.out.print(message);
        try {
            for(int i = 0; i < 3; i++) {
                Thread.sleep(500);  // half-second delay
                System.out.print(".");
            }
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(); // move to next line after loading
    }

    public static int playerTurn(int move, int character, int botHealth) {
        if(move == 1) { //***Basic attack***
            if(character == 1) { //Warrior
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(80, 120));
            } else if(character == 2) { //Assassin
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(100, 180));
            } else if(character == 3) { //Mage
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(150, 250));
            } else { //Paladin
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(130, 210));
            }
        } else if(move == 2) { //***Slay***
            if(character == 1) { //Warrior
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(130, 180));
            } else if(character == 2) { //Assassin
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(180, 280));
            } else if(character == 3) { //Mage
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(200, 260));
            } else { //Paladin
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(210, 310));
            }
        } else if(move == 3) { //***Ability***
            if(character == 1) { //Warrior
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(200, 250));
            } else if(character == 2) { //Assassin
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(220, 260));
            } else if(character == 3) { //Mage
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(200, 250));
            } else { //Paladin
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(200, 230));
            }
        }
        return botHealth;
    }
}
