# Zmiany w JAVIE 9
Repozytorium przedstawiające kilka zmian które zaszły w Javie 9
Dla każdej ze zmian zostały dodane testy przedstawiające dzialanie danej funkcjonalności

Zmiany przedstawione w repozytorium:
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
<T> Stream<T>	ofNullable(T t)
```
* TakeWhile
```
Stream<T> takeWhile(Predicate<? super T> predicate);
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
