# guess-who

Identity provider App

## Build the project

Generate swagger docs:

```bash
  php artisan l5-swagger:generate
```

Run the following command in the terminal:
```bash
  docker compose up --build
```

## Scenario

### Unlock Account

* Send `POST` request to `http://localhost:8000/api/account/unlock`

```json
{
    "email": "locked@gmail.com"
}
```

### Login

* Send `POST` request to `http://localhost:8000/api/login`

```json
{
    "email": "locked@gmail.com",
    "password": "mypassword"
}
```

### Login -- Validate

* Send `POST` request to `http://localhost:8000/api/login/validate`

```json
{
    "email": "locked@gmail.com",
    "pin": "yourpin"
}
```

### Modify Account Password -- Query

* It sends an email containing pin for validation.

* Send `POST` request to `http://localhost:8000/api/account/password`

```json
{
    "id_account": 1,
    "new_password": "vaovao"
}
```

* It returns an `id` that we need for the password change validation.

* See the `pin` in the email.

### Modify Account Password -- Validation

* Validates password modification. Use `id` and `pin` from the request above.

* Send `POST` request to `http://localhost:8000/api/account/password/validate`

```json
{
    "id": 1,
    "pin": "yourpin"
}
```
