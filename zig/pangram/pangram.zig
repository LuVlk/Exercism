const std = @import("std");
const palloc = std.heap.page_allocator;

pub fn isPangram(str: []const u8) bool {
    var set = std.bit_set.IntegerBitSet(26).initEmpty();

    for (str) |c| {
        if (!std.ascii.isAlphabetic(c)) continue;
        set.set(std.ascii.toLower(c) - 'a');
    }

    return set.count() == 26;
}
