package sieve

func Sieve(limit int) []int {

	var sieve []bool = make([]bool, limit+1)

	for i := 2; i <= limit; i++ {
		sieve[i] = true
	}

	for i := 2; i <= limit; i++ {
		if sieve[i] {
			for j := i * 2; j <= limit; j += i {
				sieve[j] = false
			}
		}
	}

	var primes []int
	for i := 2; i <= limit; i++ {
		if sieve[i] {
			primes = append(primes, i)
		}
	}

	return primes
}

// goos: windows
// goarch: amd64
// pkg: sieve
// cpu: 13th Gen Intel(R) Core(TM) i7-13700H
// BenchmarkSieve-20         202861              5457 ns/op            5336 B/op         22 allocs/op
// BenchmarkSieve-20         209544              6337 ns/op            5336 B/op         22 allocs/op
// BenchmarkSieve-20         196344              7006 ns/op            5336 B/op         22 allocs/op
// BenchmarkSieve-20         163245              7299 ns/op            5336 B/op         22 allocs/op
// BenchmarkSieve-20         179966              6188 ns/op            5336 B/op         22 allocs/op
// PASS
// ok      sieve   8.464s
