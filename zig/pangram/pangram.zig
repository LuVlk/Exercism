const std = @import("std");
const palloc = std.heap.page_allocator;

pub fn isPangram(str: []const u8) bool {
    var map = std.AutoHashMap(u8, bool).init(palloc);
    for (str) |c| {
        if (std.ascii.isAlphabetic(c)) {
            map.put(std.ascii.toLower(c), false) catch {
                @panic("allocation failed");
            };
        }
    }
    return map.count() == 26;
}
