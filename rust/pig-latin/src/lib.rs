static RULES: [(fn(&str) -> bool, fn(&str) -> String); 4] = [
    // If a word begins with a vowel, or starts with `"xr"` or `"yt"`,
    // add an `"ay"` sound to the end of the word.
    (
        |input: &str| {
            input.starts_with(['a', 'e', 'i', 'o', 'u'])
                || input.starts_with("xr")
                || input.starts_with("yt")
        },
        |input: &str| input.to_string() + "ay",
    ),
    // If a word starts with zero or more consonants followed by `"qu"`,
    // first move those consonants (if any) and the `"qu"` part to the end of the word,
    // and then add an `"ay"` sound to the end of the word.
    (
        |input: &str| {
            input
                .find("qu")
                .is_some_and(|i| input[..i].chars().all(|c| is_consonant(&c)))
        },
        |input: &str| {
            let qu_index = input.find("qu").expect("illegal action applied");
            input[qu_index + 2..].to_string() + &input[..qu_index + 2] + "ay"
        },
    ),
    // If a word starts with one or more consonants followed by `"y"`,
    // first move the consonants preceding the `"y"`to the end of the word,
    // and then add an `"ay"` sound to the end of the word.
    (
        |input: &str| {
            !input.starts_with(['a', 'e', 'i', 'o', 'u', 'y'])
                && input
                    .chars()
                    .skip_while(|c| is_consonant(c) && *c != 'y')
                    .next()
                    .is_some_and(|c| c == 'y')
        },
        |input: &str| {
            let y_index = input.find('y').expect("illegal action applied");
            input[y_index..].to_string() + &input[..y_index] + "ay"
        },
    ),
    // If a word begins with a one or more consonants,
    // first move those consonants to the end of the word and then add an `"ay"` sound to the end of the word.
    (
        |input: &str| !input.starts_with(['a', 'e', 'i', 'o', 'u']),
        |input: &str| {
            let first_vowel_index = input
                .char_indices()
                .find(|(_, c)| is_vowel(c))
                .map(|(i, _)| i)
                .unwrap_or(input.len() - 1);

            input[first_vowel_index..].to_string() + &input[..first_vowel_index] + "ay"
        },
    ),
];

fn is_vowel(c: &char) -> bool {
    matches!(c, 'a' | 'e' | 'i' | 'o' | 'u')
}

fn is_consonant(c: &char) -> bool {
   !is_vowel(c)
}

fn translate_word(input: &str) -> String {
    for (predicate, action) in RULES {
        if predicate(input) {
            return action(input);
        }
    }
    input.to_string()
}

pub fn translate(input: &str) -> String {
    input.split_ascii_whitespace().map(translate_word).collect::<Vec<_>>().join(" ")
}
