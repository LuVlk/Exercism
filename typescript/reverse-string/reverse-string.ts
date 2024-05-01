export function reverse(input: string): string {
  let out: string = ""
  for (let i = input.length - 1; i >= 0; i--) {
    out += input[i]
  }
  return out
}
