import java.util.Random;

public class Main {
    public static int bossHealth = 1000;
    public static int bossDamage = 100;
    public static String bossDefence;
    public static boolean bossStunned;
    public static int[] heroesHealth = {250, 260, 270, 220, 350, 210, 200, 200};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 15, 10, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Thor", "Witcher"};
    public static int roundNumber = 0;
    //added heroes index
    public static int medicIndex = 3;
    public static int golemIndex = 4;
    public static int luckyIndex = 5;
    public static int thorIndex = 6;
    public static int witcherIndex = 7;
    public static int medicHeal = 40;
    public static int witcherRevive = 1;


    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2, 4
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (bossStunned) {
                System.out.println("Boss missed attack");
                bossStunned = false;
                break;
            }
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {

                    heroesHealth[i] = 0;

                } else {
                    Random random = new Random();
                    boolean luckyChance = random.nextBoolean();
                    if (i == luckyIndex && luckyChance) {
                        System.out.println("Lucky dodged: " + bossDamage + ' ' + luckyChance);
                        continue;
                    }
                    if (heroesHealth[golemIndex] > bossDamage) {
                        heroesHealth[golemIndex] -= bossDamage * 0.2;
                        heroesHealth[i] -= bossDamage * 0.8; // heroesHealth[i] = heroesHealth[i] - bossDamage;
                        System.out.println("Golem protected: " + heroesAttackType[i] + " from " + bossDamage * 0.2);
                    } else {
                        heroesHealth[i] -= bossDamage;
                    }
                }
            } else {
                if (i != witcherIndex && witcherRevive > 0) {
                    heroesHealth[i] = heroesHealth[witcherIndex];
                    heroesHealth[witcherIndex] = 0;
                    System.out.println("Witcher gave him life to " + heroesAttackType[i]);
                    witcherRevive -= 1;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (i == thorIndex) {
                    Random random = new Random();
                    boolean stun = random.nextBoolean();
                    if (stun) {
                        System.out.println("Boss stunned");
                        bossStunned = stun;
                    }
                }
                if (heroesAttackType[i] == heroesAttackType[medicIndex]) {
                    for (int j = 0; j < heroesHealth.length; j++) {
                        if (heroesHealth[j] > 0 && heroesHealth[j] < 100 && heroesHealth[j] != heroesHealth[medicIndex]) {
                            heroesHealth[j] += medicHeal;
                            System.out.println("Medic heal: " + heroesAttackType[j] + " " + medicHeal);
                            break;
                        }
                    }
                }
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage; // bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}
