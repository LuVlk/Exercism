#[derive(Debug, PartialEq, Eq)]
pub enum Comparison {
    Equal,
    Sublist,
    Superlist,
    Unequal,
}

pub fn sublist<T: PartialEq>(_first_list: &[T], _second_list: &[T]) -> Comparison {
    if _first_list.len() > _second_list.len() {
        if _second_list.len() == 0 {
           return Comparison::Superlist
        }

        for w in _first_list.windows(_second_list.len()) {
            if w == _second_list {
                return Comparison::Superlist
            }
        }
    }

    else if _first_list.len() < _second_list.len() {
        if _first_list.len() == 0 {
            return Comparison::Sublist
        }

        for w in _second_list.windows(_first_list.len()) {
            if w == _first_list {
                return Comparison::Sublist
            }
        }
    }

    else if _first_list == _second_list {
        return Comparison::Equal
    }

    Comparison::Unequal
}
