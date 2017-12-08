# java9
Repozytorium przedstawiające kilka zmian które zaszły w Javie 9
Dla każdej ze zmian zostały dodane testy przedstawiające dzialanie danej funkcjonalności

### Stream API ###
* DropWhile
```
Stream<T> dropWhile(Predicate<? super T> predicate)
```
* Iterate
```
<T> Stream<T>	iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)
```
* Stream Of Nullable
```
<T> Stream<T>	ofNullable​(T t)
```
* TakeWhile
```
Stream<T> takeWhile(Predicate<? super T> predicate);
```

