const std = @import("std");

pub const Category = enum {
    ones,
    twos,
    threes,
    fours,
    fives,
    sixes,
    full_house,
    four_of_a_kind,
    little_straight,
    big_straight,
    choice,
    yacht,
};

pub fn score(dice: [5]u3, category: Category) u32 {
    return switch (category) {
        .ones => score_num(dice, 1),
        .twos => score_num(dice, 2),
        .threes => score_num(dice, 3),
        .fours => score_num(dice, 4),
        .fives => score_num(dice, 5),
        .sixes => score_num(dice, 6),
        .full_house => full_house(dice),
        .four_of_a_kind => four_of_a_kind(dice),
        .little_straight => little_straight(dice),
        .big_straight => big_straight(dice),
        .choice => choice(dice),
        .yacht => yacht(dice),
    };
}

fn score_num(dice: [5]u3, num: u3) u32 {
    var out: u32 = 0;

    for (dice) |d| {
        if (d == num) out += d;
    }

    return out;
}

fn full_house(dice: [5]u3) u32 {
    var out: u32 = 0;

    var g1: u3 = 0;
    var g1Cnt: u3 = 0;
    var g2: u3 = 0;
    var g2Cnt: u3 = 0;

    for (dice) |d| {
        if (g1 == 0 or g1 == d) {
            g1 = d;
            g1Cnt += 1;
            out += d;
        }
        else if (g2 == 0 or g2 == d) {
            g2 = d;
            g2Cnt += 1;
            out += d;
        }
    }

    const cnts = .{g1Cnt, g2Cnt};
    if (std.meta.eql(cnts, .{2, 3}) or std.meta.eql(cnts, .{3, 2})) {
        return out;
    }

    return 0;
}

fn four_of_a_kind(dice: [5]u3) u32 {
    var out: u32 = 0;

    var sortable: [5]u3 = undefined;
    @memcpy(&sortable, &dice);
    std.mem.sort(u3, &sortable, {}, comptime std.sort.asc(u3));

    const relevant: []u3 = switch (sortable[0] == sortable[1]) {
        true => sortable[0..4],
        false => sortable[1..5]
    };

    const key: u3 = relevant[0];
    for (relevant) |d| {
        if (d != key) return 0;
        out += d;
    }

    return out;
}


fn little_straight(dice: [5]u3) u32 {

    var sorted: [5]u3 = undefined;
    @memcpy(&sorted, &dice);
    std.mem.sort(u3, &sorted, {}, comptime std.sort.asc(u3));

    if (std.meta.eql(sorted, .{1, 2, 3, 4, 5})) {
        return 30;
    }
    return 0;
}

fn big_straight(dice: [5]u3) u32 {

    var sorted: [5]u3 = undefined;
    @memcpy(&sorted, &dice);
    std.mem.sort(u3, &sorted, {}, comptime std.sort.asc(u3));

    if (std.meta.eql(sorted, .{2, 3, 4, 5, 6})) {
        return 30;
    }
    return 0;
}


fn choice(dice: [5]u3) u32 {
    var out: u32 = 0;
    for (dice) |d| {
        out += d;
    }
    return out;
}


fn yacht(dice: [5]u3) u32 {
    const key: u3 = dice[0];
    for (dice) |d| {
        if (d != key) return 0;
    }
    return 50;
}