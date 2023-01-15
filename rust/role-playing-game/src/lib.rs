// This stub file contains items that aren't used yet; feel free to remove this module attribute
// to enable stricter warnings.
#![allow(unused)]

pub struct Player {
    pub health: u32,
    pub mana: Option<u32>,
    pub level: u32,
}

impl Player {
    pub fn revive(&self) -> Option<Player> {
        if self.health == 0 {
            return Some(Player {
                health: 100,
                mana: match self.mana {
                    None => { None }
                    Some(_) => { Some(100) }
                },
                level: self.level})
        }
        None
    }

    pub fn cast_spell(&mut self, mana_cost: u32) -> u32 {
        match self.mana {
            Some(mana) if mana > mana_cost => {
               self.mana = Some(mana - mana_cost); mana_cost * 2
            }
            None => {
                if mana_cost > self.health {
                    self.health = 0
                }
                else { self.health -= mana_cost }
                0
            }
            _ => { 0 }
        }
    }
}
