# Study-Up
The backend part of StudyUp, a website designed for student competition in school subjects knowledge

## Регистрация пользователя
### Запрос
`POST /register`

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
    
## Подтверждение почты пользователя
### Запрос
`PUT /email`

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
`GET /confirm-email/{token}`
### Требуется авторизация
Да.
### Ответ

    {
        "status": "success",
        "message": "Адрес электронной почты подтвержден"
    }
