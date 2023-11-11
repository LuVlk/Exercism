// The code below is a stub. Just enough to satisfy the compiler.
// In order to pass the tests you can add-to or change any of this code.

#[derive(Debug)]
pub struct Duration(u64);

impl Duration {
    
    fn years(&self) -> f64 {
        self.0 as f64 / (60.0 * 60.0 * 24.0 * 365.25)
    }
    
}

impl From<u64> for Duration {
    fn from(s: u64) -> Self {
        Duration(s)
    }
}

pub trait Planet {
    fn years_during(d: &Duration) -> f64;
}

use planet_derive::Planet;

#[derive(Planet)]
pub struct Earth;

#[derive(Planet)]
#[earth_years = 0.2408467]
pub struct Mercury;

#[derive(Planet)]
#[earth_years = 0.61519726]
pub struct Venus;

#[derive(Planet)]
#[earth_years = 1.8808158]
pub struct Mars;

#[derive(Planet)]
#[earth_years = 11.862615]
pub struct Jupiter;

#[derive(Planet)]
#[earth_years = 29.447498]
pub struct Saturn;

#[derive(Planet)]
#[earth_years = 84.016846]
pub struct Uranus;

#[derive(Planet)]
#[earth_years = 164.79132]
pub struct Neptune;
