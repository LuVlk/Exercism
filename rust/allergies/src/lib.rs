use std::array::IntoIter;

pub struct Allergies {
    score: u32
}

#[derive(Debug, PartialEq, Eq, Clone, Copy)]
pub enum Allergen {
    Eggs = 1 << 0,
    Peanuts = 1 << 1,
    Shellfish = 1 << 2,
    Strawberries = 1 << 3,
    Tomatoes = 1 << 4,
    Chocolate = 1 << 5,
    Pollen = 1 << 6,
    Cats = 1 << 7,
}

impl Allergen {
    pub fn into_iter() -> IntoIter<Allergen, 8> {
        [
            Allergen::Eggs,
            Allergen::Peanuts,
            Allergen::Shellfish,
            Allergen::Strawberries,
            Allergen::Tomatoes,
            Allergen::Chocolate,
            Allergen::Pollen,
            Allergen::Cats
        ]
        .into_iter()
    }
}

impl Allergies {
    pub fn new(score: u32) -> Self {
        Allergies {
            score
        }
    }

    pub fn is_allergic_to(&self, allergen: &Allergen) -> bool {
        (*allergen as u32) & self.score != 0
    }

    pub fn allergies(&self) -> Vec<Allergen> {
        Allergen::into_iter()
        .filter(|allergie| self.is_allergic_to(allergie))
        .map(|a| a.to_owned())
        .collect()
    }
}
