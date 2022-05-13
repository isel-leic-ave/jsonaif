# Enunciado do Trabalho 3 - `jsonaif` com suporte para sequencias _lazy_ (geradores `yield`) e _genéricos_

**Data limite de entrega: 6 de Junho**

## 1. Genéricos

Altere a API de `JsonParser` de modo a que a classe de domínio seja passada num
argumento de tipo (i.e. _genérico_) e evite a utilização explícita de casting (e.g.
operador `as`).

As utilizações apresentadas na primeira coluna do exemplo seguinte devem poder
ser reescritas na forma apresentada na segunda coluna:

<table>
<tr>
<td>

```kotlin
JsonParser parser = ...
val student = parser.parse(json, Student::class) as Student
val p = parser.parse(json, Person::class) as Person
val ps = parser.parse(json, Person::class) as List<Person>
```

</td>
<td>

```kotlin
JsonParser parser = ...
val student = parser.parse<Student>(json)
val p : Person = parser.parse(json)
val ps = parser.parseArray<Person>(json)
```

</td>
</tr>
</table>

**Note que a interface `JsonParser` passa a disponibilizar um novo método
público `parseArray` para além do `parse` já existente no Trabalho 1.**

## 2 - Sequencias _lazy_

1. Implemente um novo método `parseSequence(json: String): Sequence<T?>` que
retorna uma sequência _lazy_ para o _array_ JSON passado por parâmetro. Lance
uma excepção caso o parâmetro `json` não contenha a representação de um _array_.

2. Implemente um teste unitário que verifique o comportamento _lazy_ do método
`parseSequence`. Tire partido da anotação `@JsonConvert` para associar uma
função que permita verificar o momento em que os elementos da sequência são
produzidos.

Note que o novo método `parseSequence()` deve estar disponível a ambas as
implementações `JsonParserReflect` e `JsonParserDynamic`.

## 3. Sequencias _lazy_ (OPCIONAL)

1. Implemente dois novos método `parseFolderEager(path: String): List<T?>` e
`parseFolderLazy(path: String): Sequence<T?>` que retornam uma lista ou uma
sequência _lazy_, onde cada elemento é o resultado de aplicar `parseObject`
sobre o conteúdo de cada ficheiro presente na pasta `path`.

2. Implemente testes unitários que demonstrem que uma alteração de um ficheiro
no decorrer de uma iteração sobre o resultado do `parseFolder...` é visível, ou
não, consoante se use a abordagem _lazy_ ou _eager_.

Assuma que todos os ficheiros têm representações JSON de objectos do mesmo tipo.
Lance excepção no caso do objecto JSON ser incompatível com `T`.

