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

macro_rules! planets {
    { $( $name:ident { earth_years = $earth_years:expr } ),+ } => {
        $(
            pub struct $name;
            impl Planet for $name {
                fn years_during(d: &Duration) -> f64 {
                    d.years() / $earth_years
                }
            }
        )+
    };
}

planets! {
    Earth { earth_years = 1.0 },
    Mercury { earth_years = 0.2408467 },
    Venus { earth_years = 0.61519726 },
    Mars { earth_years = 1.8808158 },
    Jupiter { earth_years = 11.862615 },
    Saturn { earth_years = 29.447498 },
    Uranus { earth_years = 84.016846 },
    Neptune { earth_years = 164.79132 }
}
