import java.nio.channels.Pipe.SourceChannel;
import java.util.*;

public class till_death {
    public static void showRules() {
        System.out.println("Welcome to Till Death! A 1v1 battle simulation");
        System.out.println();
        System.out.println("Rules:");
        System.out.println("1) Bot has 1000 HP while you have 800-1200 HP depending on your character choice");
        System.out.println("2) You have 3 differnt attack modes, choose wisely as per limits!");
        System.out.println("3) You have an option to heal by 300 by only ONCE!");
        System.out.println("You have 4 different characters to choose from");
        System.out.println("Toss will decide who goes first");
        System.out.println("First player to hit 0 HP will loose lol :)");
    }
    public static void showMenu(int slayLeft, int abilityLeft, int healLeft) {
        System.out.println("Choose button:");
        System.out.println("/1 Basic");
        System.out.println("/2 slay" + "(Left: " + slayLeft + ")");
        System.out.println("/3 Ability" + "(Left: " + abilityLeft + ")");            
        System.out.println("/4 heal" + "(Left: " + healLeft + ")");
    }
    public static void ShowCharacter() {
        System.out.println("1/ Warrior");
        System.out.println("2/ Assassin");
        System.out.println("3/ Mage");
        System.out.println("4/ Paladin");
    }
    public static boolean MoveLimitCheck(int move, int healLeft, int slayLeft, int abilityLeft) {
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
    public static boolean CharacterLimitCheck(int character) {
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
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(80, 150));
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
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(250, 350));
            } else { //Paladin
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(210, 310));
            }
        } else if(move == 3) { //***Ability***
            if(character == 1) { //Warrior
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(200, 280));
            } else if(character == 2) { //Assassin
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(250, 300));
            } else if(character == 3) { //Mage
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(250, 300));
            } else { //Paladin
                botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(250, 300));
            }
        }
        return botHealth;
    }
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
        ShowCharacter(); 
        int character = sc.nextInt(); //Character choice input
        loadingEffect("Choosing character");
        while(!CharacterLimitCheck(character)) {
            character = sc.nextInt(); //Ask again if invalid
        }
        int myHealth = 0;
        if(character == 1) {
            myHealth = 1200; //Warrior
        }else if(character == 2) {
            myHealth = 800; //Assassin
        }else if(character == 3) {
            myHealth = 900; //Mage
        }else {
            myHealth = 1000; //Paladin
        }

        int botHealth = 1000;
        loadingEffect("Flipping the coin");
        Random rand = new Random();
        boolean turn = rand.nextBoolean();

        if(turn) {
            System.out.println(name + " won the toss and will go first!");
        } else {
            System.out.println("Bot won the toss and will go first!");
        }
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
                if(!MoveLimitCheck(move, healLeft, slayLeft, abilityLeft)) {
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
                myHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(200, 260));
            }
            //check if game is over
            if(myHealth <= 0) { //If bot wins
                System.out.println("________________________________");
                System.out.println(name + "'s Health: " + 0);
                System.out.println("Bot's Health: " + botHealth);
                System.out.println("________________________________");
                System.out.println("Bot won the game");
                break;
            }else if(botHealth <= 0) { //If player wins
                System.out.println("________________________________");
                System.out.println(name + "'s Health: " + myHealth);
                System.out.println("Bot's Health: " + 0);
                System.out.println("________________________________");
                System.out.println(name + ", you won the game");
                break;
            }
            turn = !turn;
        }
        sc.close();
    }
}
