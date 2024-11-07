package minesweeper

import "strconv"

// Annotate returns an annotated board
func Annotate(board []string) []string {
	for line_idx, line := range board {
		for c_idx, c := range line {
			if c == '*' {
				continue
			}

			cnt := 0
			// search for mines in the line, the line before and the line after
			for i := line_idx - 1; i <= line_idx+1; i++ {
				if i < 0 || i == len(board) {
					continue
				}

				if board[i][c_idx] == '*' {
					cnt++
				}

				if c_idx != 0 && board[i][c_idx-1] == '*' {
					cnt++
				}

				if c_idx != len(line)-1 && board[i][c_idx+1] == '*' {
					cnt++
				}
			}

			if cnt != 0 {
				line = line[:c_idx] + strconv.Itoa(cnt) + line[c_idx+1:]
			}
		}
		board[line_idx] = line
	}

	return board
}

// $ go test -bench . -count=5 -benchmem
// goos: windows
// goarch: amd64
// pkg: minesweeper
// cpu: 13th Gen Intel(R) Core(TM) i7-13700H
// BenchmarkAnnotate-20              960000              1402 ns/op             216 B/op         27 allocs/op
// BenchmarkAnnotate-20              827408              1543 ns/op             216 B/op         27 allocs/op
// BenchmarkAnnotate-20              722073              1721 ns/op             216 B/op         27 allocs/op
// BenchmarkAnnotate-20              742808              1727 ns/op             216 B/op         27 allocs/op
// BenchmarkAnnotate-20              692055              1499 ns/op             216 B/op         27 allocs/op
// PASS
// ok      minesweeper     7.152
