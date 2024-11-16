import java.util.List;
import java.util.Random;

class DnDCharacter {

    Random _rand = new Random();

    int _intelligence = ability(rollDice());
    int _strength = ability(rollDice());
    int _dexterity = ability(rollDice());
    int _constitution = ability(rollDice());
    int _wisdom = ability(rollDice());
    int _charisma = ability(rollDice());

    int ability(List<Integer> scores) {
        return scores.stream().sorted().skip(1).mapToInt(Integer::intValue).sum();
    }

    List<Integer> rollDice() {
        return List.of(1, 1, 1, 1).stream().map(d -> d + _rand.nextInt(6)).toList();
    }

    int modifier(int input) {
        return Math.floorDiv((input - 10), 2);
    }

    int getStrength() {
        return _strength;
    }

    int getDexterity() {
        return _dexterity;
    }

    int getConstitution() {
        return _constitution;
    }

    int getIntelligence() {
        return _intelligence;
    }

    int getWisdom() {
        return _wisdom;
    }

    int getCharisma() {
        return _charisma;
    }

    int getHitpoints() {
        return 10 + modifier(getConstitution());
    }
}
