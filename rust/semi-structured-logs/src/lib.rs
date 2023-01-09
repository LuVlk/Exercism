// This stub file contains items that aren't used yet; feel free to remove this module attribute
// to enable stricter warnings.
// #![allow(unused)]

use std::fmt::{Display, Formatter};

/// various log levels
#[derive(Clone, PartialEq, Eq, Debug)]
pub enum LogLevel {
    Info,
    Warning,
    Error,
    #[cfg(feature = "add-a-variant")]
    Debug
}

impl Display for LogLevel {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        match self {
            LogLevel::Info => {write!(f, "INFO")}
            LogLevel::Warning => {write!(f, "WARNING")}
            LogLevel::Error => {write!(f, "ERROR")}
            #[cfg(feature = "add-a-variant")]
            LogLevel::Debug => {write!(f, "DEBUG")}
        }
    }
}
/// primary function for emitting logs
pub fn log(level: LogLevel, message: &str) -> String {
    format!("[{level}]: {message}")
}
pub fn info(message: &str) -> String {
    log(LogLevel::Info, message)
}
pub fn warn(message: &str) -> String {
    log(LogLevel::Warning, message)
}
pub fn error(message: &str) -> String {
    log(LogLevel::Error, message)
}
