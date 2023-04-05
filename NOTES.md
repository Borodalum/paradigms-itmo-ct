* Хочется, чтобы операции дабвлялись в одну строчку. Например, вот так:
```js
const Add = createOperation(2, '+', (a, b) => a + b);
const Negate = createOperation(1, 'negate', x => -x);
```
Не хочется прописывать одно и то же `func.apply(null, expressions)` для каждой операции
* Не надо использовать `.apply`, правильнее будет `func(...expressions)`

