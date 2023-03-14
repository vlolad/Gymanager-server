# Глобальное описание архитектуры проекта

```plantuml
@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml

AddElementTag("service", $shape=EightSidedShape(), $bgColor="CornflowerBlue", $fontColor="white", $legendText="service")
AddElementTag("storage", $shape=RoundedBoxShape(), $bgColor="lightSkyBlue", $fontColor="white")

Person(user, "Пользователь", "Тренер")
Person(admin, "Админ")

System_Ext(ui, "Интерфейс (веб/android)")

Rel(user, ui, "Создание и редактирование карточек клиентов, создание и редактирование тренировок")
Rel(admin, ui, "Управление учетками пользователей")

System_Boundary(monolite, "Сервер (монолит)") {
    Container(auth_service, "Сервис авторизации", "/auth", "Сервис для авторизации тренеров", $tags = "service")
    Container(user_service, "Сервис пользователей", "/user", "Сервис для получения и редактирования информации о пользователях", $tags = "service")
    Container(clients_service, "Сервис для работы с клиентами", "/client", "Получение, создание, редактирование и удаление клиентов тренерами", $tags = "service")
    Container(training_service, "Сервис тренировок", "/trains", "Получение, создание, редактирование и удаление тренировок клиентов", $tags = "service")
    Container(exercises_service, "Сервис упражнений", "/exercises","Получение, создание, редактирование упражнений для тренировок", $tags = "service")
    ContainerDb(db, "База данных", "Postgresql", "Хранение данных о пользователях, клиентах, ттренировках", $tags = "storage")
}

Rel(ui, auth_service, "")
Rel(ui, clients_service, "")
Rel(ui, user_service, "")
Rel(ui, training_service, "")
Rel(ui, exercises_service, "")

Rel(auth_service, db, "select")
Rel(user_service, db, "select")
Rel(clients_service, db, "select/insert/update (sql)")
Rel(training_service, db, "select/insert/update (sql)")
Rel(exercises_service, db, "select/insert/update (sql)")


@enduml
