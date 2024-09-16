package acronym

import (
	"strings"
)

// Abbreviate takes a string and returns its acronym.
//
// An acronym is a concatenation of the first letter of each word in
// the input string, with all letters capitalized.
//
// The function recognizes subsequent words by the presence of a space, hyphen, or
// underscore preceding the first letter of the word.
func Abbreviate(s string) string {
	var res []rune
	for i, r := range s {
		if ((r >= 'A' && r <= 'Z') || (r >= 'a' && r <= 'z')) && (i == 0 || s[i-1] == ' ' || s[i-1] == '-' || s[i-1] == '_') {
			res = append(res, r)
		}
	}
	return strings.ToUpper(string(res))
}

// bench
// goos: windows
// goarch: amd64
// pkg: acronym
// cpu: 13th Gen Intel(R) Core(TM) i7-13700H
// === RUN   BenchmarkAcronym
// BenchmarkAcronym
// BenchmarkAcronym-20
//   409398              2523 ns/op             448 B/op         32 allocs/op
