package piglatin

import (
	"regexp"
	"strings"
)

func Sentence(text string) string {
	words := strings.Split(text, " ")
	res := make([]string, 0, len(words))
	for _, w := range words {
		res = append(res, word(w))
	}
	return strings.Join(res, " ")
}

var (
	beginVowelRe, _     = regexp.Compile(`^([aeiou].|xr|yt).+$`)
	beginConsonantRe, _ = regexp.Compile(`^([^aeiou]+)(.+)$`)
	quRe, _             = regexp.Compile(`^([^aeiou]*qu)(.+)$`)
	yVowelRe, _         = regexp.Compile(`^([^aeiou]+|.)(y.+)$`)
)

func word(word string) string {
	// Rule 1
	if beginVowelRe.MatchString(word) {
		return word + "ay"
	}
	// Rule 3
	if match := quRe.FindStringSubmatch(word); match != nil {
		return match[2] + match[1] + "ay"
	}
	// Rule 4
	if match := yVowelRe.FindStringSubmatch(word); match != nil {
		return match[2] + match[1] + "ay"
	}
	// Rule 2
	if match := beginConsonantRe.FindStringSubmatch(word); match != nil {
		return match[2] + match[1] + "ay"
	}
	return word
}

// $ go test -bench . -count=5 -benchmem
// goos: windows
// goarch: amd64
// pkg: piglatin
// cpu: 13th Gen Intel(R) Core(TM) i7-13700H
// BenchmarkSentence-20               67899             17395 ns/op            2808 B/op        101 allocs/op
// BenchmarkSentence-20               65150             18765 ns/op            2810 B/op        101 allocs/op
// BenchmarkSentence-20               55857             18942 ns/op            2805 B/op        101 allocs/op
// BenchmarkSentence-20               68449             16894 ns/op            2811 B/op        101 allocs/op
// BenchmarkSentence-20               66094             17262 ns/op            2806 B/op        101 allocs/op
// PASS
// ok      piglatin        7.776s
