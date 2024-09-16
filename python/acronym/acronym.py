def abbreviate(words: str) -> str:
    """
    Takes a string of words and returns their acronym.

    The acronym is made up of the first letter of each word.
    If a word starts with a non-alphabetic character, it is ignored.
    If a word is separated from the previous one by a hyphen, underscore or space,
    it is considered a new word and its first letter is included in the acronym.
    """
    return "".join([c.upper() for (i, c) in enumerate(words) if c.isalpha() and (i == 0 or words[i - 1] in ' -_')])

