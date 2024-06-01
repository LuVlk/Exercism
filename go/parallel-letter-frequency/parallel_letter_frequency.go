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
	in := make(chan string, len(texts))
	wg := sync.WaitGroup{}

	numWorkers := len(texts)
	for i := 0; i < numWorkers; i++ {
		wg.Add(1)
		go func(i int) {
			for text := range in {
				out <- Frequency(text)
			}
			wg.Done()
		}(i)
	}

	for _, text := range texts {
		in <- text
	}
	close(in)

	wg.Wait()
	close(out)

	result := FreqMap{}
	for res := range out {
		for key, val := range res {
			result[key] += val
		}
	}
	return result
}
