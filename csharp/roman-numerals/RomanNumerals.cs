public static class RomanNumeralExtension
{
    public static string ToRoman(this int value) => value switch
    {
        0 => "",

        >= 1000 => $"{new string('M', value / 1000)}{ToRoman(value % 1000)}",
        >= 900 and < 1000 => $"CM{ToRoman(value - 900)}",
        >= 500 => $"{new string('D', value / 500)}{ToRoman(value % 500)}",
        >= 400 and < 500 => $"CD{ToRoman(value - 400)}",
        >= 100 => $"{new string('C', value / 100)}{ToRoman(value % 100)}",
        >= 90 and < 100 => $"XC{ToRoman(value - 90)}",
        >= 50 => $"{new string('L', value / 50)}{ToRoman(value % 50)}",
        >= 40 and < 50 => $"XL{ToRoman(value - 40)}",
        >= 10 => $"{new string('X', value / 10)}{ToRoman(value % 10)}",
        9 => "IX",
        >= 5 => $"{new string('V', value / 5)}{ToRoman(value % 5)}",
        4 => "IV",
        _ => new string('I', value)
    };
}