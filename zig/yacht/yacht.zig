const std = @import("std");

pub const Category = enum(u4) {
    ones = 1,
    twos = 2,
    threes = 3,
    fours = 4,
    fives = 5,
    sixes = 6,
    full_house,
    four_of_a_kind,
    little_straight,
    big_straight,
    choice,
    yacht,
};

fn sum(dice: [5]u3) u32 {
    return @as(u32, dice[0]) + dice[1] + dice[2] + dice[3] + dice[4];
}

pub fn score(dice: [5]u3, category: Category) u32 {
    var sorted: [5]u3 = undefined;
    @memcpy(&sorted, &dice);
    std.mem.sort(u3, &sorted, {}, std.sort.asc(u3));

    return switch (category) {
        .ones, .twos, .threes, .fours, .fives, .sixes => @truncate(@intFromEnum(category) * std.mem.count(u3, &sorted, &[_]u3{@truncate(@intFromEnum(category))})),

        .little_straight => if (std.mem.eql(u3, &[_]u3{ 1, 2, 3, 4, 5 }, &sorted)) 30 else 0,

        .big_straight => if (std.mem.eql(u3, &[_]u3{ 2, 3, 4, 5, 6 }, &sorted)) 30 else 0,

        .choice => sum(dice),

        .four_of_a_kind => if (sorted[1] == sorted[4] or sorted[0] == sorted[3]) @as(u32, 4) * sorted[1] else 0,

        .yacht => if (sorted[0] == sorted[4]) return 50 else 0,

        .full_house => if (sorted[0] == sorted[1] and ((sorted[1] == sorted[2]) != (sorted[2] == sorted[3])) and sorted[3] == sorted[4]) sum(dice) else 0,
    };
}