
## Dependências de desenvolvimento

Lombok (consulte o guia de instalação para sua IDE)

    https://projectlombok.org/

### Porta
    8087
### Build
    mvn clean compile package -Dmaven.test.skip -DskipTests -Dmaven.javadoc.skip=true

### Iniciar a aplicação
    java -jar target/group5-0.0.1-SNAPSHOT.jar

### Variáveis de ambiente: 
**AWS** 
* AWS_ACCESS_KEY
* AWS_SECRET_KEY
* SQS_QUEUE_NAME
* AWS_ACCOUNT_ID

**DB** 
* DB_USERNAME
* DB_PASSWORD
* DB_URL

# Documentação no Swagger
http://localhost:8087/swagger-ui.html

# Endpoints

## Consulta de pedidos
### GET /orders?page={1}&user_id={1}&size={4}

**Campos necessários:**
* Header
    * Token
* Query Param
    * page
    * user_id (opcional)
    * size (opcional, valor padrão = 5)

**Retorno:**
* Status
    * 200 (sucess)
    * 400 (bad request)
    * 401 (unauthorized)

* Lista de Pedidos

```
//Exemplo de retorno:

{
    "content": [
        {
            "id": 1,
            "userId": 1,
            "totalValue": 10.0,
            "productsDescription": "Padre Francis está há 10 anos na pequena cidade do Colorado.",
            "createdAt": "2022-06-08T13:19:15.534+00:00",
            "status": "in_progress"
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 1,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 3,
    "totalElements": 3,
    "last": false,
    "size": 1,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
}
```

## Consulta de pedido
### GET /orders/:id

**Campos necessários:**
* Header
    * Token
* Path Param
    * id

**Retorno:**
* Status
    * 200 (sucess)
    * 400 (bad request)
    * 401 (unauthorized)

* Informações do pedido

```
//Exemplo de retorno:

{
    "id": 20,
    "userId": 1,
    "totalValue": "100",
    "productsDescription": "1 banana, 2 cenouras",
    "createdAt": "2022-03-23T17:06:27.530+00:00"
    "status": "done"
}
```

## Criação de pedidos
### POST /orders

**Campos necessários:**
* Header
    * Token
* Body
    * userId
    * totalValue
    * productsDescription


```
//Exemplo de requisição:

{
    "userId": 1,
    "totalValue": "100",
    "productsDescription": "1 banana, 2 cenouras"
}
```

**Retorno:**
* Status
    * 201 (created)
    * 400 (bad request)
    * 401 (unauthorized)

* Mensagem para SQS
    * messageBody: 
    ```
    {
        "products_description": "1 banana, 2 cenouras",
        "total_value": "100",
        "user_email": "joao@email.com",
        "user_name": "joao"
    }
    
    ```
    * messageAttributes:
        * order_id: 1


## Atualização de status de pedido
### PATCH /orders

**Campos necessários:**
* Header
    * Token
* Body
    * status

```
//Exemplo de requisição:

{
    "status": "done"
}
```

**Retorno:**
* Status
    * 200 (success)
    * 400 (bad request)
    * 401 (unauthorized)