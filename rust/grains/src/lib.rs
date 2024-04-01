#![feature(test)]
extern crate test;

pub fn square(s: u32) -> u64 {
    assert!((1..=64).contains(&s), "Square must be between 1 and 64");
    2u64.pow(s - 1)
}

pub fn total() -> u64 {
    u64::MAX
}

#[cfg(test)]
mod tests {
    use super::*;
    use test::Bencher;

    #[bench]
    fn one_bench(b: &mut Bencher) {
        b.iter(|| square(1));
    }

    #[bench]
    fn sixty_four_bench(b: &mut Bencher) {
        b.iter(|| square(64));
    }

    #[bench]
    fn total_bench(b: &mut Bencher) {
        b.iter(|| total());
    }

}