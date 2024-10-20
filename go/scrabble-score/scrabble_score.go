package scrabble

import "strings"

var scrabble_scores = map[rune]int{
	'A': 1, 'E': 1, 'I': 1, 'O': 1, 'U': 1, 'L': 1, 'N': 1, 'R': 1, 'S': 1, 'T': 1,
	'D': 2, 'G': 2,
	'B': 3, 'C': 3, 'M': 3, 'P': 3,
	'F': 4, 'H': 4, 'V': 4, 'W': 4, 'Y': 4,
	'K': 5,
	'J': 8, 'X': 8,
	'Q': 10, 'Z': 10,
}

func Score(word string) int {
	total_score := 0
	for _, letter := range strings.ToUpper(word) {
		if score, ok := scrabble_scores[letter]; ok {
			total_score += score
		}
	}
	return total_score
}

// goos: windows
// goarch: amd64
// pkg: scrabble
// cpu: 13th Gen Intel(R) Core(TM) i7-13700H
// BenchmarkScore-20         985086              1259 ns/op             104 B/op          9 allocs/op
// BenchmarkScore-20         852744              1273 ns/op             104 B/op          9 allocs/op
// BenchmarkScore-20        1000000              1201 ns/op             104 B/op          9 allocs/op
// BenchmarkScore-20         878496              1253 ns/op             104 B/op          9 allocs/op
// BenchmarkScore-20         964478              1190 ns/op             104 B/op          9 allocs/op
// PASS
// ok      scrabble        7.767s
