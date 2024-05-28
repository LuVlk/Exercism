package listops

type IntList = slice[int]
type slice[T any] []T

func (s slice[T]) Foldl(fn func(T, T) T, initial T) T {
	if s.Length() == 0 {
		return initial
	}
	return s[1:].Foldl(fn, fn(initial, s[0]))
}

func (s slice[T]) Foldr(fn func(T, T) T, initial T) T {
	if s.Length() == 0 {
		return initial
	}
	max := s.Length() - 1
	return s[:max].Foldr(fn, fn(s[max], initial))
}

func (s slice[T]) Filter(fn func(T) bool) slice[T] {
	if s.Length() == 0 {
		return s
	}
	res := slice[T]{}
	if fn(s[0]) {
		res = res.Append(slice[T]{s[0]})
	}
	return res.Append(s[1:].Filter(fn))
}

func (s slice[T]) Length() (cnt int) {
	cnt = 0
	for range s {
		cnt += 1
	}
	return
}

func (s slice[T]) Map(fn func(T) T) slice[T] {
	if s.Length() == 0 {
		return s
	}
	return slice[T]{fn(s[0])}.Append(s[1:].Map(fn))
}

func (s slice[T]) Reverse() slice[T] {
	if s.Length() <= 1 {
		return s
	}
	return s[1:].Reverse().Append(slice[T]{s[0]})
}

func (s slice[T]) Append(lst slice[T]) slice[T] {
	if s.Length() == 0 {
		return lst
	}
	res := make(slice[T], s.Length()+lst.Length())
	copy(res[:s.Length()], s)
	copy(res[s.Length():], lst)
	return res
}

func (s slice[T]) Concat(lists []slice[T]) (res slice[T]) {
	res = s
	for _, list := range lists {
		res = res.Append(list)
	}
	return
}
