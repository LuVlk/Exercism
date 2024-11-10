package dndcharacter

import (
	"math"
	"math/rand"
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
	return rand.Intn(15) + 3
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
// BenchmarkModifier-20            1000000000               0.7715 ns/op          0 B/op          0 allocs/op
// BenchmarkModifier-20            1000000000               0.7596 ns/op          0 B/op          0 allocs/op
// BenchmarkModifier-20            1000000000               0.7682 ns/op          0 B/op          0 allocs/op
// BenchmarkModifier-20            1000000000               0.7677 ns/op          0 B/op          0 allocs/op
// BenchmarkModifier-20            1000000000               0.7455 ns/op          0 B/op          0 allocs/op
// BenchmarkAbility-20             100000000               10.03 ns/op            0 B/op          0 allocs/op
// BenchmarkAbility-20             121148352               11.48 ns/op            0 B/op          0 allocs/op
// BenchmarkAbility-20             100000000               12.70 ns/op            0 B/op          0 allocs/op
// BenchmarkAbility-20             80351941                12.79 ns/op            0 B/op          0 allocs/op
// BenchmarkAbility-20             85541368                12.74 ns/op            0 B/op          0 allocs/op
// BenchmarkCharacter-20           13280917                84.30 ns/op            0 B/op          0 allocs/op
// BenchmarkCharacter-20           14098663                85.14 ns/op            0 B/op          0 allocs/op
// BenchmarkCharacter-20           13807021                79.99 ns/op            0 B/op          0 allocs/op
// BenchmarkCharacter-20           13413320                83.57 ns/op            0 B/op          0 allocs/op
// BenchmarkCharacter-20           12936066                83.47 ns/op            0 B/op          0 allocs/op
// PASS
// ok      dnd-character   18.249s
