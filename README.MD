# Mini Cart & Billing API

A simple REST API built using Spring Boot for managing a grocery cart and calculating the final bill. The application seeds 10 grocery products on startup and stores all prices in **paise** to avoid floating-point precision issues.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 Database
- Bean Validation
- Lombok
- JUnit 5 & MockMvc

---

## Running the Project

Clone the repository:

```bash
git clone <repository-url>
cd cart-api
```

Start the application:

```bash
./mvnw spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

## H2 Database

H2 Console:

```
http://localhost:8080/h2-console
```

Connection Details:

```
JDBC URL : jdbc:h2:mem:grocerydb
Username : sa
Password : (leave blank)
```

---

## API Endpoints

### Get all products

```http
GET /products
```

```bash
curl http://localhost:8080/products
```

Example Response

```json
[
  {
    "id": 1,
    "name": "Rice",
    "pricePaise": 5500,
    "unit": "1kg"
  }
]
```

---

### Add item to cart

```http
POST /cart/items
```

Request

```json
{
  "productId": 1,
  "quantity": 2
}
```

```bash
curl -X POST http://localhost:8080/cart/items \
-H "Content-Type: application/json" \
-d '{"productId":1,"quantity":2}'
```

Example Response

```json
{
  "productId": 1,
  "name": "Rice",
  "unit": "1kg",
  "quantity": 2
}
```

---

### Update cart quantity

```http
PATCH /cart/items/1
```

Request

```json
{
  "quantity": 5
}
```

```bash
curl -X PATCH http://localhost:8080/cart/items/1 \
-H "Content-Type: application/json" \
-d '{"quantity":5}'
```

Setting the quantity to `0` removes the item from the cart.

---

### Get cart

```http
GET /cart
```

```bash
curl http://localhost:8080/cart
```

Example Response

```json
{
  "items": [
    {
      "productId": 1,
      "name": "Rice",
      "unit": "1kg",
      "quantity": 2
    }
  ],
  "bill": {
    "itemTotalPaise": 11000,
    "deliveryFeePaise": 3000,
    "grandTotalPaise": 14000
  }
}
```

---

## Business Rules

- Products are inserted automatically when the application starts.
- Adding the same product again increases its quantity.
- Updating the quantity to `0` removes the product from the cart.
- Delivery charges are **3000 paise (₹30)**.
- Delivery is free when the item total is greater than **50000 paise (₹500)**.
- The bill is calculated every time `/cart` is requested and is never stored in the database.

---

## Running Tests

```bash
./mvnw test
```

The tests cover:

- Product listing
- Adding an item to the cart
- Free delivery threshold
- Invalid quantity
- Product not found

---

## Design Decisions

- Used a layered architecture (**Controller → Service → Repository**) to keep responsibilities separate.
- Used request and response DTOs so API models are independent of JPA entities.
- Stored all prices in integer paise instead of floating-point values.
- Calculated the bill inside the service layer instead of storing it in the database.
- Added integration tests using MockMvc to verify the complete request flow.