pub fn abbreviate(phrase: &str) -> String {
    phrase
        .split([' ', '-'])
        .map(remove_punctuation)
        .flat_map(split_camelcase)
        .filter_map(take_first_char)
        .collect::<String>()
        .to_uppercase()
}

fn split_camelcase(word: String) -> Vec<String> {
    return match word
        .chars()
        .enumerate()
        .skip_while(|(_, c)| c.is_uppercase())
        .find(|(_, c)| c.is_uppercase())
    {
        Some((pos, _)) => match word.split_at(pos) {
            ("", rest) => vec![rest.to_string()],
            (next, rest) => [next.to_string()]
                .into_iter()
                .chain(split_camelcase(rest.to_string()))
                .collect(),
        },
        None => vec![word],
    };
}

fn take_first_char(word: String) -> Option<char> {
    word.chars().next()
}

fn remove_punctuation(phrase: &str) -> String {
    phrase
        .chars()
        .filter(|c| !char::is_ascii_punctuation(c))
        .collect()
}
