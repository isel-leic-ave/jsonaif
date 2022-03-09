# Enunciado do Trabalho 1 - `jsonaif` via reflect

**Data limite de entrega: 11 de Abril**

Use como base do seu trabalho o projecto gradle disponível no repositório
`jsonaif`.

Pretende-se desenvolver a biblioteca `jsonaif` para processamento de dados em
formato JSON (https://www.json.org/).
Esta biblioteca disponibiliza um objecto `JsonParserReflect` que pode ser usado
para transformar uma _string_ JSON numa instância de uma classe de domínio
compatível (e.g. `Student`) conforme ilustrado no exemplo seguinte:

```kotlin
val json = "{ name: \"Ze Manel\", nr: 7353}"
val student = JsonParserReflect.parse(json, Student::class) as StudentWithFields
assertEquals("Ze Manel", student.name)
assertEquals(7353, student.nr)
```

A class `JsonParserReflect` usa uma instância de uma classe auxiliar
`JsonTokens` para percorrer os elementos da _String_ JSON fonte.
O algoritmo de `JsonParserReflect` é recursivo, criando instâncias de classes de
domínio, ou uma lista, e preenchendo os seus campos, ou elementos, com valores
primitivos ou instâncias de outras classes de domínio, ou listas, e assim
sucessivamente.
Ambas as classes são fornecidas no projecto `jsonaif`.

## Parte 1

Implemente os métodos `parsePrimitive` e `parseObject` de `JsonParserReflect` de
modo a ter o comportamento desejado e satisfazer os testes unitários
disponibilizados no projecto `jsonaif`.
Pode adicionar novos métodos auxiliares.

A classe `JsonTokens` não deve ser modificada.

**Nota**: o nome das propriedades JSON pode ser definido indiferentemente em
maiúsculas ou minúsculas.
A classe de domínio correspondente não pode ter propriedades com nomes que se
distingam entre si apenas por terem letras maiúsculas ou minúsculas.

Mantenha uma estrutura de dados com instâncias de `Setter` para cada classe de
domínio de modo a que não seja repetido o trabalho de leitura de metadata via
Reflexão.
Por exemplo, no parsing de um array de `Student` as propriedades a serem
afectadas só devem ser procuradas 1 vez.

A interface `Setter` especifica a forma de afectação de uma determinada
propriedade no parâmetro `target` a partir do valor obtido do parâmetro
`tokens`:
```kotlin
interface Setter {
    fun apply(target: Any, tokens: JsonTokens)
}
```

Por exemplo, na estrutura de dados seguinte cada classe de domínio é mapeada num
conjunto de pares: nome da propriedade - `Setter`

```kotlin
val setters = mutableMapOf<KClass<*>, Map<String, Setter>>()
```

`JsonParserReflect` deve suportar duas formas de instanciar a classe de domínio:

1. Através da chamada ao construtor sem parâmetros, ou que tem todos os
   parâmetros opcionais
   (https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect.full/create-instance.html).
   Posteriormente afecta cada um das propriedades vindas da string JSON. E.g. de
   data class

2. Chamando um construtor com parâmetros.. E.g. de `data class...` com
   propriedades `var`.

A implementação de `parseObject`deve dar prioridade à opção 1, sempre que possível.

Implemente mais testes unitários incluindo por exemplo entidades de domínio como:

* `Classroom` que agrega um conjunto de instâncias de `Student`
* `Account` com um saldo (_balance_) e um conjunto de movimentos de conta
  (_transactions_)
* Um outro exemplo ao seu critério.

## Parte 2

Pretende-se que as propriedades da classe de domínio possam ter nomes distintos
dos nomes usados na representação em JSON.
Por exemplo, uma propriedade em JSON pode ter o nome `birth_date`  e em Kotlin
`birthDate`. Para resolver a correspondência entre propriedades de nome distinto
implemente uma anotação `JsonProperty` que possa ser usada sobre propriedades de
uma classe de domínio indicando o nome correspondente em JSON (e.g.
`@JsonProperty(“birth_date”)`).
Altere `JsonParserReflect` para implementar o comportamento especificado e
valide com testes unitários.

## Parte 3

Pretende-se ter uma forma alternativa de definir o valor de objectos sem ter que
seguir a sintaxe JSON.
Por exemplo, em vez de a propriedade `birth` de `Person`, do tipo
`pt.isel.sample.Date`, ser definida em JSON, como no exemplo seguinte, poderá
ter uma forma alternativa como  a que se apresenta para `Student`:
* JSON for a Person: `"{ name: "Ze Manel", birth: { year: 1999, month: 9, day: 19}, sibling: { name: "Kata Badala"}}"`
* JSON for a Student: `"{ name: "Maria Papoila", nr: 73753}, birth: "1998-11-17" }"`

Neste caso a propriedade `birth` em `Student` tem que ter uma anotação que
identifique a classe responsável por fazer a conversão de `String` numa
instância de `Date`.
Exemplo:

```kotlin
data class Student (var nr: Int = 0, var name: String? = null, @JsonConvert(JsonToDate::class) val birth: Date)
```

Implemente uma forma de poder associar através da anotação `JsonConvert` um
conversor para qualquer classe de domínio.
`JsonParserReflect` deve passar a ter em consideração esta anotação na
inicialização das instâncias de `Setter`.

Além do exemplo dado, valide a sua implementação com outro exemplo de conversor
para outro tipo de propriedade que deverá acrescentar a uma das classes de
domínio.
