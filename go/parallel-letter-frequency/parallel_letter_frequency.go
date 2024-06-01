package letter

import (
	"sync"
)

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
	out := make(chan FreqMap, len(texts))
	wg := sync.WaitGroup{}

	for _, text := range texts {
		wg.Add(1)
		go func(text string) {
			out <- Frequency(text)
			wg.Done()
		}(text)
	}

	doneCombining := make(chan bool)
	result := FreqMap{}
	go func() {
		for res := range out {
			for key, val := range res {
				result[key] += val
			}
		}
		close(doneCombining)
	}()

	wg.Wait()
	close(out)
	<-doneCombining

	return result
}
