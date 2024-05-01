package reverse

func Reverse(input string) string {
	out := ""
	for _, c := range input {
		out = string(c) + out
	}
	return out
}
