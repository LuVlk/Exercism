fn pling_sound(n: u32) -> Option<String> {
    if n % 3 == 0 { Some("Pling".into()) } else { None }
}

fn plang_sound(n: u32) -> Option<String> {
    if n % 5 == 0 { Some("Plang".into()) } else { None }
}

fn plong_sound(n: u32) -> Option<String> {
    if n % 7 == 0 { Some("Plong".into()) } else { None }
}

const SOUND_PRODUCERS: [fn(u32) -> Option<String>; 3]  = [
    pling_sound,
    plang_sound,
    plong_sound
];

pub fn raindrops(n: u32) -> String {

    let dropsound = SOUND_PRODUCERS.iter()
        .filter_map(|produce_sound| produce_sound(n))
        .reduce(|acc, sound| {
            acc + &sound
        });

    match dropsound {
        Some(sound) => sound,
        None => n.to_string()
    }
}
