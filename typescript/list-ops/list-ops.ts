// Do *not* construct any array literal ([]) in your solution.
// Do *not* construct any arrays through new Array in your solution.
// DO *not* use any of the Array.prototype methods in your solution.

// You may use the destructuring and spreading (...) syntax from Iterable.

export class List {

  constructor(readonly values: unknown[]) { }

  public static create(...values: unknown[]): List {
    return new List(values)
  }

  public forEach(callback: (e: unknown) => void): void {
    for (const item of this.values) {
      callback(item);
    }
  }

  public append(list: List): List {
    return List.create(...this.values, ...list.values)
  }

  public concat(listOfLists: List): List {
    return listOfLists.foldl((acc: List, curr: List) => acc.append(curr), List.create(...this.values))
  }
  
  public filter<T>(predicate: (e: T) => boolean): List {
    return this.foldr((acc: List, curr: T) => predicate(curr) ? List.create(curr, ...acc.values) : acc, List.create()) 
  }

  public length(): number {
    return this.foldr((acc: number, _: any) => acc + 1, 0)
  }

  public map<T>(transform: (e: T) => T): List {
    return this.foldr((acc: List, curr: T) => List.create(transform(curr), ...acc.values), List.create())
  };

  public foldl<T, U>(f: (acc: U, e: T) => U, acc: U): U {
    this.forEach(e => acc = f(acc, e as T))
    return acc
  };
  
  public foldr<T, U>(f: (acc: U, e: T) => U, acc: U): U {
    this.reverse()
    this.forEach(e => acc = f(acc, e as T))
    return acc
  };

  public reverse(): List {
    for (let index = 0; index < this.values.length / 2; index++) {
      let tmp = this.values[index]
      let indexToSwapWith = this.values.length - 1 - index;
      this.values[index] = this.values[indexToSwapWith]
      this.values[indexToSwapWith] = tmp
    }
    return this
  }
}