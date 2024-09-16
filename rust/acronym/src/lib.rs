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
    if let Some((pos, _)) = word
        .chars()
        .enumerate()
        .skip_while(|(_, c)| c.is_uppercase())
        .find(|(_, c)| c.is_uppercase())
    {
        match word.split_at(pos) {
            ("", rest) => return vec![rest.to_string()],
            (next, rest) => {
                return [next.to_string()]
                    .into_iter()
                    .chain(split_camelcase(rest.to_string()).into_iter())
                    .collect()
            }
        };
    } else {
        return vec![word];
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
