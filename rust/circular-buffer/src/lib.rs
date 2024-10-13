use std::collections::VecDeque;


pub struct CircularBuffer<T> {
    buffer: VecDeque<T>,
}

#[derive(Debug, PartialEq, Eq)]
pub enum Error {
    EmptyBuffer,
    FullBuffer,
}

impl<T> CircularBuffer<T> {
    pub fn new(capacity: usize) -> Self {
        CircularBuffer {
            buffer: VecDeque::with_capacity(capacity),
        }
    }

    fn is_full(&self) -> bool {
        self.buffer.capacity() == self.buffer.len()
    }

    pub fn write(&mut self, _element: T) -> Result<(), Error> {
        if self.is_full() {
            return Err(Error::FullBuffer);
        } 
        Ok(self.buffer.push_back(_element))
    }

    pub fn read(&mut self) -> Result<T, Error> {
        self.buffer.pop_front().ok_or(Error::EmptyBuffer)
    }

    pub fn clear(&mut self) {
        self.buffer.clear();
    }

    pub fn overwrite(&mut self, _element: T) {
        if self.is_full() {
            let _ = self.read();
        } 
        let _ = self.write(_element);
    }
}
