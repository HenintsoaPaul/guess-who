# guess-who
Identity provider App

## Build the project

Run the following command in the terminal:
```bash
  docker compose up --build
```

## Scenario

### Unlock Account

* Send `POST` request to `http://localhost:8000/api/account/unlock`

```json
{
    "email": "locked@gmail.com",
    "password": "mypassword"
}
```

### Modify Account Password -- Query

* It sends an email containing pin for validation.

* Send `POST` request to `http://localhost:8000/api/account/password`

```json
{
    "email": "locked@gmail.com",
    "password": "mypassword"
}
```

### Modify Account Password -- Validation

* Validates to password modification.

* Send `POST` request to `http://localhost:8000/api/account/password/validate`

```json
{
    "email": "locked@gmail.com",
    "password": "mypassword"
}
```


## Attention

Tsy alefa ao anaty zip ilay folder `./vendor`
