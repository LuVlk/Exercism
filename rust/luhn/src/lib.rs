/// Check a Luhn checksum.
pub fn is_valid(code: &str) -> bool {
    // todo!("validate input");

    let even_pos: &str = take_every_other(code.chars().rev(), false);
    let odd_pos: &str = take_every_other(code.chars().rev(), true);

    let even_pos_double_clamped = even_pos
        .as_bytes()
        .into_iter()
        .map(|b| *b - b'0')
        .map(|d| {
            match d * 2 {
                d2 if d2 > 9 => d2 - 9,
                d2 => d2
            }
        });
    
    let checksum = even_pos_double_clamped.sum() + odd_pos
        .as_bytes()
        .into_iter()
        .map(|b| *b - b'0')
        .sum();

    checksum % 10
}
