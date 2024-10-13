package circular

import (
	"errors"
)

type Buffer struct {
	data []byte
	size int
}

func NewBuffer(size int) *Buffer {
	return &Buffer{data: make([]byte, 0, size), size: size}
}

func (b *Buffer) ReadByte() (byte, error) {
	if len(b.data) == 0 {
		return 0, errors.New("Buffer is empty")
	}
	c := b.data[0]
	b.data = b.data[1:]
	return c, nil
}

func (b *Buffer) WriteByte(c byte) error {
	if len(b.data) == b.size {
		return errors.New("Buffer is full")
	}
	b.data = append(b.data, c)
	return nil
}

func (b *Buffer) Overwrite(c byte) {
	if len(b.data) == b.size {
		b.data = b.data[1:]
	}
	b.WriteByte(c)
}

func (b *Buffer) Reset() {
	b.data = make([]byte, 0, b.size)
}

/*

goos: windows
goarch: amd64
pkg: standard
cpu: 13th Gen Intel(R) Core(TM) i7-13700H

=== RUN   BenchmarkOverwrite
BenchmarkOverwrite
BenchmarkOverwrite-20
349730750                4.152 ns/op    84228690196.28 MB/s            1 B/op          0 allocs/op
PASS
ok      standard        1.881s

=== RUN   BenchmarkWriteRead
BenchmarkWriteRead
BenchmarkWriteRead-20
215245246                5.795 ns/op    37143302142.77 MB/s            1 B/op          0 allocs/op
PASS
ok      standard        1.902s

*/
