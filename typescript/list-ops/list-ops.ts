// Do *not* construct any array literal ([]) in your solution.
// Do *not* construct any arrays through new Array in your solution.
// DO *not* use any of the Array.prototype methods in your solution.

// You may use the destructuring and spreading (...) syntax from Iterable.

export class List {

  private values: unknown[]

  constructor(values: unknown[]) {
    this.values = values
  }

  public static create(...values: unknown[]): List {
    return new List(values)
  }

  public forEach(callback: (e: unknown) => void): void {
    for (let index = 0; index < this.length(); index++) {
      callback(this.values[index]);
    }
  }

  public append(list: List): List {
    list.forEach(element => {
      this.values.push(element);
    });
    return this
  }

  public concat(listOfLists: List): List {
    listOfLists.forEach(list => {
      this.append(list as List);
    });
    return this
  }
  
  public filter<T>(predicate: (e: T) => boolean): List {
    this.forEach(e => {
      if (!predicate(e as T)) {
        let index = this.values.indexOf(e)
        this.values.splice(index, 1)
      }
    })
    return this
  }

  public length(): number {
    let count = 0
    this.values.forEach(_ => count++)
    return count
  }

  public map<T>(transform: (e: T) => T): List {
    for (let index = 0; index < this.values.length; index++) {
      this.values[index] = transform(this.values[index] as T);
    }
    return this
  };

  public foldl<T, U>(f: (acc: U, e: T) => U, initial: U): U {
    let acc = initial
    this.forEach(e => acc = f(acc, e as T))
    return acc
  };
  
  public foldr<T, U>(f: (acc: U, e: T) => U, initial: U): U {
    let acc = initial
    this.reverse()
    this.forEach(e => acc = f(acc, e as T))
    return acc
  };

  public reverse(): List {
    for (let index = 0; index < this.length() / 2; index++) {
      let tmp = this.values[index]
      let indexToSwapWith = this.length() - 1 - index;
      this.values[index] = this.values[indexToSwapWith]
      this.values[indexToSwapWith] = tmp
    }
    return this
  }
}