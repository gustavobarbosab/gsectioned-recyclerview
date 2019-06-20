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
implementation 'com.github.gustavobarbosab:gsectioned-recyclerview:1.2-BETA'

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

    * getBodyLayout():
    
    Deverá retornar somente o id do layout do Body.
    ```kotlin
    override fun getBodyLayout(): Int = R.layout.item_body_main
    ```

    * getHeaderLayout():
    
    Deverá retornar somente o id do layout do Header.
    ```kotlin
    override fun getHeaderLayout(): Int = R.layout.item_header_main
    ```
    * onCreateHeaderViewHolder(parent: ViewGroup): 
    
    O método recebe como parametro o viewGroup, que pode ser usado para inflar a view da forma que convier. Caso não esteja usando DataBinding e deseje inflar a view através do LayoutInflater, basta chamar o método inflateHeader, passando o ViewGroup como parametro.

    ```kotlin
    override fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        val view = inflateHeader(parent)
        return HeaderViewHolder(view)
    }
    ```
    
    * onCreateBodyViewHolder(view: View):
    
    O mesmo ocorre aqui, basta chamar o método inflateBody, passando o ViewGroup como parametro.

    ```kotlin
    override fun onCreateBodyViewHolder(parent: ViewGroup): BodyViewHolder {
        val view = inflateBody(parent)
        return BodyViewHolder(view)
    }
    ```
    * getHeaderSize()
    
    Deve retornar o tamanho da lista de Headers.
    ```kotlin
    override fun getHeaderSize(): Int = list.size
    ```
    * getBodySize(headerPosition: Int)
    
    Deve retornar o tamanho do Body de determinado Header.
    ```kotlin
    override fun getBodySize(headerPosition: Int): Int = list[headerPosition].bodies.size
    ```
    * onBindHeaderViewHolder(viewHolder: HeaderViewHolder, headerPosition: Int) 
    
    Aqui é feito o bind do Header View Holder, conforme ocorre no Adapter convencional.
    ```kotlin
    override fun onBindHeaderViewHolder(viewHolder: HeaderViewHolder, headerPosition: Int) {
        val header = list[headerPosition]
        viewHolder.title.text = header.title
    }
    ```
    
    * onBindBodyViewHolder(viewHolder: BodyViewHolder, headerPosition: Int, bodyPosition: Int)
    
    Aqui é feito o bind do Body View Holder, conforme ocorre no Adapter convencional.
    ```kotlin
    override fun onBindBodyViewHolder(viewHolder: BodyViewHolder, headerPosition: Int, bodyPosition: Int) {
        val header = list[headerPosition]
        val body = header.bodies[bodyPosition]
        viewHolder.titleText.text = body.value
    }
    ```
## INFORMAÇÕES IMPORTANTES: 
De modo a fazer os calculos necessários para manipulação das posições de Header e Body, ao notificar alterações no adapter, usar os seguintes métodos.

### Notificar alteração em toda lista
```kotlin
fun setList(newList: List<HeaderModel>) {
        list.clear()
        list.addAll(newList)
        notifySectionedDataChanged() // Use este método
    }
```

### Notificar alteração por inclusão de itens 
```kotlin
// Exemplo de inclusão no inicio da lista
    fun addList(newItems: List<HeaderModel>) {
        list.addAll(0,newItems)
        notifySectionedItemRangeInserted(0,newItems.size)
    }
```

### Notificar alteração por exclusão de itens 
```kotlin
// Exemplo de exclusão no inicio da lista
    fun removeItem(index: Int) {
        list.removeAt(index)
        notifySectionedItemRemoved(index)
    }
```
O mesmo é feito para remoção de itens em um intervalo, basta chamar o método: notifySectionedItemRangeRemoved.

## Usando o header fixo
Para usar o header fixo no topo, como mostra no gif acima. Basta adicionar o StickHeaderItemDecoration em seu RecyclerView.

```kotlin
private fun setupRecyclerView() {
	rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
	rvMain.adapter = adapter
	rvMain.addItemDecoration(StickHeaderItemDecoration(adapter))
	adapter.setList(model)
}
```

## Dúvidas e sugestões
Email: gustavobarbosabarreto@gmail.com
