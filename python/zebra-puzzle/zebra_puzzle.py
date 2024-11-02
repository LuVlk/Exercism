# based on: https://exercism.org/tracks/python/exercises/zebra-puzzle/solutions/paiv

import itertools

ppl = 'Norwegian Englishman Ukrainian Spaniard Japanese'.split()

def drinks_water():
    x,_ = next(solve())
    return ppl[x]

def owns_zebra():
    _,x = next(solve())
    return ppl[x]

def solve():
    for solution in ((water, zebra)
        for (red, green, ivory, yellow, blue) in itertools.permutations(range(5))
        if green - ivory == 1 # 6. The green house is immediately to the right of the ivory house. 
        for (norway, english, ukraine, spain, japan) in itertools.permutations(range(5))
        if norway == 0 # 10. The Norwegian lives in the first house.
        if english == red # 2. The Englishman lives in the red house.
        for (dog, fox, snails, horse, zebra) in itertools.permutations(range(5))
        if spain == dog # 3. The Spaniard owns the dog.
        for (coffee, tea, milk, orange, water) in itertools.permutations(range(5))
        if coffee == green # 4. The person in the green house drinks coffee.
        if ukraine == tea # 5. The Ukrainian drinks tea.
        if milk == 2 # 9. The person in the middle house drinks milk.
        for (painter, dancing, reading, football, chess) in itertools.permutations(range(5))
        if dancing == snails # 7. The snail owner likes to go dancing.
        if painter == yellow # 8. The person in the yellow house is a painter.
        if abs(reading - fox) == 1 # 11. The person who enjoys reading lives in the house next to the person with the fox.
        if abs(painter - horse) == 1 # 12. The painter's house is next to the house with the horse.
        if football == orange # 13. The person who plays football drinks orange juice.
        if chess == japan # 14. The Japanese person plays chess.
        if abs(norway - blue) == 1 # 15. The Norwegian lives next to the blue house.
        ):
        yield solution

if __name__ == '__main__':
    print(list(solve()))