package sieve

func Sieve(limit int) []int {
	if limit < 2 {
		return []int{}
	}

	var sieve []bool = make([]bool, limit+1)

	// Optimization: only mark odds as prime candidates
	for i := 3; i <= limit; i += 2 {
		sieve[i] = true
	}

	// Optimization : traverse only upto square root of limit
	// Optimization : Check numbers only upto square root of limit
	i := 3
	for i*i <= limit {

		if sieve[i] {
			// Optimization : start marking at i^2. Every multiple below that is already unmarked
			// Optimization : unmark only odd numbers because evens are already unmarked
			for j := i * i; j <= limit; j += 2 * i {
				sieve[j] = false
			}
		}

		i += 2
	}

	var primes []int = []int{2}
	for i := 3; i <= limit; i++ {
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
// BenchmarkSieve-20         367741              3332 ns/op            5336 B/op         21 allocs/op
// BenchmarkSieve-20         356944              3334 ns/op            5336 B/op         21 allocs/op
// BenchmarkSieve-20         313124              4322 ns/op            5336 B/op         21 allocs/op
// BenchmarkSieve-20         225589              5073 ns/op            5336 B/op         21 allocs/op
// BenchmarkSieve-20         245997              4414 ns/op            5336 B/op         21 allocs/op
// PASS
// ok      sieve   6.266s

// benchstat
//          │  .\old.txt   │              .\new.txt              │
//          │    sec/op    │    sec/op     vs base               │
// Sieve-20   6.337µ ± ∞ ¹   4.322µ ± ∞ ¹  -31.80% (p=0.008 n=5)
// ¹ need >= 6 samples for confidence interval at level 0.95

//          │   .\old.txt   │            .\new.txt             │
//          │     B/op      │     B/op       vs base           │
// Sieve-20   5.211Ki ± ∞ ¹   5.211Ki ± ∞ ¹  ~ (p=1.000 n=5) ²
// ¹ need >= 6 samples for confidence interval at level 0.95
// ² all samples are equal

//          │  .\old.txt  │             .\new.txt             │
//          │  allocs/op  │  allocs/op   vs base              │
// Sieve-20   22.00 ± ∞ ¹   21.00 ± ∞ ¹  -4.55% (p=0.008 n=5)
// ¹ need >= 6 samples for confidence interval at level 0.95
