# java-explore-with-me

# Идея

Свободное время — ценный ресурс. Ежедневно мы планируем, как его потратить — куда и с кем сходить.
Сложнее всего в таком планировании поиск информации и переговоры. Нужно учесть много деталей:

* какие намечаются мероприятия
* свободны ли в этот момент друзья
* как всех пригласить и где собраться.

### Приложение, java-explore-with-me — афиша, REST API часть.

В этой афише можно предложить какое-либо событие от выставки до похода в кино и собрать компанию для участия в нём.

## ПОСТАВЛЕННЫЕ ЦЕЛИ:
---
Приложение должно уметь:
---

1. Просмотр всех событий
2. Добавление нового события
3. Добавление заявки на участие в событии

## ТЕНХНОЛОГИИ:

- REST API
- java 11
- Spring BOOT
- Maven
- PostgreSQL
- Docker

---
Приложение состоит из 2 сервисов

1. Основаня часть

`Основная часть приложения работает на порту 8080`

2. Сервис сбора аналитики

`Сервис статистики работает на порту 9090`

## Structure DB

<img src="./State_db.png">

---

## Сбор аналитики подразумевает:

- Хранение общей информации к какому эндпоинту, в каком количестве были запросы

---
## Инструкция по развертыванию проекта:
1. Скачать данный репозиторий
2. mvn clean
3. mvn package
4. docker-compose build
5. docker-compose up -d

---

# В основном сервисе

## Возможности доступные всем:

### В разделе СОБЫТИЯ:

- Подборка списка событий

````
GET /compilations
````

- Получить подборку событий по его ID

````
GET /compilations/{id}
````

###

### В разделе КАТЕГОРИИ:

- Получить списка категорий событий

~~~
GET /categories
~~~

- Получение информации о категории по её идентификатору

~~~
GET /categories/{catId}
~~~

### Комментирование:

- Получить все комментарии текущего события

~~~
GET /users/{userId}/comments
~~~

###

---

## Возможности доступные зарегистрированным пользователям:

###

### В разделе СОБЫТИЯ:

- Получение событий, добавленных текущими пользователем

~~~
GET /users/{userId}/events
~~~

- Добавление нового события (проходит модерацию)

~~~
POST /users/{userId}/events
~~~

- Получить полную информацию о событии добавленном текущим пользователем

~~~
GET /users/{userId}/events/{eventId}
~~~

- Изменить события добавленного текущим пользователем

~~~
PATCH /users/{userId}/events/{eventId}
~~~

- Получение информации о запросах на участие в событии текущего пользователя

~~~
GET /users/{userId}/events/{eventId}/requests
~~~

- Изменить статус (подтверждение, отмена) заявок на участие в событии текущего пользователя

~~~
PATCH /users/{userId}/events/{eventId}/requests
~~~

###

## Запросы на УЧАСТИЕ:

- Получить информацию о заявках текущего пользователя на участие в чужих событиях

~~~
GET /users/{userId}/requests
~~~

- Добавление запроса от текущего пользователя на участие в событии

~~~
POST /users/{userId}/requests
~~~

- Отмена своего запроса на участие в событии

~~~
PATCH /users/{userId}/request/{requestId}/cancel
~~~

###

## Комментирование:

- Добавить новый комментарий

~~~
POST /users/{userId}/comments
~~~

- Получить все комментарии текущего пользователя

~~~
GET /users/{userId}/comments
~~~

- Редактирование комментария текущего пользователя

~~~
PATH /users/{userId}/comments/{commentId}
~~~

- Удалить комментарий текущего пользователя

~~~
DELETE /users/{userId}/comments/{commentId}
~~~

###

---

## Админ панель

###

### В Раздел КАТЕГОРИИ:

- Добавление новой категории

~~~
POST /admin/categories
~~~

- Удаление категории

~~~
DELETE /admin/categories/{carId}
~~~

- Изменение категории

~~~
PATCH /admin/categories/{carId}
~~~

###

### В Разделе СОБЫТИЯ:

- Поиск событий

~~~
GET /admin/events
~~~

- Редактирование данных события и его статуса (Отклонение/Публикация)

~~~
PATCH /admin/events/{eventId}
~~~

###

### ПОЛЬЗОВАТЕЛИ

- Получение информации о пользователях

~~~
GET /admin/users
~~~

- Добавление нового пользователя

~~~
POST /admin/users
~~~

- Удаление пользователя

~~~
DELETE /admin/users/{userId}
~~~

###

### Подборки событий

- Добавление новой подборки

~~~
POST /admin/compilations
~~~

- Удаление подборки

~~~
DELETE /admin/
~~~

### Комментирование

- Удаление комментария пользователя

~~~
DELETE /admin/comments/{commentId}
~~~

- Поиск комментария по ключевым словам

~~~
GET /admin/comments/{commentId}
~~~

##### Template repository for ExploreWithMe project.