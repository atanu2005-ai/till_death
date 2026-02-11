import java.nio.channels.Pipe.SourceChannel;
import java.util.*;

public class till_death {
    public static void showRules() {
        System.out.println("Welcome to Till Death! A 1v1 battle simulation");
        System.out.println();
        System.out.println("Rules:");
        System.out.println("1) Both starts with 1000 HP.");
        System.out.println("Toss will dicide first move");
        System.out.println("2) You have 3 differnt attack modes, choose wisely as per limits!");
        System.out.println("3) You have an option to heal by 300 by only ONCE!");
        System.out.println("First player to hit 0 HP will loose lol :)");
    }
    public static void showMenu(int slayLeft, int abilityLeft, int healLeft) {
        System.out.println("Choose button:");
        System.out.println("/1 Basic");
        System.out.println("/2 slay" + "(Left: " + slayLeft + ")");
        System.out.println("/3 Ability" + "(Left: " + abilityLeft + ")");            
        System.out.println("/4 heal" + "(Left: " + healLeft + ")");
    }
    public static boolean LimitCheck(int move, int healLeft, int slayLeft, int abilityLeft) {
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
    public static int playerTurn(int move, int botHealth) {
        if(move == 1) {
            botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(120, 200));
        } else if(move == 2) {
            botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(150, 250));
        } else if(move == 3) {
            botHealth -= (java.util.concurrent.ThreadLocalRandom.current().nextInt(250, 300));
        }
        return botHealth;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        showRules(); //Showing rules
        System.out.print("Enter your name: "); //Player name input
        String name = sc.nextLine();
        System.out.println();
        int myHealth = 1000;
        int botHealth = 1000;

        boolean turn = true;
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
                if(!LimitCheck(move, healLeft, slayLeft, abilityLeft)) {
                    continue; //ask again
                }
                if(move == 2) {
                    slayLeft--;
                } else if(move == 3) {
                    abilityLeft--;
                } else if(move == 4 && healLeft == 1) {
                    myHealth = Math.min(myHealth + 300, 1000); //Healing, but not exceeding 1000
                    healLeft = 0; //Heal used
                }
                if(move != 4) { //If not healing, then attack
                    botHealth = playerTurn(move, botHealth);
                }
            }else { //Bot's turn
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
