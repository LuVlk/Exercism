use std::collections::HashSet;

fn sort_word(word: &str) -> String {
    let mut cvec = word.chars().collect::<Vec<_>>();
    cvec.sort_unstable();
    String::from_iter(cvec)
}

fn is_anagram(word: &str, other_word: &str) -> bool {
    other_word != word && sort_word(other_word) == sort_word(word)
}

pub fn anagrams_for<'a>(word: &str, possible_anagrams: &[&'a str]) -> HashSet<&'a str> {
    possible_anagrams
        .iter()
        .copied()
        .filter(|w| { is_anagram(&word.to_lowercase(), &w.to_lowercase()) })
        .collect()
}
