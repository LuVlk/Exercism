const std = @import("std");
const heap = std.heap.page_allocator;

pub fn primes(buffer: []u32, limit: u32) []u32 {
    var sieve = heap.alloc(bool, limit + 1) catch {
        @panic("allocation failed");
    };
    defer heap.free(sieve);
    @memset(sieve, true);

    var prime_cntr: u32 = 0;
    var i: u32 = 2;

    while (i <= limit) : (i += 1) {
        if (!sieve[i]) continue;

        var j = i * i;
        while (j <= limit) : (j += i) sieve[j] = false;

        buffer[prime_cntr] = i;
        prime_cntr += 1;
    }

    return buffer[0..prime_cntr];
}
