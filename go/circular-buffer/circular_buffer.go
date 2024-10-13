package circular

import (
	"errors"
)

type Buffer struct {
	data []byte
}

func NewBuffer(size int) *Buffer {
	return &Buffer{data: make([]byte, 0, size)}
}

func (b *Buffer) pop() byte {
	c := b.data[0]
	tail := make([]byte, len(b.data)-1, cap(b.data))
	copy(tail, b.data[1:])
	b.data = tail
	return c
}

func (b *Buffer) ReadByte() (byte, error) {
	if len(b.data) == 0 {
		return 0, errors.New("Buffer is empty")
	}
	return b.pop(), nil
}

func (b *Buffer) WriteByte(c byte) error {
	if len(b.data) == cap(b.data) {
		return errors.New("Buffer is full")
	}
	b.data = append(b.data, c)
	return nil
}

func (b *Buffer) Overwrite(c byte) {
	if len(b.data) == cap(b.data) {
		b.pop()
	}
	b.WriteByte(c)
}

func (b *Buffer) Reset() {
	b.data = make([]byte, 0, cap(b.data))
}

/*

goos: windows
goarch: amd64
pkg: standard
cpu: 13th Gen Intel(R) Core(TM) i7-13700H

=== RUN   BenchmarkOverwrite
BenchmarkOverwrite
BenchmarkOverwrite-20
17386164                71.41 ns/op     243472592.85 MB/s            111 B/op          0 allocs/op
PASS
ok      standard        1.379s

=== RUN   BenchmarkWriteRead
BenchmarkWriteRead
BenchmarkWriteRead-20
21257222                76.97 ns/op     276168292.25 MB/s            112 B/op          1 allocs/op
PASS
ok      standard        1.744s

*/
