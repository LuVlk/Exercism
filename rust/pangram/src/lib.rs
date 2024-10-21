use std::collections::HashSet;

/// Determine whether a sentence is a pangram.
pub fn is_pangram(sentence: &str) -> bool {
    let unique_chars = sentence
        .chars()
        .filter(char::is_ascii_alphabetic)
        .map(|c| c.to_ascii_lowercase())
        .fold(HashSet::new(), |mut set, c| {
            set.insert(c);
            set
        });

    unique_chars.len() == 26
}
