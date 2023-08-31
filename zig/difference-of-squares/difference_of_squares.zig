pub fn squareOfSum(number: usize) usize {
    var i: usize = 0;
    var acc: usize = 0;
    while (i <= number) : (i += 1) {
        acc += i;
    }
    return acc * acc;
}

pub fn sumOfSquares(number: usize) usize {
    var i: usize = 0;
    var acc: usize = 0;
    while (i <= number) : (i += 1) {
        acc += i * i;
    }
    return acc;
}

pub fn differenceOfSquares(number: usize) usize {
    return squareOfSum(number) - sumOfSquares(number);
}
