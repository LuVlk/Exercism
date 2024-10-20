const std = @import("std");

pub fn primes(buffer: []u32, comptime limit: u32) []u32 {
    var sieve = [_]bool{true} ** (limit + 1);
    var prime_cntr: u32 = 0;

    var i: u32 = 2;
    while (i <= limit) {
        if (sieve[i]) {
            var j = i * i;
            while (j <= limit) {
                sieve[j] = false;
                j += i;
            }

            buffer[prime_cntr] = i;
            prime_cntr += 1;
        }

        i += 1;
    }

    return buffer[0..prime_cntr];
}
