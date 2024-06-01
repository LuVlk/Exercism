package letter

// FreqMap records the frequency of each rune in a given text.
type FreqMap map[rune]int

// Frequency counts the frequency of each rune in a given text and returns this
// data as a FreqMap.
func Frequency(text string) FreqMap {
	frequencies := FreqMap{}
	for _, r := range text {
		frequencies[r]++
	}
	return frequencies
}

// ConcurrentFrequency counts the frequency of each rune in the given strings,
// by making use of concurrency.
func ConcurrentFrequency(texts []string) FreqMap {
	out := make(chan FreqMap)

	for _, text := range texts {
		go func(s string) { out <- Frequency(s) }(text)
	}

	result := FreqMap{}

	done := make(chan bool)
	go func() {
		for range texts {
			for key, val := range <-out {
				result[key] += val
			}
		}
		close(done)
	}()

	<-done
	close(out)

	return result
}
