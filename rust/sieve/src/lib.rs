pub fn primes_up_to(upper_bound: usize) -> Vec<usize> {
    let sieve = sieve(upper_bound);

    let mut primes = vec![];

    for i in 2..=upper_bound {
        if sieve[i] {
            primes.push(i);
        }
    }

    primes
}

fn sieve(upper_bound: usize) -> Vec<bool> {
    let mut sieve = vec![true; upper_bound + 1];

    // mark all even numbers as non-prime except 2
    for i in (4..=upper_bound).step_by(2) {
        sieve[i] = false
    }

    // Optimization : traverse only upto square root of upper_bound
    // Optimization : Check numbers only upto square root of uppper_bound
    let mut i = 3;
    while i * i <= upper_bound {
        if sieve[i] {
            // Optimization : start marking at i^2
            // Optimization : mark only odd numbers because evens are already marked
            for j in (i * i..=upper_bound).step_by(2 * i) {
                sieve[j] = false
            }
        }

        i += 2;
    }
    sieve
}
