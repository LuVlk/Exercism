package piglatin

import (
	"bytes"
	"strings"
	"unicode"
)

func Sentence(sentence string) string {
	var buffer bytes.Buffer

	for _, word := range strings.Split(sentence, " ") {
		buffer.WriteString(translateWord(word))
		buffer.WriteRune(' ')
	}

	buffer.Truncate(buffer.Len() - 1)

	return buffer.String()
}

func translateWord(word string) string {
	for _, rule := range rules {
		if rule.Predicate(word) {
			return rule.Translate(word)
		}
	}

	return word
}

func isVowel(c rune) bool {
	return strings.ContainsRune(vowels, unicode.ToLower(c))
}

const vowels = "aeiou"

func isConsonant(c rune) bool {
	return !isVowel(c)
}

var rules []Rule = []Rule{
	Rule1{},
	Rule3{},
	Rule4{},
	Rule2{},
}

type Rule interface {
	Predicate(word string) bool
	Translate(word string) string
}

type Rule1 struct {
	Rule
}

// If a word begins with a vowel, or starts with `"xr"` or `"yt"`
func (r Rule1) Predicate(word string) bool {
	return isVowel([]rune(word)[0]) || strings.HasPrefix(word, "xr") || strings.HasPrefix(word, "yt")
}

// Add an `"ay"` sound to the end of the word
func (r Rule1) Translate(word string) string {
	return word + "ay"
}

type Rule2 struct {
	Rule
}

// If a word begins with a one or more consonants
func (r Rule2) Predicate(word string) bool {
	return isConsonant([]rune(word)[0])
}

// First move those consonants to the end of the word and then add an `"ay"` sound to the end of the word.
func (r Rule2) Translate(word string) string {
	idx := 0
	for i, c := range word {
		idx = i
		if !isConsonant(c) {
			break
		}
	}

	return word[idx:] + word[:idx] + "ay"
}

type Rule3 struct {
	Rule
}

// If a word starts with zero or more consonants followed by `"qu"`
func (r Rule3) Predicate(word string) bool {
	i := strings.Index(strings.ToLower(word), "qu")

	if i == -1 {
		return false
	}

	for _, c := range word[:i] {
		if !isConsonant(c) {
			return false
		}
	}

	return true
}

// First move those consonants (if any) and the `"qu"` part to the end of the word, and then add an `"ay"` sound to the end of the word.
func (r Rule3) Translate(word string) string {
	quIdx := strings.Index(strings.ToLower(word), "qu")

	if quIdx == -1 {
		panic("qu not found")
	}

	return word[(quIdx+2):] + word[:(quIdx+2)] + "ay"
}

type Rule4 struct {
	Rule
}

// If a word starts with one or more consonants followed by `"y"`
func (r Rule4) Predicate(word string) bool {
	i := strings.Index(strings.ToLower(word), "y")

	if i == -1 || i == 0 {
		return false
	}

	for _, c := range word[:i] {
		if !isConsonant(c) {
			return false
		}
	}

	return true
}

// First move the consonants preceding the `"y"`to the end of the word, and then add an `"ay"` sound to the end of the word.
func (r Rule4) Translate(word string) string {
	yIdx := strings.Index(strings.ToLower(word), "y")

	if yIdx == -1 {
		panic("y not found")
	}

	return word[yIdx:] + word[:yIdx] + "ay"
}

// $ go test -bench . -count=5 -benchmem
// goos: windows
// goarch: amd64
// pkg: piglatin
// cpu: 13th Gen Intel(R) Core(TM) i7-13700H
// BenchmarkSentence-20              215292              6932 ns/op            2184 B/op         90 allocs/op
// BenchmarkSentence-20              167683              7309 ns/op            2184 B/op         90 allocs/op
// BenchmarkSentence-20              136690              7547 ns/op            2184 B/op         90 allocs/op
// BenchmarkSentence-20              149104              7230 ns/op            2184 B/op         90 allocs/op
// BenchmarkSentence-20              157342              7665 ns/op            2184 B/op         90 allocs/op
// PASS
// ok      piglatin        7.416s
