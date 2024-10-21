package pangram

import "strings"

func IsPangram(input string) bool {
	lowercase_input := strings.ToLower(input)

	for i := 0; i < 26; i++ {
		c := rune('a' + i)
		if !strings.ContainsRune(lowercase_input, c) {
			return false
		}
	}
	return true
}
