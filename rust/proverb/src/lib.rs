pub fn build_proverb(list: &[&str]) -> String {
    let want = list.into_iter();
    let mut lost = list.into_iter();
    let head = lost.next();

    want.zip(lost).fold(String::new(), |acc, (want, lost)| {
        acc + &format!("For want of a {} the {} was lost.\n", want, lost)
    }) + &head
        .map(|h| format!("And all for the want of a {}.", h))
        .unwrap_or("".to_owned())
}
