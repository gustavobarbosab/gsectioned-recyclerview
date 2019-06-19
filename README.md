# GSectioned RecyclerView

Esta biblioteca foi criada de modo a facilitar a criação de um RecyclerView com seções, algo muito comum em diversas aplicações.
Abaixo segue um breve exemplo do funcionamento da biblioteca.

<img src="project_list.gif" alt="drawing" width="338" heigh="600"/>

## Adicionar ao projeto

1. Adicione o repositório do jitpack a sua aplicação
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2. Adicione a biblioteca ao seu projeto
```
implementation 'com.github.gustavobarbosab:gsectioned-recyclerview:1.0-BETA'

```

## Como usar a lib

1. Crie seu adapter e um HeaderViewHolder que deverá extender SectionedRecyclerAdapter.SectionedHeaderViewHolder

```kotlin
 class HeaderViewHolder(view: View) : SectionedRecyclerAdapter.SectionedHeaderViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvHeaderMain)
}
```
2. Logo em seguida crie seu BodyViewHolder que extenderá de SectionedRecyclerAdapter.SectionedBodyViewHolder

```kotlin
class BodyViewHolder(view: View) : SectionedRecyclerAdapter.SectionedBodyViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.tvBodyMain)
}
```

3. Uma vez criados Header e Body ViewHolder, faça seu adapter extender de SectionedRecyclerAdapter<HEADER_VH,BODY_VH>

```kotlin
class MainRecyclerAdapter :
            SectionedRecyclerAdapter<MainRecyclerAdapter.HeaderViewHolder,MainRecyclerAdapter.BodyViewHolder>()
...
```
4. Feito os passos acima, partiremos para a implementação do adapter

    1. getBodyLayout():
    Deverá retornar somente o id do layout do Body.
    ```kotlin
    override fun getBodyLayout(): Int = R.layout.item_body_main
    ```

    2. getHeaderLayout():
    Deverá retornar somente o id do layout do Header.
    ```kotlin
    override fun getHeaderLayout(): Int = R.layout.item_header_main
    ```
    3. onCreateHeaderViewHolder(view: View): 
    O método recebe como parametro a view já inflada, portanto, basta passá-la no construtor do seu ViewHolder.

    ```kotlin
    override fun onCreateHeaderViewHolder(view: View): HeaderViewHolder =
            HeaderViewHolder(view)
    ```
    
    4. onCreateBodyViewHolder(view: View):
    O mesmo ocorrerá para o Body.

    ```kotlin
    override fun onCreateBodyViewHolder(view: View): BodyViewHolder =
            BodyViewHolder(view)
    ```
    5. getHeaderSize()
    Deve retornar o tamanho da lista de Headers.
    ```kotlin
    override fun getHeaderSize(): Int = list.size
    ```
    6. getBodySize(headerPosition: Int)
    Deve retornar o tamanho do Body de determinado Header.
    ```kotlin
    override fun getBodySize(headerPosition: Int): Int = list[headerPosition].bodies.size
    ```
    7. onBindHeaderViewHolder(viewHolder: HeaderViewHolder, headerPosition: Int) 
    Aqui é feito o bind do Header View Holder, conforme ocorre no Adapter convencional.
    ```kotlin
    override fun onBindHeaderViewHolder(viewHolder: HeaderViewHolder, headerPosition: Int) {
        val header = list[headerPosition]
        viewHolder.title.text = header.title
    }
    ```
    
    8. onBindBodyViewHolder(viewHolder: BodyViewHolder, headerPosition: Int, bodyPosition: Int)
    Aqui é feito o bind do Body View Holder, conforme ocorre no Adapter convencional.

    ```kotlin
    override fun onBindBodyViewHolder(viewHolder: BodyViewHolder, headerPosition: Int, bodyPosition: Int) {
        val header = list[headerPosition]
        val body = header.bodies[bodyPosition]
        viewHolder.titleText.text = body.value
    }
    ```

# Dúvidas e sugestões
Email: gustavobarbosabarreto@gmail.com
