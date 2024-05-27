use std::collections::HashMap;

pub struct CodonsInfo<'a> {
    codon_names: HashMap<&'a str, &'a str>,
    stop_codons: Vec<&'a str>,
}

impl<'a> CodonsInfo<'a> {
    pub fn name_for(&self, codon: &str) -> Option<&'a str> {
        self.codon_names.get(codon).copied()
    }

    pub fn of_rna(&self, rna: &str) -> Option<Vec<&'a str>> {
        if rna.is_empty() {
            return Some(vec![]);
        }

        if rna.chars().count() % 3 != 0
            && !self.stop_codons.iter().any(|codon| rna.contains(*codon))
        {
            return None;
        }

        let protein_names = rna
            .chars()
            .collect::<Vec<char>>()
            .chunks(3)
            .map(String::from_iter)
            .filter_map(|codon| self.name_for(&codon))
            .collect::<Vec<&'a str>>();

        (!protein_names.is_empty()).then_some(
            protein_names
                .into_iter()
                .take_while(|name| *name != "stop codon")
                .collect(),
        )
    }
}

pub fn parse<'a>(pairs: Vec<(&'a str, &'a str)>) -> CodonsInfo<'a> {
    CodonsInfo {
        stop_codons: pairs
            .iter()
            .filter(|(_, name)| *name == "stop codon")
            .map(|(codon, _)| *codon)
            .collect(),
        codon_names: HashMap::from_iter(pairs.into_iter()),
    }
}
