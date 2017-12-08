# Zmiany w JAVIE 9
Repozytorium przedstawiające kilka zmian które zaszły w Javie 9
Dla każdej ze zmian zostały dodane testy przedstawiające dzialanie danej funkcjonalności

Zmiany przedstawione w repozytorium:
### Stream API ###
* DropWhile
```
Stream<T> dropWhile(Predicate<? super T> predicate)
```
np.
```
Stream.iterate(0, i -> i <= 10, i -> i + 2)
  .collect(Collectors.toList());
```
* Iterate
```
<T> Stream<T>	iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)
```
np.
```
List<String> collect = Stream.iterate("", s -> s.length() <= 5, s -> s + "s")
        .dropWhile(s -> s.length() <= 3)
        .collect(Collectors.toList());
```
* Stream Of Nullable
```
<T> Stream<T>	ofNullable(T t)
```
np.
```
assertThat(Stream.ofNullable("42").count()).isEqualTo(1);
assertThat(Stream.ofNullable(null).count()).isEqualTo(0);
```
* TakeWhile
```
Stream<T> takeWhile(Predicate<? super T> predicate);
```
np.
```
List<String> stringList = Stream.of("a", "b", "c", "", "e")
        .takeWhile(v -> !v.isEmpty())
        .collect(Collectors.toList());
```
### Factory Methods for Collections ###
Metody do tworzenia niemutowalnych kolekcji, które nie mogą przyjmować wartości null
* List
```
List<E> of(E... elements)
```
np.
```
List<Integer> integers = List.of(1, 2, 3);
```
* Set
```
Set<E> of(E... elements)
```
np.
```
Set<Integer> integers = Set.of(1, 2, 3);
```
* Map
```
<K,V> Map<K,V> of()
<K,V> Map<K,V> ofEntries​(Map.Entry<? extends K,? extends V>... entries)
```
np.
```
Map<String, String> stringMap = Map.of("hello", "world");
```
### Optional API ###
* ifPresentOrElse
```
void ifPresentOrElse(Consumer<? super T> action,Runnable emptyAction)
```
* Or
```
Optional<T> or​(Supplier<? extends Optional<? extends T>> supplier)
```
* Optional.stream()
```
Stream<T>	stream()	
If a value is present, returns a sequential Stream containing only that value, otherwise returns an empty Stream.
```
### Process API ###
* ProcessHandle 
* ProcessBuilder
* Runtime.version()
### Concurrent API ###
* CompletableFuture copy
* CompletableFuture completeOnTimeout
* CompletableFuture orTimeout
### Date API ###
* datesUntil
### Objects API ###
* checkIndex
* requireNonNullElseGet
np.
```
List<String> aList = Objects.requireNonNullElseGet(null, List::of);
    assertThat(aList).isNotEmpty();
```
* requireNonNullElse
### Other ###
* StackWalker
* Private method in Interface
* TryWithResources
