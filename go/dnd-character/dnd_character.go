package dndcharacter

import (
	"math"
	"math/rand"
	"slices"
)

type Character struct {
	Strength     int
	Dexterity    int
	Constitution int
	Intelligence int
	Wisdom       int
	Charisma     int
	Hitpoints    int
}

// Modifier calculates the ability modifier for a given ability score
func Modifier(score int) int {
	return int(math.Floor((float64(score) - 10.0) / 2))
}

// Ability uses randomness to generate the score for an ability
func Ability() int {
	rolls := make([]int, 4)

	for i := range rolls {
		rolls[i] = rand.Intn(6) + 1
	}

	slices.Sort(rolls)

	score := 0
	for _, roll := range rolls[1:] {
		score += roll
	}

	return score
}

// GenerateCharacter creates a new Character with random scores for abilities
func GenerateCharacter() Character {
	c := Character{
		Strength:     Ability(),
		Dexterity:    Ability(),
		Constitution: Ability(),
		Intelligence: Ability(),
		Wisdom:       Ability(),
		Charisma:     Ability(),
	}

	c.Hitpoints = 10 + Modifier(c.Constitution)

	return c
}

// go test -bench . -count=5 -benchmem
// goos: windows
// goarch: amd64
// pkg: dnd-character
// cpu: 13th Gen Intel(R) Core(TM) i7-13700H
// BenchmarkModifier-20            1000000000               0.8045 ns/op          0 B/op          0 allocs/op
// BenchmarkModifier-20            1000000000               0.7506 ns/op          0 B/op          0 allocs/op
// BenchmarkModifier-20            1000000000               0.7304 ns/op          0 B/op          0 allocs/op
// BenchmarkModifier-20            1000000000               0.7414 ns/op          0 B/op          0 allocs/op
// BenchmarkModifier-20            1000000000               0.9272 ns/op          0 B/op          0 allocs/op
// BenchmarkAbility-20             11838008                96.14 ns/op            0 B/op          0 allocs/op
// BenchmarkAbility-20             12659923                91.91 ns/op            0 B/op          0 allocs/op
// BenchmarkAbility-20             12805080                91.19 ns/op            0 B/op          0 allocs/op
// BenchmarkAbility-20             10400541               113.5 ns/op             0 B/op          0 allocs/op
// BenchmarkAbility-20             12040744               111.0 ns/op             0 B/op          0 allocs/op
// BenchmarkCharacter-20            1843574               614.3 ns/op             0 B/op          0 allocs/op
// BenchmarkCharacter-20            2020203               635.6 ns/op             0 B/op          0 allocs/op
// BenchmarkCharacter-20            1828016               668.9 ns/op             0 B/op          0 allocs/op
// BenchmarkCharacter-20            1875812               679.9 ns/op             0 B/op          0 allocs/op
// BenchmarkCharacter-20            1953675               688.9 ns/op             0 B/op          0 allocs/op
// PASS
// ok      dnd-character   20.395s
