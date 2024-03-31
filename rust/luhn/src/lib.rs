/// Check a Luhn checksum.
pub fn is_valid(code: &str) -> bool {

    let reverse_trimmed_bytes = code
        .as_bytes()
        .into_iter()
        .rev()
        .filter(|b| !b.is_ascii_whitespace());

    if reverse_trimmed_bytes.clone().count() <= 1 {
        return false
    }

    if !reverse_trimmed_bytes.clone().all(|c| c.is_ascii_digit()) {

        return false

    } else {

        let even_pos_bytes = take_every_other(reverse_trimmed_bytes.clone(), false);
        let even_pos_sum: u32 = even_pos_bytes
            .map(|b| *b - b'0')
            .map(|d| {
                match d * 2 {
                    d2 if d2 > 9 => d2 - 9,
                    d2 => d2
                }
            })
            .fold(0, |agg, e| { agg + e as u32});

        let odd_pos_bytes = take_every_other(reverse_trimmed_bytes.clone(), true);
        let odd_pos_sum: u32 = odd_pos_bytes
            .map(|b| *b - b'0')
            .fold(0, |agg, e| { agg + e as u32});
        
        return ( even_pos_sum + odd_pos_sum ) % 10 == 0
    }


}

fn take_every_other<T>(iter: impl Iterator<Item = T>, include_first: bool) -> impl Iterator<Item = T>
{
    iter.skip(if include_first { 0 } else { 1 }).step_by(2)
}