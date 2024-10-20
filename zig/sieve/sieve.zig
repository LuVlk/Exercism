const std = @import("std");

pub fn primes(buffer: []u32, comptime limit: u32) []u32 {
    var sieve = std.StaticBitSet(limit + 1).initFull();
    var prime_cntr: u32 = 0;
    var i: u32 = 2;

    while (i <= limit) : (i += 1) {
        if (!sieve.isSet(i)) continue;

        var j = i * i;
        while (j <= limit) : (j += i) sieve.unset(j);

        buffer[prime_cntr] = i;
        prime_cntr += 1;
    }

    return buffer[0..prime_cntr];
}
