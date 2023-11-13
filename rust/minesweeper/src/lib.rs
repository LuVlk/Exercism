use std::ops::Range;

pub fn annotate(minefield: &[&str]) -> Vec<String> {
    let mut annotated_minefield: Vec<String> = Vec::new();

    for (y, line) in minefield.into_iter().enumerate() {
        let mut annotated_line = String::new();

        for (x, c) in line.as_bytes().into_iter().enumerate() {
            if *c == '*' as u8 {
                annotated_line.push('*');
                continue;
            }
            
            let adjacent_mines_count = get_adjacent_mines_count(minefield, y, x);
            
            let mut annotation: char = ' ';
            if adjacent_mines_count > 0 {
                assert!(adjacent_mines_count < 9);
                annotation = (adjacent_mines_count + b'0') as char;
            }

            annotated_line.push(annotation);
        }

        annotated_minefield.push(annotated_line);
    }

    annotated_minefield
}

fn get_adjacent_mines_count(minefield: &[&str], pos_y: usize, pos_x: usize) -> u8 {
    let mut mines_count: u8 = 0;

    let ys: Range<usize> = (if pos_y == 0 { 0 } else { pos_y - 1 })..(pos_y + 2);
    let xs: Range<usize> = (if pos_x == 0 { 0 } else { pos_x - 1 })..(pos_x + 2);

    for y in ys {
        for x in xs.clone() {
            if y == pos_y && x == pos_x {
                continue;
            }

            if let Some(adjacent_line) = minefield.into_iter().nth(y) {
                if let Some(adjacent_char) = adjacent_line.as_bytes().into_iter().nth(x) {
                    if *adjacent_char == '*' as u8 {
                        mines_count += 1
                    }
                }
            }
        }
    }

    mines_count
}
