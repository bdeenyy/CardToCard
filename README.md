# CardToCard

### Описание

Данный проект представляет собой приложение для трансфера денежных средств между кредитными картами. Он состоит из трех основных компонентов: TransferController, RequestHandler и TransferService.

**TransferController** является REST API контроллером, который обрабатывает входящие HTTP запросы на перевод денег и подтверждение операций перевода. Он использует экземпляр класса RequestHandler для обработки запросов. Контроллер принимает POST запросы на URL /transfer и /confirmOperation соответственно.

**RequestHandler** является классом, который обрабатывает запросы, вызывая методы экземпляра TransferService. Он содержит два метода: handleTransferRequest для обработки запросов на перевод денег, и handleConfirmRequest для обработки запросов на подтверждение операции перевода.

**TransferService** является сервисом, который предоставляет функциональность для перевода денег с одной кредитной карты на другую. Он использует экземпляры классов CardRepositoryImpl и TransferLogger для хранения информации о картах и записи операций перевода соответственно.

[//]: # (Описание методов)

[//]: # (public TransferResponse transfer&#40;TransferRequest transferRequest&#41;)

[//]: # (Метод transfer переводит деньги с одной карты на другую в соответствии с переданным запросом на перевод. Он создает идентификатор операции, сохраняет данные карты, получает информацию о картах из репозитория, проверяет существование обеих карт, вычисляет и применяет комиссию, списывает средства с карты отправителя, зачисляет их на карту получателя, записывает операцию в лог используя TransferLogger, добавляет идентификатор операции в список операций и возвращает TransferResponse с идентификатором операции.)

[//]: # ()
[//]: # (public TransferResponse confirmOperation&#40;ConfirmRequest confirmRequest&#41;)

[//]: # (Метод confirmOperation подтверждает операцию перевода, возвращая TransferResponse с идентификатором операции, если идентификатор операции присутствует в списке операций. В противном случае, он генерирует исключение BadRequestException.)

[//]: # ()
[//]: # (Конструкторы)

[//]: # (public TransferController&#40;RequestHandler requestHandler&#41;)

[//]: # (Конструктор класса TransferController принимает экземпляр класса RequestHandler в качестве параметра и инициализирует поле requestHandler.)

[//]: # ()
[//]: # (public RequestHandler&#40;TransferService transferService&#41;)

[//]: # (Конструктор класса RequestHandler принимает экземпляр класса TransferService в качестве параметра и инициализирует поле transferService.)

[//]: # ()
[//]: # (public TransferService&#40;CardRepositoryImpl cardRepositoryImpl, TransferLogger transferLogger&#41;)

[//]: # (Конструктор класса TransferService принимает экземпляры классов CardRepositoryImpl и TransferLogger в качестве параметров и инициализирует поля cardRepositoryImpl и transferLogger.)

[//]: # ()
[//]: # (Описание полей)

[//]: # (private final List<String> listOfOperations = new ArrayList<>&#40;&#41;)

[//]: # (Поле listOfOperations хранит список идентификаторов операций.)

[//]: # ()
[//]: # (private final CardRepositoryImpl cardRepositoryImpl;)

[//]: # (Поле cardRepositoryImpl является экземпляром класса CardRepositoryImpl, который используется для хранения данных о картах.)

[//]: # ()
[//]: # (private final TransferLogger transferLogger;)

[//]: # (Поле transferLogger является экземпляром класса TransferLogger, который используется для записи операций перевода.)