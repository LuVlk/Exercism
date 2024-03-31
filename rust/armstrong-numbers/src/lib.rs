pub fn is_armstrong_number(num: u32) -> bool {
    let mut splitter = num; 

    let digits = std::iter::from_fn(|| {
        if splitter != 0 {
            let digit = splitter % 10;
            splitter /= 10;
            Some(digit)
        } else {
            None
        }
    }).collect::<Vec<_>>();

    let n_digits = digits.len() as u32;

    return num as usize == digits.iter().map(|d| d.pow(n_digits) as usize).sum()
}
