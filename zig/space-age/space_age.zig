pub const Planet = enum {
    mercury,
    venus,
    earth,
    mars,
    jupiter,
    saturn,
    uranus,
    neptune,

    pub fn age(self: Planet, seconds: usize) f64 {
        return @as(f64, @floatFromInt(seconds)) / 31_557_600.00 / orbital_periods[@intFromEnum(self)];
    }
};

const orbital_periods = [8]f64{
    0.2408467,
    0.61519726,
    1.0,
    1.8808158,
    11.862615,
    29.447498,
    84.016846,
    164.79132
};
