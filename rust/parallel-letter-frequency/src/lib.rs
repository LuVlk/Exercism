use std::{
    collections::HashMap,
    sync::mpsc,
    thread::{self},
};

pub fn frequency(input: &[&str], worker_count: usize) -> HashMap<char, usize> {
    if input.is_empty() {
        return HashMap::new();
    }

    let (tx, rx) = mpsc::channel();

    for chunk in input.chunks((input.len() as f64 / worker_count as f64).ceil() as usize) {
        let owned_chunk = chunk.iter().map(|&x| String::from(x)).collect::<Vec<_>>();
        let thread_tx = tx.clone();
        thread::spawn(move || {
            let char_frequency = case_insensitive_char_freq(owned_chunk);
            thread_tx.send(char_frequency).unwrap();
        });
    }
    drop(tx); // channel is closed when all senders are dropped. Drop "main" sender here

    let mut result = HashMap::new();

    for received in rx {
        for (c, count) in received {
            result
                .entry(c)
                .and_modify(|counter| *counter += count)
                .or_insert(count);
        }
    }

    result
}

pub fn case_insensitive_char_freq(texts: Vec<String>) -> HashMap<char, usize> {
    let mut map = HashMap::new();

    for line in texts {
        for chr in line.chars().filter(|c| c.is_alphabetic()) {
            if let Some(c) = chr.to_lowercase().next() {
                (*map.entry(c).or_insert(0)) += 1;
            }
        }
    }

    map
}
