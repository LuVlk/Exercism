package acronym

import (
	"regexp"
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
	r := regexp.MustCompile(`(^|\s|-|_)[a-zA-Z]`)
	matches := r.FindAllString(s, -1)

	res := ""
	for _, m := range matches {
		res += strings.ToUpper(strings.Trim(m, " -_"))
	}
	return res
}
