package pangram

import "strings"

func IsPangram(input string) bool {
	lowercase_input := strings.ToLower(input)

	for c := 'a'; c <= 'z'; c++ {
		if !strings.ContainsRune(lowercase_input, c) {
			return false
		}
	}
	return true
}
