const mem = @import("std").mem;
const std = @import("std");

const Rule = struct {
    predicate: *const fn (word: []const u8) bool,
    action: *const fn (w: std.io.FixedBufferStream([]u8).Writer, word: []const u8) std.io.FixedBufferStream([]u8).WriteError!void,
};

const Rule1 = struct {

   pub fn init() Rule {
        return Rule {
            .predicate = predicate,
            .action = action
        };
   }

    // If a word begins with a vowel, or starts with `"xr"` or `"yt"`
    fn predicate(word: []const u8) bool {
        return isVowel(word[0]) or startsWithAnyOfIgnoreCase(word, &.{ "xr", "yt" });
    }

    // add an `"ay"` sound to the end of the word
    fn action(w: std.io.FixedBufferStream([]u8).Writer, word: []const u8) std.io.FixedBufferStream([]u8).WriteError!void {
        _ = try w.print("{s}ay", .{ word });
    }

};

const Rule2 = struct {

   pub fn init() Rule {
        return Rule {
            .predicate = predicate,
            .action = action
        };
   }

    // If a word begins with one or more consonants
    fn predicate(word: []const u8) bool {
        return isConsonant(word[0]);
    }

    // first move those consonants to the end of the word and then add an `"ay"` sound to the end of the word.
    fn action(w: std.io.FixedBufferStream([]u8).Writer, word: []const u8) std.io.FixedBufferStream([]u8).WriteError!void {
        var c_idx: usize = 0;
        while (isConsonant(word[c_idx]) and c_idx < word.len) {
            c_idx += 1;
        }

        _ = try w.print("{s}{s}ay", .{ word[c_idx..], word[0..c_idx] });
    }

};

const Rule3 = struct {

   pub fn init() Rule {
        return Rule {
            .predicate = predicate,
            .action = action
        };
   }

    // If a word starts with zero or more consonants followed by `"qu"`
    fn predicate(word: []const u8) bool {
        var c_idx: usize = 0;
        while (isConsonant(word[c_idx]) and (c_idx + 1) < word.len) {
            if (std.ascii.startsWithIgnoreCase(word[c_idx..c_idx+2], "qu")) return true;
            c_idx += 1;
        }

        return false;
    }

    // first move those consonants (if any) and the `"qu"` part to the end of the word, and then add an `"ay"` sound to the end of the word.
    fn action(w: std.io.FixedBufferStream([]u8).Writer, word: []const u8) std.io.FixedBufferStream([]u8).WriteError!void {
        const qu_idx = std.mem.indexOf(u8, word, "qu");

        if (qu_idx) |i| {
            _ = try w.print("{s}{s}ay", .{ word[(i+2)..], word[0..(i + 2)]});
        }
        else {
            @panic("'qu' not found in word.");
        }
    }

};

const Rule4 = struct {

   pub fn init() Rule {
        return Rule {
            .predicate = predicate,
            .action = action
        };
   }

    // If a word starts with one or more consonants followed by `"y"`
    fn predicate(word: []const u8) bool {
        var c_idx: usize = 0;
        while (isConsonant(word[c_idx]) and (c_idx + 1) <= word.len) {
            if (word[c_idx+1] == 'y') return true;
            c_idx += 1;
        }

        return false;
    }

    // first move the consonants preceding the `"y"`to the end of the word, and then add an `"ay"` sound to the end of the word.
    fn action(w: std.io.FixedBufferStream([]u8).Writer, word: []const u8) std.io.FixedBufferStream([]u8).WriteError!void {
        const y_idx = std.mem.indexOf(u8, word, "y");

        if (y_idx) |i| {
            _ = try w.print("{s}{s}ay", .{ word[i..], word[0..i]});
        }
        else {
            @panic("'y' not found in word.");
        }
    }

};

fn startsWithAnyOfIgnoreCase(haystack: []const u8, needles: []const []const u8) bool {
    for (needles) |needle| {
        if (std.ascii.startsWithIgnoreCase(haystack, needle)) return true;
    }
    return false;
}

fn isVowel(c: u8) bool {
    return switch (std.ascii.toLower(c)) {
        'a', 'e', 'i', 'o', 'u' => true,
        else => false
    };
}

fn isConsonant(c: u8) bool {
    return !isVowel(c);
}

const RULES = [4]Rule{
    Rule1.init(),
    Rule3.init(),
    Rule4.init(),
    Rule2.init()
};

fn translateWord(w: std.io.FixedBufferStream([]u8).Writer, word: []const u8) std.io.FixedBufferStream([]u8).WriteError!void {
    for (RULES) |rule| {
        if (rule.predicate(word)) {
            return rule.action(w, word);
        }
    }
    _ = try w.print("{s}", .{ word });
}

pub fn translate(allocator: mem.Allocator, phrase: []const u8) mem.Allocator.Error![]u8 {
    const buf = try allocator.alloc(u8, phrase.len * 2);
    var fbs = std.io.fixedBufferStream(buf);
    defer if (!allocator.resize(buf, fbs.getWritten().len)) { 
        @panic("unable to resize buffer"); 
    };

    var words = mem.splitAny(u8, phrase, " ");
    while (words.next()) |word| {
        translateWord(fbs.writer(), word) catch {
            @panic("translated word exceeded buffer size");
        };
        if (words.peek() != null) {
            _ = fbs.write(" ") catch {
                @panic("whitespace exceeded buffer size");
            };  
        }
    }
    
    return fbs.getWritten();
}
