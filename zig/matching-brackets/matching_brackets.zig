const std = @import("std");
const mem = std.mem;

pub fn isBalanced(allocator: mem.Allocator, s: []const u8) !bool {
    _ = allocator;
    if (s.len == 0) return true;
    return findMatchingBracketsWithRespectToInnerPairs(s, null) != null;
}

fn findMatchingBracketsWithRespectToInnerPairs(s: []const u8, match: ?u8) ?usize {

    if (s.len == 0) return null;

    var i: usize = 0;
    while (i <= s.len-1) : (i += 1) {
        if (match) |c| {
            if (s[i] == c) {
                return i;
            }
        }

        if (s[i] == '{') {
            if (findMatchingBracketsWithRespectToInnerPairs(s[i+1..], '}')) |found| {
                i += found+1;
            } else {
                return null;
            }
        }
        else if (s[i] == '[') {
            if (findMatchingBracketsWithRespectToInnerPairs(s[i+1..], ']')) |found| {
                i += found+1;
            } else {
                return null;
            }
        }
        else if (s[i] == '(') {
            if (findMatchingBracketsWithRespectToInnerPairs(s[i+1..], ')')) |found| {
                i += found+1;
            } else {
                return null;
            }
        }
        else if (s[i] == '}' or s[i] == ']' or s[i] == ')') {
            return null;
        }
    }

    if (match == null) {
        return 0;
    }
    return null;
}
