const rand = @import("std").Random;
const time = @import("std").time;
const math = @import("std").math;

pub fn modifier(score: i8) i8 {
    const fscore: f16 = @floatFromInt(score);
    return @intFromFloat(math.floor((fscore - 10) / 2));
}

fn seed() u32 {
    const ts: u128 = @bitCast(time.nanoTimestamp());
    return @truncate(ts);
}

pub fn ability() i8 {
    var prng = rand.DefaultPrng.init(seed());
    return @intCast(prng.random().uintLessThan(u8, 15) + 3);
}

pub const Character = struct {
    strength: i8,
    dexterity: i8,
    constitution: i8,
    intelligence: i8,
    wisdom: i8,
    charisma: i8,
    hitpoints: i8,

    pub fn init() Character {
        var c = Character{ .strength = ability(), .dexterity = ability(), .constitution = ability(), .intelligence = ability(), .wisdom = ability(), .charisma = ability(), .hitpoints = 0 };
        c.hitpoints = 10 + modifier(c.constitution);
        return c;
    }
};
