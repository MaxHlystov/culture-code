package ru.fmtk.khlystov.culture_code.changelogs.collections

fun getListsOfBookGenres(): Map<String, Sequence<String>> = mapOf(
        "Фантастика и Фэнтези" to sequenceOf(
                "Альтернативная история",
                "Боевая фантастика",
                "Героическая фантастика",
                "Городское фэнтези",
                "Готический роман",
                "Детективная фантастика",
                "Ироническая фантастика",
                "Ироническое фэнтези",
                "Историческое фэнтези",
                "Киберпанк",
                "Космическая фантастика",
                "Космоопера",
                "ЛитРПГ",
                "Мистика",
                "Научная фантастика",
                "Ненаучная фантастика",
                "Попаданцы",
                "Постапокалипсис",
                "Сказочная фантастика",
                "Социально-философская фантастика",
                "Стимпанк",
                "Технофэнтези",
                "Ужасы и мистика",
                "Фантастика: прочее",
                "Фэнтези",
                "Эпическая фантастика",
                "Юмористическая фантастика",
                "Юмористическое фэнтези"),
        "Детективы и Триллеры" to sequenceOf(
                "Боевики",
                "Дамский детективный роман",
                "Иронические детективы",
                "Исторические детективы",
                "Классические детективы",
                "Криминальные детективы",
                "Крутой детектив",
                "Маньяки",
                "Медицинский триллер",
                "Политические детективы",
                "Полицейские детективы",
                "Прочие Детективы",
                "Техно триллер",
                "Триллеры",
                "Шпионские детективы",
                "Юридический триллер"
        ),
        "Проза" to sequenceOf(
                "Антисоветская литература",
                "Афоризмы",
                "Военная проза",
                "Историческая проза",
                "Классическая проза",
                "Контркультура",
                "Магический реализм",
                "Новелла",
                "Повесть",
                "Проза прочее",
                "Рассказ",
                "Роман",
                "Русская классическая проза",
                "Семейный роман/Семейная сага",
                "Сентиментальная проза",
                "Советская классическая проза",
                "Современная проза",
                "Феерия",
                "Эпистолярная проза",
                "Эпопея",
                "Эссе, очерк, этюд, набросок"
        ),
        "Любовные романы" to sequenceOf(
                "Исторические любовные романы",
                "Короткие любовные романы",
                "Любовно-фантастические романы",
                "Остросюжетные любовные романы",
                "Порно",
                "Прочие любовные романы",
                "Слеш",
                "Современные любовные романы",
                "Фемслеш",
                "Эротика"
        ),
        "Приключения" to sequenceOf(
                "Вестерны",
                "Исторические приключения",
                "Морские приключения",
                "Приключения про индейцев",
                "Природа и животные",
                "Прочие приключения",
                "Путешествия и география"
        ),
        "Детские" to sequenceOf(
                "Детская образовательная литература",
                "Детская проза",
                "Детская фантастика",
                "Детские остросюжетные",
                "Детские приключения",
                "Детские стихи",
                "Детский фольклор",
                "Книга-игра",
                "Прочая детская литература",
                "Сказки"
        ),
        "Поэзия и драматургия" to sequenceOf(
                "Басни",
                "Верлибры",
                "Визуальная поэзия",
                "в стихах",
                "Драматургия",
                "Лирика",
                "Палиндромы",
                "Песенная поэзия",
                "Поэзия",
                "Экспериментальная поэзия",
                "Эпическая поэзия"
        ),
        "Старинная литература" to sequenceOf(
                "Античная литература",
                "Древневосточная литература",
                "Древнерусская литература",
                "Европейская старинная литература",
                "Мифы. Легенды. Эпос",
                "Прочая старинная литература"
        ),
        "Научно-образовательная" to sequenceOf(
                "Альтернативная медицина",
                "Аналитическая химия",
                "Астрономия и Космос",
                "Биология",
                "Биофизика",
                "Биохимия",
                "Ботаника",
                "Ветеринария",
                "Военная история",
                "Геология и география",
                "Государство и право",
                "Детская психология",
                "Зоология",
                "Иностранные языки",
                "История",
                "Культурология",
                "Литературоведение",
                "Математика",
                "Медицина",
                "Обществознание",
                "Органическая химия",
                "Педагогика",
                "Политика",
                "Прочая научная литература",
                "Психология",
                "Психотерапия и консультирование",
                "Религиоведение",
                "Рефераты",
                "Секс и семейная психология",
                "Технические науки",
                "Учебники",
                "Физика",
                "Физическая химия",
                "Философия",
                "Химия",
                "Шпаргалки",
                "Экология",
                "Юриспруденция",
                "Языкознание"
        ),
        "Компьютеры и Интернет" to sequenceOf(
                "Базы данных",
                "Интернет",
                "Компьютерное «железо»   ",
                "ОС и Сети",
                "Программирование",
                "Программное обеспечение",
                "Прочая компьютерная литература",
                "Цифровая обработка сигналов"
        ),
        "Справочная литература" to sequenceOf(
                "Прочая справочная литература",
                "Путеводители",
                "Руководства",
                "Словари",
                "Справочники",
                "Энциклопедии"
        ),
        "Документальная литература" to sequenceOf(
                "Биографии и мемуары",
                "Военная документалистика",
                "Искусство и Дизайн",
                "Критика",
                "Научпоп",
                "Прочая документальная литература",
                "Публицистика"
        ),
        "Религия и духовность" to sequenceOf(
                "Астрология",
                "Буддизм",
                "Индуизм",
                "Ислам",
                "Иудаизм",
                "Католицизм",
                "Православие",
                "Протестантизм",
                "Прочая религиозная литература",
                "Религия",
                "Самосовершенствование",
                "Хиромантия",
                "Христианство",
                "Эзотерика",
                "Язычество"
        ),
        "Юмор" to sequenceOf(
                "Анекдоты",
                "Комедия",
                "Прочий юмор",
                "Сатира",
                "Юмористическая проза",
                "Юмористические стихи"
        ),
        "Дом и Семья" to sequenceOf(
                "Домашние животные",
                "Здоровье и красота",
                "Коллекционирование",
                "Кулинария",
                "Прочее домоводство",
                "Развлечения",
                "Сад и Огород",
                "Сделай сам",
                "Спорт",
                "Хобби и ремесла",
                "Эротика и секс"
        ),
        "Деловая литература" to sequenceOf(
                "Банковское дело",
                "Бухучет и аудит",
                "Внешнеэкономическая деятельность",
                "Деловая литература",
                "Делопроизводство",
                "Корпоративная культура",
                "Личные финансы",
                "Малый бизнес",
                "Маркетинг, PR, реклама",
                "Недвижимость",
                "О бизнесе популярно",
                "Отраслевые издания",
                "Поиск работы, карьера",
                "Торговля",
                "Управление, подбор персонала",
                "Ценные бумаги, инвестиции",
                "Экономика"
        ),
        "Жанр не определен" to sequenceOf(
                "Иностранная литература",
                "Разное"
        ),
        "Техника" to sequenceOf(
                "Автомобили и ПДД",
                "Архитектура",
                "Металлургия",
                "Радиоэлектроника",
                "Строительство и сопромат",
                "Транспорт и авиация"
        ),
        "Прочее" to sequenceOf(
                "Газеты и журналы",
                "Изобразительное искусство, фотография",
                "Кино",
                "Музыка",
                "Недописанное",
                "Подростковая литература",
                "Театр",
                "Фанфик",
                "Шахматы"
        ),
        "Драматургия" to sequenceOf(
                "Водевиль",
                "Драма",
                "Киносценарии",
                "Мистерия",
                "Сценарии",
                "Трагедия"
        ),
        "Фольклор" to sequenceOf(
                "Былины",
                "Загадки",
                "Народные песни",
                "Народные сказки",
                "Пословицы, поговорки",
                "Фольклор: прочее",
                "Частушки, прибаутки, потешки"
        ),
        "Военное дело" to sequenceOf(
                "Боевые искусства",
                "Военная техника и вооружение",
                "Военное дело: прочее",
                "Спецслужбы"
        ),
        "Бизнес-книги" to sequenceOf(
                "Бухучет / налогообложение / аудит",
                "Государственное и муниципальное управление",
                "Зарубежная деловая литература",
                "Интернет-бизнес",
                "Кадровый менеджмент",
                "Краткое содержание",
                "Личная эффективность",
                "Логистика",
                "Менеджмент",
                "Менеджмент и кадры",
                "Ораторское искусство / риторика",
                "Переговоры",
                "Поиск работы / карьера",
                "Политическое управление",
                "Продажи",
                "Работа с клиентами",
                "Стартапы и создание бизнеса",
                "Тайм-менеджмент",
                "Финансы",
                "Ценные бумаги / инвестиции"
        ),
        "Детские книги" to sequenceOf(
                "Буквари",
                "Внеклассное чтение",
                "Детская познавательная и развивающая литература",
                "Детские детективы",
                "Зарубежные детские книги",
                "Книги для дошкольников",
                "Книги для подростков",
                "Учебная литература",
                "Школьные учебники"
        ),
        "Дом и дача" to sequenceOf(
                "Домашнее хозяйство",
                "Интерьеры",
                "Комнатные растения",
                "Отдых / туризм",
                "Охота",
                "Ремонт в квартире",
                "Рукоделие и ремесла",
                "Рыбалка",
                "Фэн-шуй",
                "Хобби / увлечения"
        ),
        "Зарубежная литература" to sequenceOf(
                "Зарубежная драматургия",
                "Зарубежная классика",
                "Зарубежная компьютерная литература",
                "Зарубежная литература о культуре и искусстве",
                "Зарубежная образовательная литература",
                "Зарубежная поэзия",
                "Зарубежная прикладная литература",
                "Зарубежная психология",
                "Зарубежная публицистика",
                "Зарубежная религиозная и эзотерическая литература",
                "Зарубежная религиозная литература",
                "Зарубежная справочная литература",
                "Зарубежная старинная литература",
                "Зарубежная фантастика",
                "Зарубежная эзотерическая литература",
                "Зарубежное фэнтези",
                "Зарубежные боевики",
                "Зарубежные детективы",
                "Зарубежные любовные романы",
                "Зарубежные приключения",
                "Зарубежный юмор",
                "Современная зарубежная литература"
        ),
        "Знания и навыки" to sequenceOf(
                "Бизнес-книги",
                "Изучение языков",
                "Истории из жизни",
                "Компьютерная литература",
                "Культура и искусство",
                "Научно-популярная литература",
                "Саморазвитие / личностный рост",
                "Словари, справочники",
                "Учебная и научная литература",
                "Хобби, досуг"
        ),
        "История" to sequenceOf(
                "Документальная литература",
                "Историческая литература",
                "Историческая фантастика",
                "Книги о войне",
                "Книги о путешествиях",
                "Популярно об истории"
        ),
        "Легкое чтение" to sequenceOf(
                "Боевики, остросюжетная литература",
                "Детективы",
                "Классика жанра",
                "Комиксы / манга",
                "Легкая проза",
                "Любовные романы",
                "Приключения",
                "Ужасы / мистика",
                "Фантастика",
                "Юмористическая литература"
        ),
        "Психология и мотивация" to sequenceOf(
                "Книги по психологии",
                "Религия и духовная литература"
        ),
        "Публицистика и периодические издания" to sequenceOf(
                "Периодические издания",
                "Публицистическая литература"
        ),
        "Родителям" to sequenceOf(
                "Воспитание детей",
                "Детские книги",
                "Здоровье детей"
        ),
        "Серьезное чтение" to sequenceOf(
                "Cтихи, поэзия",
                "Классическая литература",
                "Об истории серьезно",
                "Пьесы, драматургия"
        ),
        "Спорт, здоровье и красота" to sequenceOf(
                "Красота",
                "Медицина и здоровье",
                "Секс / секс-руководства"
        ),
        "Хобби и досуг" to sequenceOf(
                "Изобразительное искусство",
                "Интеллектуальные игры",
                "Искусство фотографии",
                "Мода и стиль",
                "Прикладная литература",
                "Спорт / фитнес"
        )
)