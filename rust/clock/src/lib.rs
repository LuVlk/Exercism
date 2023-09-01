use std::fmt::{Display, Formatter};

#[derive(Debug,PartialEq,Eq)]
pub struct Clock {
    hours: i32,
    minutes: i32
}

impl Display for Clock {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        write!(f, "{:02}:{:02}", self.hours, self.minutes)
    }
}

impl Clock {
    pub fn new(hours: i32, minutes: i32) -> Self {
        let mut h = (hours + minutes / 60) % 24;
        let mut m = minutes % 60;

        if m < 0 {
            h -= 1;
            m += 60;
        }
        if h < 0 { h += 24 }

        Clock { hours: h, minutes: m }
    }

    pub fn add_minutes(&self, minutes: i32) -> Self {
        Clock::new(self.hours, self.minutes + minutes)
    }
}
