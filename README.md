# Study-Up
The backend part of StudyUp, a website designed for student competition in school subjects knowledge

## Регистрация пользователя
### Запрос
`POST /api/auth/register`

    {
        "username": "example",
        "password": "password123",
        "email": "example@example.com"
    }
    
### Требуется авторизация
Нет.
### Ответ

    {
        "status": "success",
        "message": "Пользователь успешно зарегистрирован"
    }

## Проверка на существование пользователя
### Запрос
`GET /api/auth/login

    {
        "username": "example",
        "password": "password123",
    }

### Требуется авторизация
Нет.
### Ответ

    {
        "status": "success",
        "message": true
    }

## Смена почты пользователя
### Запрос
`PUT /api/auth/email`

    {
        "new_email": "new@example.com"
    }
    
### Требуется авторизация
Да.
### Ответ

    {
        "status": "success",
        "message": "Адрес электронной почты успешно изменен"
    }
    
## Подтверждение почты пользователя
### Запрос
`GET /api/auth/confirm-email/{token}`
### Требуется авторизация
Нет.
### Ответ

    {
        "status": "success",
        "message": "Адрес электронной почты подтвержден"
    }
