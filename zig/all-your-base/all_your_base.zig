const std = @import("std");
const mem = std.mem;

pub const ConversionError = error{
    InvalidInputBase,
    InvalidOutputBase,
    InvalidDigit,
};

/// Converts `digits` from `input_base` to `output_base`, returning a slice of digits.
/// Caller owns the returned memory.
pub fn convert(
    allocator: mem.Allocator,
    digits: []const u32,
    input_base: u32,
    output_base: u32,
) (mem.Allocator.Error || ConversionError)![]u32 {
    const n = try fromDigits(digits, input_base);
    return toDigits(allocator, n, output_base);
}

fn fromDigits(digits: []const u32, input_base: u32) (ConversionError)!u32 {
    if (input_base < 2) return ConversionError.InvalidInputBase;

    var n: u32 = 0;
    for (digits) |d| {
        if (!(d >= 0 and d < input_base)) return ConversionError.InvalidDigit;
        n = n * input_base + d;
    }
    return n;
}

fn toDigits(allocator: mem.Allocator, number: u32, output_base: u32) (mem.Allocator.Error || ConversionError)![]u32 {
    if (output_base < 2) return ConversionError.InvalidOutputBase;

    var exp: u32 = 0;
    while (std.math.pow(u32, output_base, exp) <= number) {
        exp += 1;
    }

    var digits = try std.ArrayList(u32).initCapacity(allocator, exp);
    defer digits.deinit();

    var n = number;
    for (0..digits.capacity) |_| {
        digits.insertAssumeCapacity(0, n % output_base);
        n /= output_base;
    }

    if (exp == 0) {
        try digits.append(0);
    }

    return try digits.toOwnedSlice();
}
