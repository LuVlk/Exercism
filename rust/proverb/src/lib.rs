use std::iter;

fn pairwise<I>(right: I) -> impl Iterator<Item = (Option<I::Item>, I::Item)>
where
    I: IntoIterator + Clone,
{
    let left = iter::once(None).chain(right.clone().into_iter().map(Some));
    left.zip(right)
}

pub fn build_proverb(list: &[&str]) -> String {
    let mut pairs = pairwise(list);
    let head = pairs.next().map(|(_, head)| head);

    pairs
        .flat_map(|(want, lost)| want.map(|w| (w, lost)))
        .fold(String::new(), |acc, (want, lost)| {
            acc + &format!("For want of a {} the {} was lost.\n", want, lost)
        }) 
        + &head.map(|h| format!("And all for the want of a {}.", h)).unwrap_or("".to_owned())
}
