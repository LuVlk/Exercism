adjacency_offsets = [
    (-1, -1), (-1, 0), (-1, 1),
    (0, -1),           (0, 1),
    (1, -1),  (1, 0),  (1, 1)
]

invalid_board_error = ValueError("The board is invalid with current input.")

def annotate(minefield):
    if len(minefield) == 0:
        return []
    
    width = len(minefield[0])
    if any([width != len(l) for l in minefield]):
        raise invalid_board_error

    return [
        annotate_line(minefield, idx, width) for idx in range(len(minefield))
    ]

def annotate_line(minefield, line_idx, width):
    return ''.join([
        annotate_char(minefield, line_idx, idx) for idx in range(width)
    ])

def annotate_char(minefield, line_idx, char_idx):
    c = minefield[line_idx][char_idx]
    if c == '*':
        return '*' 

    if c != ' ':
        raise invalid_board_error 
    
    cnt = 0
    for (dy, dx) in adjacency_offsets:
        if not 0 <= (line_idx + dy) < len(minefield):
            continue

        if not 0 <= (char_idx + dx) < len(minefield[line_idx]):
            continue

        if minefield[line_idx + dy][char_idx + dx] == '*':
            cnt += 1
        
    return str(cnt) if cnt > 0 else ' '