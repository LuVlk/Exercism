pub fn primes_up_to(upper_bound: usize) -> Vec<usize> {
    let mut primes = Vec::new();
    let mut sieve = vec![true; upper_bound + 1];

    for i in 2..=upper_bound {
        if sieve[i] {
            for j in (2 * i..=upper_bound).step_by(i) {
                sieve[j] = false
            }
            primes.push(i);
        }
    }

    primes
}
